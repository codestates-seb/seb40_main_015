package com.dongnebook.domain.review.ui;

import javax.validation.Valid;

import org.springframework.data.domain.SliceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dongnebook.domain.review.application.ReviewService;
import com.dongnebook.domain.review.dto.request.ReviewRequest;
import com.dongnebook.domain.review.dto.response.ReviewResponse;
import com.dongnebook.global.dto.request.PageRequestImpl;
import com.dongnebook.global.security.auth.annotation.Login;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ReviewController {
	private final ReviewService reviewService;

	@PostMapping("/review/{rentalId}")
	public ResponseEntity<Void> postReview(@PathVariable Long rentalId, @Valid @RequestBody ReviewRequest reviewRequest,
		@Login Long memberId) {
		reviewService.createReview(rentalId, reviewRequest, memberId);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@GetMapping("/review/{merchantId}")
	public ResponseEntity<SliceImpl<ReviewResponse>> getReviews(@PathVariable Long merchantId,
		PageRequestImpl pageRequestImpl) {
		return new ResponseEntity<>(reviewService.readReviews(merchantId, pageRequestImpl), HttpStatus.OK);
	}
}
