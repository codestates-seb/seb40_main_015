package com.dongnebook.domain.review.application;

import com.dongnebook.domain.book.domain.Book;
import com.dongnebook.domain.book.domain.BookProduct;
import com.dongnebook.domain.book.domain.Money;
import com.dongnebook.domain.member.domain.Member;
import com.dongnebook.domain.model.Location;
import com.dongnebook.domain.rental.domain.Rental;
import com.dongnebook.domain.rental.domain.RentalState;
import com.dongnebook.domain.rental.exception.RentalNotFoundException;
import com.dongnebook.domain.rental.repository.RentalQueryRepository;
import com.dongnebook.domain.review.dto.request.ReviewRequest;
import com.dongnebook.domain.review.exception.BookRentalNotMatchException;
import com.dongnebook.domain.review.repository.ReviewQueryRepository;
import com.dongnebook.domain.review.repository.ReviewRepository;
import com.dongnebook.global.dto.request.PageRequestImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {
    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private ReviewQueryRepository reviewQueryRepository;
    @Mock
    private RentalQueryRepository rentalQueryRepository;
    @InjectMocks
    private ReviewService reviewService;

    @Test
    public void RentalNotFoundExceptionTest() {
        // given
        Long rentalId = 1L;
        ReviewRequest reviewRequest = ReviewRequest.builder()
                .bookId(1L)
                .reviewMessage("good")
                .grade(1L)
                .build();
        Long customerId = 1L;
        Optional<Rental> rentalOptional = Optional.empty();

        given(rentalQueryRepository.findRentalById(Mockito.anyLong())).willReturn(rentalOptional);

        // when & then
        assertThrows(RentalNotFoundException.class, () -> reviewService.createReview(rentalId, reviewRequest, customerId));
    }

    @Test
    public void checkBookRentalMatchTest() {
        // given
        Long rentalId = 1L;
        ReviewRequest reviewRequest = ReviewRequest.builder()
                .bookId(2L)
                .reviewMessage("good")
                .grade(1L)
                .build();
        Long customerId = 2L;

        Long merchantId = 1L;
        Member merchant = new Member(merchantId, "test1", "tester1", new Location(37.5340, 126.7064), "anywhere", "abc1@abc.com");
        Book book = new Book(1L, new BookProduct(), "aaa@abc.com", "기본이 중요하다", Money.of(1000), new Location(37.5340, 126.7064), merchant);
        Member customer = new Member(customerId, "test2", "tester2", new Location(37.5340, 126.7064), "anywhere", "abc2@abc.com");

        Rental rental = new Rental(LocalDateTime.now(), RentalState.TRADING, merchantId, book, customer);
        Optional<Rental> rentalOptional = Optional.of(rental);

        given(rentalQueryRepository.findRentalById(Mockito.anyLong())).willReturn(rentalOptional);

        // when & then
        assertThrows(BookRentalNotMatchException.class, () -> reviewService.createReview(rentalId, reviewRequest, customerId));
    }

    @Test
    public void checkRentalPersonTest() {
        // given
        Long rentalId = 1L;
        Long bookId = 1L;
        ReviewRequest reviewRequest = ReviewRequest.builder()
                .bookId(1L)
                .reviewMessage("good")
                .grade(1L)
                .build();
        Long customerId = 2L;

        Long merchantId = 1L;
        Member merchant = new Member(merchantId, "test1", "tester1", new Location(37.5340, 126.7064), "anywhere", "abc1@abc.com");
        Book book = new Book(bookId, new BookProduct(), "aaa@abc.com", "기본이 중요하다", Money.of(1000), new Location(37.5340, 126.7064), merchant);
        Member customer = new Member(3L, "test2", "tester2", new Location(37.5340, 126.7064), "anywhere", "abc2@abc.com");

        Rental rental = new Rental(LocalDateTime.now(), RentalState.TRADING, merchantId, book, customer);
        Optional<Rental> rentalOptional = Optional.of(rental);

        given(rentalQueryRepository.findRentalById(Mockito.anyLong())).willReturn(rentalOptional);

        // when & then
        assertThrows(RentalNotFoundException.class, () -> reviewService.createReview(rentalId, reviewRequest, customerId));
    }

    @Test
    public void createReviewTest() {
        // given
        Long rentalId = 1L;
        Long bookId = 1L;
        ReviewRequest reviewRequest = ReviewRequest.builder()
                .bookId(1L)
                .reviewMessage("good")
                .grade(1L)
                .build();
        Long customerId = 2L;

        Long merchantId = 1L;
        Member merchant = new Member(merchantId, "test1", "tester1", new Location(37.5340, 126.7064), "anywhere", "abc1@abc.com");
        Book book = new Book(bookId, new BookProduct(), "aaa@abc.com", "기본이 중요하다", Money.of(1000), new Location(37.5340, 126.7064), merchant);
        Member customer = new Member(customerId, "test2", "tester2", new Location(37.5340, 126.7064), "anywhere", "abc2@abc.com");

        Rental rental = new Rental(LocalDateTime.now(), RentalState.TRADING, merchantId, book, customer);
        rental.changeRentalStateFromTo(rental.getRentalState(), RentalState.RETURN_UNREVIEWED);
        Optional<Rental> rentalOptional = Optional.of(rental);

        given(rentalQueryRepository.findRentalById(Mockito.anyLong())).willReturn(rentalOptional);

        // when & then
        assertDoesNotThrow(() -> reviewService.createReview(rentalId, reviewRequest, customerId));
    }

    @Test
    public void readReviewsTest() {
        // given
        Long merchantId = 1L;
        PageRequestImpl pageRequest = new PageRequestImpl(1L);

        // when & then
        assertDoesNotThrow(() -> reviewService.readReviews(merchantId, pageRequest));
    }
}
