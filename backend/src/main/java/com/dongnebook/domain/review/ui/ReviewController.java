package com.dongnebook.domain.review.ui;

import com.dongnebook.domain.review.application.ReviewService;
import com.dongnebook.domain.review.dto.request.ReviewRequest;
import com.dongnebook.global.Login;
import com.dongnebook.global.config.security.auth.userdetails.AuthMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}
