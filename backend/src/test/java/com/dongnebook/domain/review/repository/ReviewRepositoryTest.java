package com.dongnebook.domain.review.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.dongnebook.domain.book.adapter.out.BookCommandRepository;
import com.dongnebook.domain.book.domain.Book;
import com.dongnebook.domain.book.domain.BookProduct;
import com.dongnebook.domain.book.domain.Money;
import com.dongnebook.domain.member.domain.Member;
import com.dongnebook.domain.member.repository.MemberRepository;
import com.dongnebook.domain.model.Location;
import com.dongnebook.domain.rental.domain.Rental;
import com.dongnebook.domain.rental.domain.RentalState;
import com.dongnebook.domain.rental.repository.RentalRepository;
import com.dongnebook.domain.review.domain.Review;
import com.dongnebook.domain.review.dto.request.ReviewRequest;

@DataJpaTest(showSql = false)
class ReviewRepositoryTest {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private BookCommandRepository bookCommandRepository;
    @Autowired
    private RentalRepository rentalRepository;

    @Test
    void saveReviewTest() {
        // given
        Member member1 = Member.builder()
                .userId("test")
                .avatarUrl("aaa@aa.com")
                .nickname("tester")
                .password("12341234")
                .build();
        Member savedMember = memberRepository.save(member1);

        BookProduct bookProduct = new BookProduct("수학의 정석1", "남궁성", "도우출판");
        Book book = Book.builder()
                .id(1L)
                .bookProduct(bookProduct)
                .imgUrl("aaa@abc.com")
                .description("기본이 중요하다")
                .rentalFee(Money.of(1000))
                .location(new Location(37.5340, 126.7064))
                .member(savedMember)
                .build();
        Book savedBook = bookCommandRepository.save(book);

        Member member2 = Member.builder()
                .userId("test2")
                .avatarUrl("aaa@aa.com")
                .nickname("tester2")
                .password("12341234")
                .build();
        Member savedMember2 = memberRepository.save(member2);

        Rental rental = Rental.builder()
                .rentalDeadLine(LocalDateTime.now())
                .rentalState(RentalState.TRADING)
                .merchantId(savedMember.getId())
                .book(savedBook)
                .customer(savedMember2)
                .build();
        rentalRepository.save(rental);

        ReviewRequest reviewRequest = ReviewRequest.builder()
                .bookId(1L)
                .reviewMessage("good")
                .grade(1L)
                .build();

        Review review = Review.create(reviewRequest.getReviewMessage(), reviewRequest.getGrade(), rental, member2);

        // when
        Review savedReview = reviewRepository.save(review);

        // then
        assertNotNull(savedReview);
        assertEquals(savedReview.getReviewMessage(), review.getReviewMessage());
        assertEquals(savedReview.getGrade(), review.getGrade());
        assertEquals(savedReview.getRental(), review.getRental());
        assertEquals(savedReview.getMerchant(), review.getMerchant());
    }
}
