package com.dongnebook.domain.dibs.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.SliceImpl;

import com.dongnebook.domain.book.application.BookQueryService;
import com.dongnebook.domain.book.application.port.in.response.BookSimpleResponse;
import com.dongnebook.domain.book.application.port.out.BookRepositoryPort;
import com.dongnebook.domain.book.domain.Book;
import com.dongnebook.domain.book.domain.BookProduct;
import com.dongnebook.domain.book.domain.BookState;
import com.dongnebook.domain.book.domain.Money;
import com.dongnebook.domain.dibs.domain.Dibs;
import com.dongnebook.domain.dibs.repository.DibsRepository;
import com.dongnebook.domain.dibs.service.DibsService;
import com.dongnebook.domain.member.application.MemberService;
import com.dongnebook.domain.member.domain.Member;
import com.dongnebook.domain.model.Location;
import com.dongnebook.global.dto.request.PageRequestImpl;

@ExtendWith(MockitoExtension.class)
class DibsServiceTest {
    @Mock
    private DibsRepository dibsRepository;
    @Mock
    private MemberService memberService;
    @Mock
    private BookQueryService bookQueryService;
    @Mock
    private BookRepositoryPort bookRepositoryPort;
    @InjectMocks
    private DibsService dibsService;

    @Test
    void checkAlreadyDibsSaveTest() throws Exception {
        // given
        Long bookId = 1L;
        Long memberId = 3L;

        Member merchant = new Member(1L, "test1", "tester1", new Location(37.5340, 126.7064), "anywhere", "abc1@abc.com");
        Book book = new Book(bookId, new BookProduct(), "aaa@abc.com", "기본이 중요하다", Money.of(1000), new Location(37.5340, 126.7064), merchant);

        Member newCustomer = new Member(memberId, "test3", "tester3", new Location(37.5340, 126.7064), "anywhere", "abc3@abc.com");

        Optional<Dibs> dibsOptional = Optional.empty();

        given(bookQueryService.getByBookId(any())).willReturn(book);
        given(memberService.getById(any())).willReturn(newCustomer);
        given(dibsRepository.findByBookAndMemberOrderByIdDesc(any(), any())).willReturn(dibsOptional);

        // when & then
        assertDoesNotThrow(() -> dibsService.doDibs(bookId, memberId));
    }

    @Test
    void checkAlreadyDibsDeleteTest() throws Exception {
        // given
        Long bookId = 1L;
        Long memberId = 3L;

        Member merchant = new Member(1L, "test1", "tester1", new Location(37.5340, 126.7064), "anywhere", "abc1@abc.com");
        Book book = new Book(bookId, new BookProduct(), "aaa@abc.com", "기본이 중요하다", Money.of(1000), new Location(37.5340, 126.7064), merchant);

        Member newCustomer = new Member(memberId, "test3", "tester3", new Location(37.5340, 126.7064), "anywhere", "abc3@abc.com");

        Dibs dibs = Dibs.of(newCustomer, book);
        Optional<Dibs> dibsOptional = Optional.of(dibs);

        given(bookQueryService.getByBookId(any())).willReturn(book);
        given(memberService.getById(any())).willReturn(newCustomer);
        given(dibsRepository.findByBookAndMemberOrderByIdDesc(any(), any())).willReturn(dibsOptional);

        // when & then
        assertDoesNotThrow(() -> dibsService.doDibs(bookId, memberId));
    }

    @Test
    void findAllTest() throws Exception {
        // given
        PageRequestImpl pageRequest = new PageRequestImpl(1L);
        Long memberId = 1L;

        BookSimpleResponse bookSimpleResponse1 = new BookSimpleResponse(
                1L, "자바의 정석1", BookState.RENTABLE, "aa1@aaa.com", Money.of(1000),
                new Location(37.5340, 126.7064), "test1"
        );
        BookSimpleResponse bookSimpleResponse2 = new BookSimpleResponse(
                2L, "자바의 정석2", BookState.RENTABLE, "aa2@aaa.com", Money.of(1000),
                new Location(37.5340, 126.7064), "test1"
        );
        SliceImpl<BookSimpleResponse> bookSimpleResponseSlice = new SliceImpl<>(
                List.of(bookSimpleResponse1, bookSimpleResponse2)
        );

        // when
        given(dibsService.findAll(any(), any())).willReturn(bookSimpleResponseSlice);

        SliceImpl<BookSimpleResponse> gotBookSimpleResponseSlice = dibsService.findAll(pageRequest, memberId);

        // then
        assertEquals(bookSimpleResponseSlice.getContent().size(), gotBookSimpleResponseSlice.getContent().size());
        assertEquals(bookSimpleResponseSlice.getContent().get(1).getBookId(),
                gotBookSimpleResponseSlice.getContent().get(1).getBookId());
    }

}
