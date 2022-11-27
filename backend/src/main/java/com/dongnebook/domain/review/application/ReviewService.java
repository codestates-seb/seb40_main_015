package com.dongnebook.domain.review.application;

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
import com.dongnebook.global.dto.request.PageRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        Review review = Review.create(reviewRequest.getReviewMessage(), reviewRequest.getGrade(), rental, rental.getBook().getMember());
        reviewRepository.save(review);
    }

    public SliceImpl<ReviewResponse> readReviews(Long merchantId, PageRequest pageRequest){
        return reviewQueryRepository.findAllByMerchantIdOrderByIdDesc(merchantId, pageRequest);
    }


    private static void checkRentalPerson(Rental rental, Long customerId) {
        if(!rental.getCustomer().getId().equals(customerId)){
            throw new RentalNotFoundException();
        }
    }

    private static void checkBookRentalMatch(Rental rental, Long bookId) {
        if(!rental.getBook().getId().equals(bookId)){
            throw new BookRentalNotMatchException();
        }
    }

    private Rental getRental(Long rentalId) {
        Rental rental = rentalQueryRepository.getRentalById(rentalId);
        if(rental == null){
            throw new RentalNotFoundException();
        }
        return rental;
    }
}
