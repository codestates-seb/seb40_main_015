package com.dongnebook.domain.review.application;

import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dongnebook.domain.member.domain.Member;
import com.dongnebook.domain.rental.domain.Rental;
import com.dongnebook.domain.rental.domain.RentalState;
import com.dongnebook.domain.rental.exception.RentalNotFoundException;
import com.dongnebook.domain.rental.repository.RentalQueryRepository;
import com.dongnebook.domain.review.domain.Review;
import com.dongnebook.domain.review.dto.request.ReviewRequest;
import com.dongnebook.domain.review.dto.response.ReviewResponse;
import com.dongnebook.domain.review.exception.BookRentalNotMatchException;
import com.dongnebook.domain.review.repository.ReviewQueryRepository;
import com.dongnebook.domain.review.repository.ReviewRepository;
import com.dongnebook.global.dto.request.PageRequestImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewQueryRepository reviewQueryRepository;
    private final RentalQueryRepository rentalQueryRepository;

    @Transactional
    public void createReview(Long rentalId, ReviewRequest reviewRequest, Long customerId){
        Rental rental = getRental(rentalId);
        checkBookRentalMatch(rental, reviewRequest.getBookId());
        checkRentalPerson(rental, customerId);
        rental.changeRentalStateFromTo(RentalState.RETURN_UNREVIEWED, RentalState.RETURN_REVIEWED);

        Member merchant = rental.getBook().getMember();
        merchant.setAvgGradeAndUpCount(reviewRequest.getGrade());

        Review review = Review.create(reviewRequest.getReviewMessage(), reviewRequest.getGrade(), rental, merchant);
        reviewRepository.save(review);
    }

    public SliceImpl<ReviewResponse> readReviews(Long merchantId, PageRequestImpl pageRequestImpl){
        return reviewQueryRepository.findAllByMerchantIdOrderByIdDesc(merchantId, pageRequestImpl);
    }


    private void checkRentalPerson(Rental rental, Long customerId) {
        if(!rental.getCustomer().getId().equals(customerId)){
            throw new RentalNotFoundException();
        }
    }

    private void checkBookRentalMatch(Rental rental, Long bookId) {
        if(!rental.getBook().getId().equals(bookId)){
            throw new BookRentalNotMatchException();
        }
    }

    private Rental getRental(Long rentalId) {
        return rentalQueryRepository.findRentalById(rentalId).orElseThrow(RentalNotFoundException::new);
    }
}
