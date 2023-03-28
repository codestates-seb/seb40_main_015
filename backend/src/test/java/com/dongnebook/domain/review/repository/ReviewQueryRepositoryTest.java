package com.dongnebook.domain.review.repository;

import com.dongnebook.config.TestConfig;
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
import com.dongnebook.domain.review.dto.response.ReviewResponse;
import com.dongnebook.global.dto.request.PageRequestImpl;
import com.dongnebook.support.DataClearExtension;
import com.dongnebook.support.DatabaseCleaner;
import com.github.gavlyukovskiy.boot.jdbc.decorator.DataSourceDecoratorAutoConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.SliceImpl;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ImportAutoConfiguration(DataSourceDecoratorAutoConfiguration.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({TestConfig.class, DatabaseCleaner.class})
@ExtendWith(DataClearExtension.class)
public class ReviewQueryRepositoryTest {
    @Autowired
    private ReviewQueryRepository reviewQueryRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private BookCommandRepository bookCommandRepository;
    @Autowired
    private RentalRepository rentalRepository;


    @BeforeEach
    public void setting() {
        Member member1 = Member.builder()
                .userId("test")
                .avatarUrl("aaa@aa.com")
                .nickname("tester")
                .password("12341234")
                .build();
        Member savedMember = memberRepository.save(member1);

        BookProduct bookProduct = new BookProduct("자바의 정석", "남궁성", "도우출판");
        Book book = Book.create(bookProduct, "aaa@abc.com", "기본이 중요하다", Money.of(1000), new Location(37.5340, 126.7064), savedMember);
        //        Book book = Book.builder()
//                .id(1L)
//                .bookProduct(bookProduct)
//                .imgUrl("aaa@abc.com")
//                .description("기본이 중요하다")
//                .rentalFee(Money.of(1000))
//                .location(new Location(37.5340, 126.7064))
//                .member(savedMember)
//                .build();
        Book savedBook = bookCommandRepository.save(book);

        Member member2 = Member.builder()
                .userId("test2")
                .avatarUrl("aaa@aa.com")
                .nickname("tester2")
                .password("12341234")
                .build();
        Member savedMember2 = memberRepository.save(member2);

        Rental rental1 = Rental.create(savedBook, savedMember2);
        Rental savedRental1 = rentalRepository.save(rental1);
        Rental rental2 = Rental.create(savedBook, savedMember2);
        Rental savedRental2 = rentalRepository.save(rental2);
        Rental rental3 = Rental.create(savedBook, savedMember2);
        Rental savedRental3 = rentalRepository.save(rental3);
        Rental rental4 = Rental.create(savedBook, savedMember2);
        Rental savedRental4 = rentalRepository.save(rental4);
        Rental rental5 = Rental.create(savedBook, savedMember2);
        Rental savedRental5 = rentalRepository.save(rental5);
        Rental rental6 = Rental.create(savedBook, savedMember2);
        Rental savedRental6 = rentalRepository.save(rental6);
        Rental rental7 = Rental.create(savedBook, savedMember2);
        Rental savedRental7 = rentalRepository.save(rental7);

//        Rental rental1 = Rental.builder()
//                .rentalDeadLine(LocalDateTime.now())
//                .rentalState(RentalState.TRADING)
//                .merchantId(savedMember.getId())
//                .book(savedBook)
//                .customer(savedMember2)
//                .build();
//        rentalRepository.save(rental1);
//
//        Rental rental2 = Rental.builder()
//                .rentalDeadLine(LocalDateTime.now())
//                .rentalState(RentalState.TRADING)
//                .merchantId(savedMember.getId())
//                .book(savedBook)
//                .customer(savedMember2)
//                .build();
//        rentalRepository.save(rental2);
//
//        Rental rental3 = Rental.builder()
//                .rentalDeadLine(LocalDateTime.now())
//                .rentalState(RentalState.TRADING)
//                .merchantId(savedMember.getId())
//                .book(savedBook)
//                .customer(savedMember2)
//                .build();
//        rentalRepository.save(rental3);
//
//        Rental rental4 = Rental.builder()
//                .rentalDeadLine(LocalDateTime.now())
//                .rentalState(RentalState.TRADING)
//                .merchantId(savedMember.getId())
//                .book(savedBook)
//                .customer(savedMember2)
//                .build();
//        rentalRepository.save(rental4);
//
//        Rental rental5 = Rental.builder()
//                .rentalDeadLine(LocalDateTime.now())
//                .rentalState(RentalState.TRADING)
//                .merchantId(savedMember.getId())
//                .book(savedBook)
//                .customer(savedMember2)
//                .build();
//        rentalRepository.save(rental5);
//
//        Rental rental6 = Rental.builder()
//                .rentalDeadLine(LocalDateTime.now())
//                .rentalState(RentalState.TRADING)
//                .merchantId(savedMember.getId())
//                .book(savedBook)
//                .customer(savedMember2)
//                .build();
//        rentalRepository.save(rental6);
//
//        Rental rental7 = Rental.builder()
//                .rentalDeadLine(LocalDateTime.now())
//                .rentalState(RentalState.TRADING)
//                .merchantId(savedMember.getId())
//                .book(savedBook)
//                .customer(savedMember2)
//                .build();
//        rentalRepository.save(rental7);

        ReviewRequest reviewRequest = ReviewRequest.builder()
                .bookId(1L)
                .reviewMessage("good")
                .grade(1L)
                .build();

        Review review1 = Review.create(reviewRequest.getReviewMessage(), reviewRequest.getGrade(), savedRental1, savedMember);
        Review savedReview = reviewRepository.save(review1);
        Review review2 = Review.create(reviewRequest.getReviewMessage(), reviewRequest.getGrade(), savedRental2, savedMember);
        Review savedReview2 = reviewRepository.save(review2);
        Review review3 = Review.create(reviewRequest.getReviewMessage(), reviewRequest.getGrade(), savedRental3, savedMember);
        reviewRepository.save(review3);
        Review review4 = Review.create(reviewRequest.getReviewMessage(), reviewRequest.getGrade(), savedRental4, savedMember);
        reviewRepository.save(review4);
        Review review5 = Review.create(reviewRequest.getReviewMessage(), reviewRequest.getGrade(), savedRental5, savedMember);
        reviewRepository.save(review5);
        Review review6 = Review.create(reviewRequest.getReviewMessage(), reviewRequest.getGrade(), savedRental6, savedMember);
        reviewRepository.save(review6);
        Review review7 = Review.create(reviewRequest.getReviewMessage(), reviewRequest.getGrade(), savedRental7, savedMember);
        reviewRepository.save(review7);

        System.out.println(savedReview.getRental().equals(savedRental1));
        assertThat(savedReview.getRental()).isEqualTo(savedRental1);

    }

//    @Test
    public void findAllByMerchantIdOrderByIdDescTest1(){
        // given
        Long merchantId = 1L;
        PageRequestImpl pageRequest = new PageRequestImpl(1L);

        // when
        SliceImpl<ReviewResponse> reviewResponseSlice = reviewQueryRepository.findAllByMerchantIdOrderByIdDesc(merchantId, pageRequest);

        // then
        assertThat(reviewResponseSlice.hasNext()).isEqualTo(false);
    }

//    @Test
    public void findAllByMerchantIdOrderByIdDescTest2(){
        // given
        Long merchantId = 1L;
        PageRequestImpl pageRequest = new PageRequestImpl(null);

        // when
        SliceImpl<ReviewResponse> reviewResponseSlice = reviewQueryRepository.findAllByMerchantIdOrderByIdDesc(merchantId, pageRequest);

        // then
        assertThat(reviewResponseSlice.hasNext()).isEqualTo(true);
    }
}
