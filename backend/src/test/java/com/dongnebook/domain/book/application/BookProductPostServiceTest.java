package com.dongnebook.domain.book.application;

import static com.dongnebook.support.BookStub.*;
import static com.dongnebook.support.MemberStub.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.SliceImpl;

import com.dongnebook.domain.book.adapter.in.request.BookPostEditRequest;
import com.dongnebook.domain.book.adapter.in.request.BookPostRegisterRequest;
import com.dongnebook.domain.book.adapter.in.request.BookSearchCondition;
import com.dongnebook.domain.book.application.port.in.request.BookPostEditCommand;
import com.dongnebook.domain.book.application.port.in.request.BookPostRegisterCommand;
import com.dongnebook.domain.book.application.port.in.request.BookSearchCommand;
import com.dongnebook.domain.book.application.port.in.response.BookCountPerSectorResponse;
import com.dongnebook.domain.book.application.port.in.response.BookDetailMemberResponse;
import com.dongnebook.domain.book.application.port.in.response.BookDetailResponse;
import com.dongnebook.domain.book.application.port.in.response.BookResponse;
import com.dongnebook.domain.book.application.port.in.response.BookSimpleResponse;
import com.dongnebook.domain.book.application.port.out.BookRepositoryPort;
import com.dongnebook.domain.book.domain.Book;
import com.dongnebook.domain.book.domain.BookState;
import com.dongnebook.domain.member.application.MemberQueryUseCase;
import com.dongnebook.domain.member.domain.Member;
import com.dongnebook.domain.model.Location;
import com.dongnebook.global.dto.request.PageRequestImpl;
import com.dongnebook.global.error.exception.NotOwnerException;
import com.dongnebook.support.LocationStub;

// @DataJpaTest
@ExtendWith(MockitoExtension.class)
class BookProductPostServiceTest {

	@Mock
	BookRepositoryPort bookRepositoryPort;
	@Mock
	MemberQueryUseCase memberService;
	@InjectMocks
	BookCommandService bookCommandService;
	@InjectMocks
	BookQueryService bookQueryService;


	@Test
	@DisplayName("책을 생성한다.")
	void createBook() {
		//given
		BookPostRegisterRequest bookPostRegisterRequest = BookPostRegisterRequest.builder().title("자바의 정석")
			.rentalFee(1000)
			.publisher("도우출판")
			.imageUrl("www.naver.com")
			.description("자바의 정석입니다")
			.author("남궁석")
			.build();
		BookPostRegisterCommand command = BookPostRegisterCommand.of(bookPostRegisterRequest);
		Long bookId = 1L;
		Long memberId = 1L;
		Book savedBook = BOOK1.of(bookId);
		Member member1 = MEMBER1.of(memberId);
		given(bookRepositoryPort.save(any(Book.class))).willReturn(savedBook);
		given(memberService.getMember(memberId)).willReturn(member1);

		//when
		Long savedId = bookCommandService.register(command, memberId);

		//then
		assertAll(
			() -> assertThat(savedId).isEqualTo(1L),
			() -> verify(bookRepositoryPort).save(any(Book.class)))
		;
	}

	@Test
	@DisplayName("책을 수정한다.")
	void bookEdit() {
		//given
		BookPostEditRequest bookPostEditRequest = BookPostEditRequest.builder()
			.description("수정된 책 내용")
			.imageUrl("이미지 내용")
			.build();
		Long memberId = 1L;
		Long bookId = 1L;
		Book savedBook = BOOK1.of(bookId);
		given(bookRepositoryPort.findById(bookId)).willReturn(Optional.of(savedBook));
		BookPostEditCommand command = BookPostEditCommand.of(bookPostEditRequest);
		//when
		bookCommandService.edit(command,memberId,bookId);

		//then
		assertAll(() ->assertThat(savedBook.getDescription()).isEqualTo(bookPostEditRequest.getDescription()));
	}

