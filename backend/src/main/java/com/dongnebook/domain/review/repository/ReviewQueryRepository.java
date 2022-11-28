package com.dongnebook.domain.review.repository;

import com.dongnebook.domain.review.dto.response.QReviewResponse;
import com.dongnebook.domain.review.dto.response.ReviewResponse;
import com.dongnebook.global.dto.request.PageRequest;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.dongnebook.domain.rental.domain.QRental.rental;
import static com.dongnebook.domain.review.domain.QReview.review;

@Repository
@RequiredArgsConstructor
public class ReviewQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public SliceImpl<ReviewResponse> findAllByMerchantIdOrderByIdDesc(Long merchantId, PageRequest pageRequest){

        List<ReviewResponse> reviews = jpaQueryFactory.select(new QReviewResponse(
                        review.id,
                        review.rental.customer.nickname,
                        review.rental.customer.avatarUrl,
                        review.reviewMessage,
                        review.grade,
                        review.rental.book.title,
                        review.rental.book.id,
                        review.rental.id
                    )
                )
                .from(review)
                .where(ltReviewId(pageRequest.getIndex()),
                        review.merchant.id.eq(merchantId))
                .innerJoin(review.rental, rental)
                .innerJoin(rental.book)
                .innerJoin(rental.customer)
                .orderBy(review.id.desc())
                .limit(pageRequest.getSize() + 1)
                .fetch();

        boolean hasNext = false;

        if(reviews.size() > pageRequest.getSize()){
            hasNext = true;
            reviews.remove(pageRequest.getSize().intValue());
        }

        return new SliceImpl<>(reviews, pageRequest.of(), hasNext);
    }

    private BooleanExpression ltReviewId(Long reviewId){
        if(reviewId == null){
            return null;
        }
        return review.id.lt(reviewId);
    }

}
