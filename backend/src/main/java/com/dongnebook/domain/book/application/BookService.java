package com.dongnebook.domain.book.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dongnebook.domain.book.domain.Book;
import com.dongnebook.domain.book.dto.request.BookRegisterRequest;
import com.dongnebook.domain.book.dto.request.SectorBookCountRequest;
import com.dongnebook.domain.book.dto.response.BookDetailResponse;
import com.dongnebook.domain.book.dto.response.BookSectorCountResponse;
import com.dongnebook.domain.book.exception.BookNotFoundException;
import com.dongnebook.domain.book.repository.BookCommandRepository;
import com.dongnebook.domain.book.repository.BookQueryRepository;
import com.dongnebook.domain.member.domain.Member;
import com.dongnebook.domain.member.exception.LocationNotCreatedYetException;
import com.dongnebook.domain.member.repository.MemberRepository;
import com.dongnebook.domain.model.Location;
import com.fasterxml.jackson.annotation.JsonInclude;

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

		Member member = getMember();
		Location location = ifDtoHasNoLocationGetMemberLocation(bookRegisterRequest, member);
		Book book = Book.create(bookRegisterRequest, location, member);

		return bookCommandRepository.save(book).getId();
	}

	@Transactional
	public Long delete(Long bookId, Long memberId) {

		//자기가 쓴 글인지 확인하는 로직

		Book book = getByBookId(bookId);

		book.delete();

		return bookId;
	}

	public BookDetailResponse getDetail(Long id) {
		return bookQueryRepository.getDetail(id);
	}

	private Location ifDtoHasNoLocationGetMemberLocation(BookRegisterRequest bookRegisterRequest, Member member) {
		return Optional.ofNullable(bookRegisterRequest.getLocation())
			.orElse(Optional.ofNullable(member.getLocation()).orElseThrow(LocationNotCreatedYetException::new));
	}

	private Member getMember() {
		return memberRepository.findById(1L).orElseThrow(IllegalStateException::new);
	}

	private Book getByBookId(Long bookId) {
		return bookCommandRepository.findById(bookId).orElseThrow(BookNotFoundException::new);
	}

	public ArrayList<BookSectorCountResponse> getSectorBookCounts(SectorBookCountRequest sectorBookCountRequest) {

		List<Double> latRangeList = sectorBookCountRequest.latRangeList();
		List<Double> lonRangeList = sectorBookCountRequest.lonRangeList();
		List<Location> sectorBookCounts = bookQueryRepository.getSectorBookCounts(sectorBookCountRequest);
		ArrayList<BookSectorCountResponse> bookSectorCountResponses = new ArrayList<>();

		for (int i = 0; i < 9; i++) {
			bookSectorCountResponses.add(new BookSectorCountResponse());
		}

		for (Location location : sectorBookCounts) {
			log.info("location={}", location);

			Double latitude = location.getLatitude();
			Double longitude = location.getLongitude();
			for (int i = 0; i < 2; i++) {
				for (int j = 0; j < 2; j++) {
					if (latRangeList.get(i+1) <= latitude && latitude <= latRangeList.get(i) && lonRangeList.get(j) <= longitude
						&& longitude <= lonRangeList.get(j+1)) {
						makeBookCountResponse(bookSectorCountResponses, i*j, location);
					}
				}
			}

		}

		return bookSectorCountResponses;

		// 섹터 1 :  LatRange 1~0,LonRange 0~1,
		// 섹터 2 :  LatRange 1~0,LonRange 1~2,
		// 섹터 3 :  LatRange 1~0,LonRange 2~3,
		// 섹터 4 : LatRange 2~1,LonRange 0~1,
		// 섹터 5 :  LatRange 2~1,LonRange 1~2,
		// 섹터 6 :  LatRange 2~1,LonRange 2~3,
		// 섹터 7 :  LatRange 3~2,LonRange 0~1,
		// 섹터 8 :  LatRange 3~2,LonRange 1~2,
		// 섹터 9 :  LatRange 3~2,LonRange 2~3,
	}

	private void makeBookCountResponse(ArrayList<BookSectorCountResponse> bookSectorCountResponses, int index,
		Location location) {
		BookSectorCountResponse bookSectorCountResponse = bookSectorCountResponses.get(index);
		bookSectorCountResponse.plusBookCount();
		if(Objects.isNull(bookSectorCountResponse.getLocation())){
			bookSectorCountResponse.initLocation(location);
			bookSectorCountResponse.initSector(index+1L);
		}
	}
}
