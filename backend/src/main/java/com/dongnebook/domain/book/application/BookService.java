package com.dongnebook.domain.book.application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dongnebook.domain.book.domain.Book;
import com.dongnebook.domain.book.dto.request.BookEditRequest;
import com.dongnebook.domain.book.dto.request.BookRegisterRequest;
import com.dongnebook.domain.book.dto.request.BookSearchCondition;
import com.dongnebook.domain.book.dto.response.BookDetailResponse;
import com.dongnebook.domain.book.dto.response.BookCountPerSectorResponse;
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
	public List<Double> latRangeList;
	public List<Double> lonRangeList;

	@Transactional
	public Long create(BookRegisterRequest bookRegisterRequest, Long memberId) {
		Member member = getMember(memberId);
		Location location = getLocationOfMember(member);
		Book book = Book.create(bookRegisterRequest, location, member);
		return bookCommandRepository.save(book).getId();
	}

	public List<BookCountPerSectorResponse> getBookCountPerSector(BookSearchCondition condition) {
		List<BookCountPerSectorResponse> bookCountPerSectorResponses = new ArrayList<>();
		HashMap<Integer, Integer> indexMap = new HashMap<>();
		this.latRangeList = getLatRangeList(condition);
		this.lonRangeList = getLonRangeList(condition);
		List<Location> bookLocationList = bookQueryRepository.getNearByBookLocation(condition);
		int arrIndex = 0;

		for (Location location : bookLocationList) {
			int sector = 0;

			arrIndex = addBookCountPerSector(condition, bookCountPerSectorResponses, indexMap,
				arrIndex, location,
				sector);
		}

		return bookCountPerSectorResponses;
	}

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

	public BookDetailResponse getDetail(Long id, Long memberId) {
		return bookQueryRepository.getBookDetail(id, memberId);
	}

	private Location getLocationOfMember(Member member) {
		return Optional.ofNullable(member.getLocation()).orElseThrow(LocationNotCreatedYetException::new);
	}

	public Book getByBookId(Long bookId) {
		return bookCommandRepository.findById(bookId).orElseThrow(BookNotFoundException::new);
	}

	public Book getWithMerchantByBookId(Long bookId) {
		return bookQueryRepository.getWithMerchantByBookId(bookId).orElseThrow(BookNotFoundException::new);
	}

	public SliceImpl<BookSimpleResponse> getList(BookSearchCondition bookSearchCondition, PageRequest pageRequest) {
		return bookQueryRepository.getAll(bookSearchCondition, pageRequest);
	}

	public SliceImpl<BookSimpleResponse> getListByMember(Long memberId, PageRequest pageRequest) {
		return bookQueryRepository.getListByMember(memberId, pageRequest);
	}

	private boolean makeBookCountResponse(List<BookCountPerSectorResponse> bookCountPerSectorResponses, int sector,
		int arrIndex, Location location, HashMap<Integer, Integer> indexMap) {
		boolean newResponse = false;

		if (Optional.ofNullable(indexMap.get(sector)).isEmpty()) {
			bookCountPerSectorResponses.add(new BookCountPerSectorResponse());
			indexMap.put(sector, arrIndex);
			newResponse = true;
		}

		BookCountPerSectorResponse bookCountPerSectorResponse = bookCountPerSectorResponses.get(indexMap.get(sector));
		bookCountPerSectorResponse.plusBookCount();

		if (Objects.isNull(bookCountPerSectorResponse.getLocation())) {
			bookCountPerSectorResponse.initLocation(location);
			bookCountPerSectorResponse.initSector(sector);
		}

		return newResponse;
	}

	private boolean checkRange(Double latitude, Double longitude,
		int i, int j) {
		return this.latRangeList.get(i + 1) <= latitude && latitude <= this.latRangeList.get(i)
			&& this.lonRangeList.get(j) <= longitude && longitude <= this.lonRangeList.get(j + 1);
	}

	private Member getMember(Long memberId) {
		return memberService.getById(memberId);
	}

	private boolean isMyBook(Long memberId, Book book) {
		return Objects.equals(book.getMember().getId(), memberId);
	}

	private int addBookCountPerSector(BookSearchCondition condition,
		List<BookCountPerSectorResponse> bookCountPerSectorResponses, HashMap<Integer, Integer> indexMap,
		int arrIndex, Location location, int sector) {

		for (int i = 0; i < condition.getLevel(); i++) {

			for (int j = 0; j < condition.getLevel(); j++) {
				sector++;

				if (checkRange(location.getLatitude(), location.getLongitude(), i, j) && makeBookCountResponse(
					bookCountPerSectorResponses, sector, arrIndex, location, indexMap)) {
					return arrIndex + 1;
				}

			}

		}

		return arrIndex;
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
