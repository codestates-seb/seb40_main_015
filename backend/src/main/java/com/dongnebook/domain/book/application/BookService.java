package com.dongnebook.domain.book.application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dongnebook.domain.book.domain.Book;
import com.dongnebook.domain.book.dto.request.BookEditRequest;
import com.dongnebook.domain.book.dto.request.BookRegisterRequest;
import com.dongnebook.domain.book.dto.request.BookSearchCondition;
import com.dongnebook.domain.book.dto.response.BookCountPerSectorResponse;
import com.dongnebook.domain.book.dto.response.BookDetailResponse;
import com.dongnebook.domain.book.dto.response.BookSimpleResponse;
import com.dongnebook.domain.book.exception.BookNotFoundException;
import com.dongnebook.domain.book.repository.BookCommandRepository;
import com.dongnebook.domain.book.repository.BookQueryRepository;
import com.dongnebook.domain.member.application.MemberService;
import com.dongnebook.domain.member.domain.Member;
import com.dongnebook.domain.member.exception.LocationNotCreatedYetException;
import com.dongnebook.domain.model.Location;
import com.dongnebook.global.dto.request.PageRequest;
import com.dongnebook.global.error.exception.NotOwnerException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookService {
	private final BookCommandRepository bookCommandRepository;
	private final BookQueryRepository bookQueryRepository;
	private final MemberService memberService;
	private List<Double> latRangeList;
	private List<Double> lonRangeList;

	@CacheEvict(value ="books", allEntries = true)
	@Transactional
	public Long create(BookRegisterRequest bookRegisterRequest, Long memberId) {
		Member member = getMember(memberId);
		Location location = getLocationOfMember(member);
		Book book = Book.create(bookRegisterRequest, location, member);
		return bookCommandRepository.save(book).getId();
	}

	public List<BookCountPerSectorResponse> getBookCountPerSector(BookSearchCondition condition) {
		this.latRangeList = getLatRangeList(condition);
		this.lonRangeList = getLonRangeList(condition);
		List<Location> bookLocationList = bookQueryRepository.getNearByBookLocation(condition);
		HashMap<Integer, BookCountPerSectorResponse> map = new HashMap<>();
		// 주변에 주소들을 가져옴
		for (Location location : bookLocationList) {
			IntStream.iterate(1, sector -> sector <= Math.pow(condition.getLevel(), 2), sector -> sector + 1)
				.filter(sector -> checkRange(location, sector, condition.getLevel()))
				.forEach(sector -> {
					map.putIfAbsent(sector, new BookCountPerSectorResponse(0L, sector, location));
					map.computeIfPresent(sector, (k, v) -> v.plusBookCount());
				});
		}

		return new ArrayList<>(map.values());
	}

	@Caching(
		evict = {
			@CacheEvict(value = "books", allEntries = true),
			@CacheEvict(value = "bookDetail", key="#bookId")
		}
	)
	@Transactional
	public Long delete(Long bookId, Long memberId) {
		Book book = getByBookId(bookId);

		if (!isMyBook(memberId, book)) {
			throw new NotOwnerException();
		}

		book.delete();
		return bookId;
	}

	@Transactional
	public void edit(Long memberId, Long id, BookEditRequest bookEditRequest) {
		Book book = getByBookId(id);

		if (!isMyBook(memberId, book)) {
			throw new NotOwnerException();
		}

		book.edit(bookEditRequest);
	}

	@Cacheable(value = "bookDetail", key = "#id",  cacheManager = "cacheManager")
	public BookDetailResponse getDetail(Long id, Long memberId) {
		return bookQueryRepository.findBookDetail(id, memberId).orElseThrow(BookNotFoundException::new);
	}

	private Location getLocationOfMember(Member member) {
		return Optional.ofNullable(member.getLocation()).orElseThrow(LocationNotCreatedYetException::new);
	}

	public Book getByBookId(Long bookId) {
		return bookCommandRepository.findById(bookId).orElseThrow(BookNotFoundException::new);
	}

	public Book getWithMerchantByBookId(Long bookId) {
		return bookQueryRepository.findWithMerchantByBookId(bookId).orElseThrow(BookNotFoundException::new);
	}

	@Cacheable(value = "books", key = "#pageRequest", condition = "#pageRequest.index == null",cacheManager = "cacheManager")
	public SliceImpl<BookSimpleResponse> getList(BookSearchCondition bookSearchCondition, PageRequest pageRequest) {
		return bookQueryRepository.getAll(bookSearchCondition, pageRequest);
	}

	public SliceImpl<BookSimpleResponse> getListByMember(Long memberId, PageRequest pageRequest) {
		return bookQueryRepository.getListByMember(memberId, pageRequest);
	}

	private boolean checkRange(Location location, int sector, int level
		) {
		int i = (sector - 1) / level;
		int j = (sector - 1) % level;
		return this.latRangeList.get(i + 1) <= location.getLatitude() && location.getLatitude() <= this.latRangeList.get(i)
			&& this.lonRangeList.get(j) <=  location.getLongitude() && location.getLongitude() <= this.lonRangeList.get(j + 1);
	}

	private Member getMember(Long memberId) {
		return memberService.getById(memberId);
	}

	private boolean isMyBook(Long memberId, Book book) {
		return Objects.equals(book.getMember().getId(), memberId);
	}

	private List<Double> getLonRangeList(BookSearchCondition condition) {
		return Location.lonRangeList(condition.getLongitude(), condition.getWidth(),
			condition.getLevel());
	}

	private List<Double> getLatRangeList(BookSearchCondition condition) {
		return Location.latRangeList(condition.getLatitude(), condition.getHeight(),
			condition.getLevel());
	}

}
