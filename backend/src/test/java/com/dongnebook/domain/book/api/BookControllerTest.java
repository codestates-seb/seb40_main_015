package com.dongnebook.domain.book.api;

import static org.mockito.BDDMockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.dongnebook.domain.book.adapter.in.BookController;
import com.dongnebook.domain.book.adapter.in.BookInfoFetcher;
import com.dongnebook.domain.book.adapter.in.BookInfoParser;
import com.dongnebook.domain.book.adapter.in.request.BookPostEditRequest;
import com.dongnebook.domain.book.adapter.in.request.BookPostRegisterRequest;
import com.dongnebook.domain.book.adapter.in.request.BookSearchCondition;
import com.dongnebook.domain.book.application.port.in.BookPostCommandUseCase;
import com.dongnebook.domain.book.application.port.in.BookPostQueryUseCase;
import com.dongnebook.domain.book.application.port.in.request.BookPostEditCommand;
import com.dongnebook.domain.book.application.port.in.request.BookPostRegisterCommand;
import com.dongnebook.domain.book.application.port.in.request.BookSearchCommand;
import com.dongnebook.domain.book.application.port.in.response.BookCountPerSectorResponse;
import com.dongnebook.domain.book.application.port.in.response.BookDetailMemberResponse;
import com.dongnebook.domain.book.application.port.in.response.BookDetailResponse;
import com.dongnebook.domain.book.application.port.in.response.BookResponse;
import com.dongnebook.domain.book.application.port.in.response.BookSimpleResponse;
import com.dongnebook.domain.book.domain.Book;
import com.dongnebook.domain.book.domain.BookState;
import com.dongnebook.domain.member.domain.Member;
import com.dongnebook.domain.model.Location;
import com.dongnebook.global.dto.request.PageRequestImpl;
import com.dongnebook.support.BookStub;
import com.dongnebook.support.LocationStub;
import com.dongnebook.support.MemberStub;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest({BookController.class})
@MockBean(JpaMetamodelMappingContext.class)
class BookControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private BookPostQueryUseCase bookPostQueryUseCase;

	@MockBean
	private BookPostCommandUseCase bookPostCommandUseCase;

	@MockBean
	BookInfoFetcher bookInfoFetcher;

	@MockBean
	BookInfoParser bookInfoParser;

	@Autowired
	ObjectMapper objectMapper;

	private static final String BASE = "/books";

	@Test
	@WithMockUser
	@DisplayName("책을 생성한다.")
	void test1() throws Exception {
		Book book = BookStub.BOOK1.of();
		BookPostRegisterRequest request = BookPostRegisterRequest.builder()
			.author(book.getBookProduct().getAuthor())
			.description(book.getDescription())
			.imageUrl(book.getImgUrl())
			.publisher(book.getBookProduct().getPublisher())
			.rentalFee(book.getRentalFee().getValue())
			.title(book.getBookProduct().getTitle()).build();

		when(bookPostCommandUseCase.register(any(), any())).thenReturn(1L);
		mockMvc.perform(post(BASE)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request))
				.with(csrf()))
			.andExpect(status().isCreated())
			.andExpect(header().string("Location", "http://localhost/books/1"));

		then(bookPostCommandUseCase).should().register(eq(BookPostRegisterCommand.of(request)), any());
	}

	@Test
	@WithMockUser
	@DisplayName("책을 수정한다.")
	void test2() throws Exception {
		Book book = BookStub.BOOK1.of();
		BookPostEditRequest request = BookPostEditRequest.builder()
			.description(book.getDescription())
			.imageUrl(book.getImgUrl())
			.description(book.getDescription())
			.build();
		long bookId = 1L;
		mockMvc.perform(patch(BASE + "/" + bookId)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request))
				.with(csrf()))
			.andExpect(status().isOk());

		then(bookPostCommandUseCase).should().edit(eq(BookPostEditCommand.of(request)), any(), any());
	}

	@Test
	@WithMockUser
	@DisplayName("책 정보를 가져온다.")
	void test3() throws Exception {
		long bookId = 1L;
		Book book = BookStub.BOOK1.of();
		Member member = MemberStub.MEMBER1.of();
		BookDetailMemberResponse memberResponse = BookDetailMemberResponse.builder()
			.avatarUrl(member.getAvatarUrl()).
			merchantId(member.getId())
			.grade(member.getAvgGrade())
			.name(member.getNickname())
			.build();

		BookResponse bookResponse = BookResponse.builder()
			.bookId(bookId)
			.bookImgUrl(book.getImgUrl())
			.author(book.getBookProduct().getAuthor())
			.title(book.getBookProduct().getTitle())
			.content(book.getDescription())
			.publisher(book.getBookProduct().getPublisher())
			.dibsId(null)
			.rentalFee(1000)
			.rentalEnd(LocalDateTime.now().plusDays(1))
			.rentalStart(LocalDateTime.now())
			.state(BookState.RENTABLE)
			.build();
		BookDetailResponse bookDetailResponse = BookDetailResponse.builder()
			.book(bookResponse)
			.merchant(memberResponse)
			.build();
		when(bookPostQueryUseCase.getDetail(anyLong(), any())).thenReturn(bookDetailResponse);
		mockMvc.perform(get(BASE + "/" + bookId)
				.contentType(MediaType.APPLICATION_JSON)
				.with(csrf()))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.book.title").value(book.getBookProduct().getTitle()));
	}

	@Test
	@WithMockUser
	@DisplayName("책 리스트를 가져온다.")
	void test4() throws Exception {
		Book book = BookStub.BOOK1.of();
		Location location = LocationStub.봉천역.of();
		BookSearchCondition condition = BookSearchCondition.builder()
			.bookTitle(book.getBookProduct().getTitle())
			.height(200)
			.latitude(location.getLatitude())
			.level(3)
			.longitude(location.getLongitude())
			.sector(5)
			.width(200)
			.bookTitle(book.getBookProduct().getTitle())
			.build();
		PageRequestImpl pageRequest = PageRequestImpl.of(1L);

		BookSimpleResponse result = BookSimpleResponse.builder()
			.bookId(book.getId())
			.bookImage(book.getImgUrl())
			.location(book.getLocation())
			.rentalFee(book.getRentalFee())
			.status(book.getBookState())
			.merchantName("이성준")
			.title(book.getBookProduct().getTitle())
			.build();
		SliceImpl<BookSimpleResponse> slice = new SliceImpl(List.of(result), pageRequest.of(), true);
		when(bookPostQueryUseCase.getList(any(BookSearchCommand.class), any(PageRequestImpl.class))).thenReturn(slice);

		mockMvc.perform(get(BASE)
				.param("latitude", condition.getLatitude().toString())
				.param("longitude", condition.getLongitude().toString())
				.param("sector", condition.getSector().toString())
				.param("height", condition.getHeight().toString())
				.param("width", condition.getWidth().toString())
				.param("level", condition.getLevel().toString())
				.param("index", "1")
				.contentType(MediaType.APPLICATION_JSON)
				.with(csrf()))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.content[0].title").value("자바의 정석"))
			.andExpect(jsonPath("$.content[0].status").value("대여가능"))
			.andExpect(jsonPath("$.content[0].bookImage").value("이미지 주소"))
			.andExpect(jsonPath("$.content[0].rentalFee").value(1000))
			.andExpect(jsonPath("$.content[0].location.latitude").value(37.482475661))
			.andExpect(jsonPath("$.content[0].location.longitude").value(126.941669283))
			.andExpect(jsonPath("$.content[0].merchantName").value("이성준"))
			.andExpect(jsonPath("$.pageable.pageSize").value(6))
			.andExpect(jsonPath("$.first").value(true))
			.andExpect(jsonPath("$.last").value(false));
	}
	@Test
	@WithMockUser
	@DisplayName("섹터 당 책 갯수를 가져온다.")
	void test5() throws Exception {
		Book book = BookStub.BOOK1.of();
		Location location = LocationStub.봉천역.of();
		BookSearchCondition condition = BookSearchCondition.builder()
			.bookTitle(book.getBookProduct().getTitle())
			.height(200)
			.latitude(location.getLatitude())
			.level(3)
			.longitude(location.getLongitude())
			.sector(5)
			.width(200)
			.bookTitle(book.getBookProduct().getTitle())
			.build();

		BookCountPerSectorResponse response = new BookCountPerSectorResponse(1L, 5, location);

		when(bookPostQueryUseCase.getBookCountPerSector(any(BookSearchCommand.class))).thenReturn(List.of(response));

		mockMvc.perform(get(BASE+"/count")
				.param("latitude", condition.getLatitude().toString())
				.param("longitude", condition.getLongitude().toString())
				.param("height", condition.getHeight().toString())
				.param("width", condition.getWidth().toString())
				.param("level", condition.getLevel().toString())
				.contentType(MediaType.APPLICATION_JSON)
				.with(csrf()))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$[0].bookCount").value(1))
			.andExpect(jsonPath("$[0].sector").value(5))
			.andExpect(jsonPath("$[0].location.latitude").value(37.482475661))
			.andExpect(jsonPath("$[0].location.longitude").value(126.941669283));
	}

	@Test
	@WithMockUser
	@DisplayName("섹터에 있는 모든 책을 가져온다.")
	void test6() throws Exception {
		Book book = BookStub.BOOK1.of();
		Location location = LocationStub.봉천역.of();
		BookSearchCondition condition = BookSearchCondition.builder()
			.bookTitle(book.getBookProduct().getTitle())
			.height(200)
			.latitude(location.getLatitude())
			.level(3)
			.longitude(location.getLongitude())
			.sector(5)
			.width(200)
			.bookTitle(book.getBookProduct().getTitle())
			.build();
		BookSimpleResponse result = BookSimpleResponse.builder()
			.bookId(book.getId())
			.bookImage(book.getImgUrl())
			.location(book.getLocation())
			.rentalFee(book.getRentalFee())
			.status(book.getBookState())
			.merchantName("이성준")
			.title(book.getBookProduct().getTitle())
			.build();
		PageRequestImpl pageRequest = PageRequestImpl.of(1L);
		SliceImpl<BookSimpleResponse> slice = new SliceImpl(List.of(result), pageRequest.of(), true);

		when(bookPostQueryUseCase.getList(any(BookSearchCommand.class),any(PageRequestImpl.class))).thenReturn(slice);

		mockMvc.perform(get(BASE+"/sector")
				.param("latitude", condition.getLatitude().toString())
				.param("longitude", condition.getLongitude().toString())
				.param("sector", condition.getSector().toString())
				.param("height", condition.getHeight().toString())
				.param("width", condition.getWidth().toString())
				.param("level", condition.getLevel().toString())
				.contentType(MediaType.APPLICATION_JSON)
				.with(csrf()))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.content[0].title").value("자바의 정석"))
			.andExpect(jsonPath("$.content[0].status").value("대여가능"))
			.andExpect(jsonPath("$.content[0].bookImage").value("이미지 주소"))
			.andExpect(jsonPath("$.content[0].rentalFee").value(1000))
			.andExpect(jsonPath("$.content[0].location.latitude").value(37.482475661))
			.andExpect(jsonPath("$.content[0].location.longitude").value(126.941669283))
			.andExpect(jsonPath("$.content[0].merchantName").value("이성준"));

	}

	@Test
	@WithMockUser
	@DisplayName("책을 삭제한다.")
	void test7() throws Exception {

		mockMvc.perform(delete(BASE+"/1")
				.contentType(MediaType.APPLICATION_JSON)
				.with(csrf()))
			.andExpect(status().isOk());

		then(bookPostCommandUseCase).should().delete(eq(1L), any());
	}

	@Test
	@WithMockUser
	@DisplayName("책 정보를 가져온다.")
	void test8() throws Exception {

		mockMvc.perform(get(BASE+"/bookInfo")
				.param("bookTitle", "자바의 정석")
			.contentType(MediaType.APPLICATION_JSON)
			.with(csrf()))
			.andExpect(status().isOk());

	}
}
