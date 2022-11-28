package com.dongnebook.domain.review.ui;

import com.dongnebook.domain.review.application.ReviewService;
import com.dongnebook.domain.review.dto.request.ReviewRequest;
import com.dongnebook.domain.review.dto.response.ReviewResponse;
import com.dongnebook.global.Login;
import com.dongnebook.global.config.security.auth.userdetails.AuthMember;
import com.dongnebook.global.dto.request.PageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.SliceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/review/{rentalId}")
    public ResponseEntity<Void> postReview(@PathVariable Long rentalId, @Valid @RequestBody ReviewRequest reviewRequest, @Login AuthMember customer){
        reviewService.createReview(rentalId, reviewRequest, customer.getMemberId());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/review/{merchantId}")
    public ResponseEntity<SliceImpl<ReviewResponse>> getReviews(@PathVariable Long merchantId, PageRequest pageRequest){
        return new ResponseEntity<>(reviewService.readReviews(merchantId, pageRequest), HttpStatus.OK);
    }

}