	@Test
	@DisplayName("책을 수정한다. - 책의 주인이 아니면 예외가 발생한다.")
	void bookEdit_NotOwnerException() {
		//given
		BookPostEditRequest bookPostEditRequest = BookPostEditRequest.builder()
			.description("수정된 책 내용")
			.imageUrl("이미지 내용")
			.build();
		BookPostEditCommand command = BookPostEditCommand.of(bookPostEditRequest);
		Long memberId = 5L;
		Long bookId = 1L;
		Book savedBook = BOOK1.of(bookId);
		given(bookRepositoryPort.findById(bookId)).willReturn(Optional.of(savedBook));

		//when,then
		assertAll(() -> assertThatThrownBy(() -> bookCommandService.edit(command,memberId,bookId)).isInstanceOf(
			NotOwnerException.class));
	}

	@Test
	@DisplayName("책을 삭제한다.")
	void bookDelete() {
		//given
		Long memberId = 1L;
		Long bookId = 1L;
		Book savedBook = BOOK1.of(bookId);
		given(bookRepositoryPort.findById(bookId)).willReturn(Optional.of(savedBook));
		bookCommandService.delete(bookId,memberId);

		//when,then
		assertAll(() ->assertThat(savedBook.getBookState()).isEqualTo(BookState.DELETED));
	}

	@Test
	@DisplayName("책을 삭제한다. - 자신의 책이 아니면 예외가 발생한다.")
	void bookDelete_NotOwnerException() {
		//given
		Long memberId = 2L;
		Long bookId = 1L;
		Book savedBook = BOOK1.of(bookId);
		given(bookRepositoryPort.findById(bookId)).willReturn(Optional.of(savedBook));

		//when,then
		assertAll(() ->assertThatThrownBy(() -> bookCommandService.delete(bookId,memberId)).isInstanceOf(
			NotOwnerException.class),
		()-> verify(bookRepositoryPort).findById(bookId));
	}

	@Test
	@DisplayName("책을 상세조회한다.")
	void bookDetail() {
		//given
		Long memberId = 1L;
		Long bookId = 1L;
		BookResponse bookResponse = BookResponse.builder()
			.bookId(bookId)
			.state(BookState.RENTABLE)
			.dibsId(1L)
			.build();
		BookDetailMemberResponse memberResponse = BookDetailMemberResponse.builder().merchantId(memberId)
			.build();
		BookDetailResponse bookDetailResponse = BookDetailResponse.builder().book(bookResponse).merchant(memberResponse).build();
		given(bookRepositoryPort.findBookDetail(bookId, memberId)).willReturn(Optional.of(bookDetailResponse));

		BookDetailResponse detail = bookQueryService.getDetail(bookId, memberId);

		//when,then
		assertAll(() ->assertThat(detail.getBook()).usingRecursiveComparison()
				.ignoringActualNullFields().isEqualTo(bookResponse),
			() -> verify(bookRepositoryPort).findBookDetail(bookId,memberId));
	}

	@Test
	@DisplayName("책이 db에 없으면 상세조회에 실패한다.")
	void bookDetail_NotFoundException() {
		//given
		Long memberId = 1L;
		Long bookId = 1L;
		given(bookRepositoryPort.findBookDetail(bookId, memberId)).willReturn(Optional.empty());
		//when,then
		assertAll(() -> assertThatThrownBy(() -> bookQueryService.getDetail(bookId, memberId)).isInstanceOf(
			BookNotFoundException.class));
	}

