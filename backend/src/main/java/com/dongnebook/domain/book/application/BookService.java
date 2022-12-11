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

	@Transactional
	public Long create(BookRegisterRequest bookRegisterRequest, Long memberId) {
		Member member = getMember(memberId);
		Location location = getLocationOfMember(member);
		Book book = Book.create(bookRegisterRequest, location, member);
		return bookCommandRepository.save(book).getId();
	}

	public ArrayList<BookCountPerSectorResponse> getBookCountPerSector(BookSearchCondition condition) {
		ArrayList<BookCountPerSectorResponse> BookCountPerSectorResponses = new ArrayList<>();
		HashMap<Integer, Integer> indexMap = new HashMap<>();
		List<Double> latRangeList = getLatRangeList(condition);
		List<Double> lonRangeList = getLonRangeList(condition);
		List<Location> bookLocationList = bookQueryRepository.getNearByBookLocation(condition);
		int arrIndex = 0;

		for (Location location : bookLocationList) {
			int sector = 0;

			arrIndex = addBookCountPerSector(condition, BookCountPerSectorResponses, indexMap, latRangeList, lonRangeList,
				arrIndex, location,
				sector);
		}
		return BookCountPerSectorResponses;
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

	private boolean makeBookCountResponse(ArrayList<BookCountPerSectorResponse> bookCountPerSectorResponses, int sector,
		int arrIndex, Location location, HashMap<Integer, Integer> indexMap) {
		boolean newResponse = false;
		BookCountPerSectorResponse bookCountPerSectorResponse = bookCountPerSectorResponses.get(indexMap.get(sector));
		bookCountPerSectorResponse.plusBookCount();

		if (Optional.ofNullable(indexMap.get(sector)).isEmpty()) {
			bookCountPerSectorResponses.add(new BookCountPerSectorResponse());
			indexMap.put(sector, arrIndex);
			newResponse = true;
		}

		if (Objects.isNull(bookCountPerSectorResponse.getLocation())) {
			bookCountPerSectorResponse.initLocation(location);
			bookCountPerSectorResponse.initSector(sector);
		}

		return newResponse;
	}

	private boolean checkRange(List<Double> latRangeList, List<Double> lonRangeList, Double latitude, Double longitude,
		int i, int j) {
		return latRangeList.get(i + 1) <= latitude && latitude <= latRangeList.get(i)
			&& lonRangeList.get(j) <= longitude && longitude <= lonRangeList.get(j + 1);
	}

	private Member getMember(Long memberId) {
		return memberService.getById(memberId);
	}

	private boolean isMyBook(Long memberId, Book book) {
		return Objects.equals(book.getMember().getId(), memberId);
	}

	private int addBookCountPerSector(BookSearchCondition condition,
		ArrayList<BookCountPerSectorResponse> BookCountPerSectorResponses, HashMap<Integer, Integer> indexMap,
		List<Double> latRangeList, List<Double> lonRangeList, int arrIndex, Location location, int sector) {
		Loop:
		for (int i = 0; i < condition.getLevel(); i++) {

			for (int j = 0; j < condition.getLevel(); j++) {
				sector++;

				if (checkRange(latRangeList, lonRangeList, location.getLatitude(), location.getLongitude(), i, j)) {

					if (makeBookCountResponse(BookCountPerSectorResponses, sector, arrIndex, location, indexMap)) {
						arrIndex += 1;
					}
					break Loop;
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
