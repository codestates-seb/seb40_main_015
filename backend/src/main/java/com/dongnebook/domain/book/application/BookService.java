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
import com.dongnebook.domain.book.dto.response.BookSectorCountResponse;
import com.dongnebook.domain.book.dto.response.BookSimpleResponse;
import com.dongnebook.domain.book.exception.BookNotFoundException;
import com.dongnebook.domain.book.repository.BookCommandRepository;
import com.dongnebook.domain.book.repository.BookQueryRepository;
import com.dongnebook.domain.member.domain.Member;
import com.dongnebook.domain.member.exception.LocationNotCreatedYetException;
import com.dongnebook.domain.member.exception.MemberNotFoundException;
import com.dongnebook.domain.member.repository.MemberRepository;
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
	private final MemberRepository memberRepository;


	@Transactional
	public Long create(BookRegisterRequest bookRegisterRequest, Long memberId) {

		Member member = getMember(memberId);
		Location location = getMemberLocation(member);
		Book book = Book.create(bookRegisterRequest, location, member);

		return bookCommandRepository.save(book).getId();
	}

	@Transactional
	public Long delete(Long bookId, Long memberId) {

		//자기가 쓴 글인지 확인하는 로직
		Book book = getByBookId(bookId);

		if (!Objects.equals(book.getMember().getId(), memberId)) {
			throw new NotOwnerException();
		}

		book.delete();

		return bookId;
	}
	@Transactional
	public void edit(Long memberId, Long id, BookEditRequest bookEditRequest) {

		Book book = getByBookId(id);
		if (!Objects.equals(book.getMember().getId(), memberId)) {
			throw new NotOwnerException();
		}
		book.edit(bookEditRequest);

	}

	public BookDetailResponse getDetail(Long id, Long memberId) {
		return bookQueryRepository.getBookDetail(id, memberId);
	}

	private Location getMemberLocation(Member member) {
		return Optional.ofNullable(member.getLocation()).orElseThrow(LocationNotCreatedYetException::new);
	}

	private Member getMember(Long memberId) {
		return memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
	}

	public Book getByBookId(Long bookId) {
		return bookCommandRepository.findById(bookId).orElseThrow(BookNotFoundException::new);
	}

	public Book getByBookIdWithMerchant(Long bookId) {
		return bookQueryRepository.findByBookIdWithMember(bookId).orElseThrow(BookNotFoundException::new);
	}

	public ArrayList<BookSectorCountResponse> getSectorBookCounts(
		BookSearchCondition condition) {

		List<Double> latRangeList = Location.latRangeList(condition.getLatitude(), condition.getHeight(),
			condition.getLevel());
		List<Double> lonRangeList = Location.lonRangeList(condition.getLongitude(), condition.getWidth(),
			condition.getLevel());
		List<Location> sectorBookCounts = bookQueryRepository.getSectorBookCounts(condition);
		ArrayList<BookSectorCountResponse> bookSectorCountResponses = new ArrayList<>();
		HashMap<Integer, Integer> indexMap = new HashMap<>();
		int arrIndex = 0;


		for (Location location : sectorBookCounts) {
			int sector = 0;
			Loop :
			for (int i = 0; i < condition.getLevel(); i++) {
				for (int j = 0; j < condition.getLevel(); j++) {
					sector++;
					if (checkRange(latRangeList, lonRangeList, location.getLatitude(), location.getLongitude(), i, j)) {
						if (makeBookCountResponse(bookSectorCountResponses, sector, arrIndex, location, indexMap)) {
							arrIndex += 1;
						}
						break Loop;
					}
				}
			}
		}
		return bookSectorCountResponses;
	}


	public SliceImpl<BookSimpleResponse> getList(
		BookSearchCondition bookSearchCondition, PageRequest pageRequest) {

		return bookQueryRepository.getAll(bookSearchCondition, pageRequest);
	}

	private boolean makeBookCountResponse(ArrayList<BookSectorCountResponse> bookSectorCountResponses, int sector,
		int arrIndex, Location location, HashMap<Integer, Integer> indexMap) {
		boolean newResponse = false;

		if (Optional.ofNullable(indexMap.get(sector)).isEmpty()) {
			bookSectorCountResponses.add(new BookSectorCountResponse());
			indexMap.put(sector, arrIndex);
			newResponse = true;
		}

		BookSectorCountResponse bookSectorCountResponse = bookSectorCountResponses.get(indexMap.get(sector));
		bookSectorCountResponse.plusBookCount();

		if (Objects.isNull(bookSectorCountResponse.getLocation())) {
			bookSectorCountResponse.initLocation(location);
			bookSectorCountResponse.initSector(sector);
		}
		return newResponse;
	}

	private boolean checkRange(List<Double> latRangeList, List<Double> lonRangeList, Double latitude, Double longitude,
		int i, int j) {
		return latRangeList.get(i + 1) <= latitude && latitude <= latRangeList.get(i)
			&& lonRangeList.get(j) <= longitude
			&& longitude <= lonRangeList.get(j + 1);
	}

	public SliceImpl<BookSimpleResponse> getListByMember(Long memberId, PageRequest pageRequest) {
		return bookQueryRepository.getListByMember(memberId,pageRequest);
	}



	// 섹터 1 :  LatRange 1~0,LonRange 0~1,  00 1
	// 섹터 2 :  LatRange 1~0,LonRange 1~2,  01 2
	// 섹터 3 :  LatRange 1~0,LonRange 2~3,  02 3
	// 섹터 4 : LatRange 2~1,LonRange 0~1,   10 4
	// 섹터 5 :  LatRange 2~1,LonRange 1~2,  11
	// 섹터 6 :  LatRange 2~1,LonRange 2~3,  12
	// 섹터 7 :  LatRange 3~2,LonRange 0~1,  20
	// 섹터 8 :  LatRange 3~2,LonRange 1~2,  21
	// 섹터 9 :  LatRange 3~2,LonRange 2~3,  22
}
