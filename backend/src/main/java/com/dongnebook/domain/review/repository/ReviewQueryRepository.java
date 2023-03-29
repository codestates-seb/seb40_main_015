package com.dongnebook.domain.review.repository;

import static com.dongnebook.domain.rental.domain.QRental.*;
import static com.dongnebook.domain.review.domain.QReview.*;

import java.util.List;

import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import com.dongnebook.domain.review.dto.response.QReviewResponse;
import com.dongnebook.domain.review.dto.response.ReviewResponse;
import com.dongnebook.global.dto.request.PageRequestImpl;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ReviewQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public SliceImpl<ReviewResponse> findAllByMerchantIdOrderByIdDesc(Long merchantId, PageRequestImpl pageRequestImpl){
        List<ReviewResponse> reviews = jpaQueryFactory
            .select(new QReviewResponse(
                        review.id,
                        review.rental.customer.id,
                        review.rental.customer.nickname,
                        review.rental.customer.avatarUrl,
                        review.reviewMessage,
                        review.grade,
//                        review.rental.book.bookProduct.title,
                        review.rental.book.imgUrl,
                        review.rental.book.id,
                        review.rental.id
                    )
                )
                .from(review)
                .where(ltReviewId(pageRequestImpl.getIndex()),
                        review.merchant.id.eq(merchantId))
                .innerJoin(review.rental, rental)
                .innerJoin(rental.book)
                .innerJoin(rental.customer)
                .orderBy(review.id.desc())
                .limit(pageRequestImpl.getSize() + 1)
                .fetch();

        boolean hasNext = false;

        if(reviews.size() > pageRequestImpl.getSize()){
            hasNext = true;
            reviews.remove(pageRequestImpl.getSize().intValue());
        }

        return new SliceImpl<>(reviews, pageRequestImpl.of(), hasNext);
    }

    private BooleanExpression ltReviewId(Long reviewId){
        if(reviewId == null){
            return null;
        }

        return review.id.lt(reviewId);
    }
}