	@Test
	@DisplayName("섹터 당 책 갯수를 가져온다.")
	void bookSectorCount() {
		//가로세로 2km 3x3 중앙 봉천역 기준
		//5섹터
		Location 봉천역 = LocationStub.봉천역.of();
		//7섹터
		Location 서원동성당 = LocationStub.서원동성당.of();
		//9섹터
		Location 관악구청 = LocationStub.관악구청.of();
		//2섹터
		Location 서울관광고 = LocationStub.서울관광고.of();
		BookSearchCondition condition = new BookSearchCondition(null,봉천역.getLongitude(),봉천역.getLatitude(),40,40,null,3);
		List<Location> locations = List.of(봉천역,봉천역, 서원동성당, 관악구청, 서울관광고);
		BookSearchCommand command = BookSearchCommand.of(condition);

		given(bookRepositoryPort.getNearByBookLocation(command)).willReturn(locations);

		List<BookCountPerSectorResponse> bookCountPerSector = bookQueryService.getBookCountPerSector(command);
		System.out.println(bookCountPerSector);
		Map<Integer,Long> testMap = new HashMap<>();
		for (BookCountPerSectorResponse bookCountPerSectorResponse : bookCountPerSector) {
			testMap.put(bookCountPerSectorResponse.getSector(),bookCountPerSectorResponse.getBookCount());
		}

		assertAll(() -> assertThat(bookCountPerSector).hasSize(4),
			()-> assertThat(testMap).containsEntry(2,1L),
			()-> assertThat(testMap).containsEntry(5,2L),
			()-> assertThat(testMap).containsEntry(7,1L),
			()-> assertThat(testMap).containsEntry(9,1L)
		);


	}

	@Test
	@DisplayName("섹터당 책 목록을 무한스크롤해서 뿌려준다.")
	void bookSector_InfinityScroll() {
		//given
		//가로세로 2km 3x3 중앙 봉천역 기준
		//5섹터
		Location 봉천역 = LocationStub.봉천역.of();

		BookSearchCondition condition = new BookSearchCondition(null,봉천역.getLongitude(),봉천역.getLatitude(),40,40,null,3);

		PageRequestImpl pageRequestImpl = new PageRequestImpl(1L);
		Book book = BOOK1.of(1L);
		BookSearchCommand command = BookSearchCommand.of(condition);
		SliceImpl<BookSimpleResponse> slice = new SliceImpl<>(List.of(BookSimpleResponse.builder().bookId(book.getId())
			.bookImage(book.getImgUrl())
			.location(book.getLocation())
			.rentalFee(book.getRentalFee())
			.title(book.getBookProduct().getTitle())
			.status(book.getBookState())
			.build()), org.springframework.data.domain.PageRequest.of(0, 6), false);

		given(bookRepositoryPort.getAll(command, pageRequestImpl)).willReturn(slice);

		SliceImpl<BookSimpleResponse> result = bookQueryService.getList(command, pageRequestImpl);
		assertThat(result.getContent().get(0).getTitle()).isEqualTo("자바의 정석");
	}

	@Test
	@DisplayName("책 한권을 가져온다.")
	void getByBookId(){
		Long bookId = 1L;
		Book book = BOOK1.of(bookId);
		when(bookRepositoryPort.findById(1L)).thenReturn(Optional.ofNullable(book));
		assertThat(bookQueryService.getByBookId(1L).getId()).isEqualTo(1L);
	}

	@Test
	@DisplayName("책 한권과 그 책을 등록한 회원을 가져온다.")
	void getWithMerchantByBookId(){
		Long bookId = 1L;
		Book book = BOOK1.of(bookId);
		when(bookRepositoryPort.findWithMerchantByBookId(1L)).thenReturn(Optional.ofNullable(book));
		assertThat(bookQueryService.getWithMerchantByBookId(1L).getId()).isEqualTo(1L);
	}

	@Test
	@DisplayName("회원 A가 등록한 모든 책을 가져온다.")
	void getListByMember(){
		Long memberId = 1L;
		Member member = MEMBER1.of(memberId);
		Book book = BOOK1.of();
		BookSimpleResponse response = BookSimpleResponse.builder()
			.merchantName(member.getNickname())
			.bookImage(book.getImgUrl())
			.rentalFee(book.getRentalFee())
			.location(book.getLocation())
			.status(book.getBookState())
			.bookId(book.getId())
			.title(book.getBookProduct().getTitle())
			.build();
		SliceImpl<BookSimpleResponse> slice = new SliceImpl(List.of(response), PageRequest.of(0, 6), false);

		when(bookRepositoryPort.getListByMember(memberId,new PageRequestImpl(1L))).thenReturn(slice);
		assertThat(bookQueryService.getListByMember(memberId,new PageRequestImpl(1L)).getContent().get(0).getTitle()).isEqualTo(book.getBookProduct().getTitle());

	}

}