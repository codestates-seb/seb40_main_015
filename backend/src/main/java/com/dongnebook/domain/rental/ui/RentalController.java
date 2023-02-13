package com.dongnebook.domain.rental.ui;

import javax.validation.Valid;

import org.springframework.dao.ConcurrencyFailureException;
import org.springframework.data.domain.SliceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.dongnebook.domain.book.exception.NotRentableException;
import com.dongnebook.domain.rental.application.RentalService;
import com.dongnebook.domain.rental.dto.request.RentalSearchCondition;
import com.dongnebook.domain.rental.dto.response.RentalBookResponse;
import com.dongnebook.global.dto.request.PageRequest;
import com.dongnebook.global.security.auth.annotation.Login;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/rental")
public class RentalController {
	private final RentalService rentalService;

	@PostMapping("/{bookId}")
	public ResponseEntity<Void> postRental(@PathVariable Long bookId, @Login Long memberId) {
		try {
			rentalService.createRental(bookId, memberId);
		} catch (ConcurrencyFailureException e) {
			log.info("이미 누군가 대여한 책입니다.");
			throw new NotRentableException();
		}
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@PatchMapping("/{rentalId}/cancelByCustomer")
	public ResponseEntity<Void> cancelRentalByCustomer(@PathVariable Long rentalId,
		@Login Long memberId) {
		rentalService.cancelRentalByCustomer(rentalId, memberId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PatchMapping("/{rentalId}/cancelByMerchant")
	public ResponseEntity<Void> cancelRentalByMerchant(@PathVariable Long rentalId,
		@Login Long memberId) {
		rentalService.cancelRentalByMerchant(rentalId, memberId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PatchMapping("/{rentalId}/receive")
	public ResponseEntity<Void> receiveBook(@PathVariable Long rentalId, @Login Long memberId) {
		rentalService.receiveBook(rentalId, memberId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PatchMapping("{rentalId}/return")
	public ResponseEntity<Void> returnRental(@PathVariable Long rentalId,
		@Login Long memberId) {
		rentalService.returnRental(rentalId, memberId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("from")
	public ResponseEntity<SliceImpl<RentalBookResponse>> getRentalsByMerchant(
		@Login Long memberId, @Valid @RequestParam(name = "rentalState", required = false, defaultValue = "NONE") RentalSearchCondition rentalSearchCondition,
		PageRequest pageRequest) {
		return new ResponseEntity<>(
			rentalService.getRentalsByMerchant(memberId, rentalSearchCondition.getRentalState(),
				pageRequest), HttpStatus.OK);
	}

	@GetMapping("to")
	public ResponseEntity<SliceImpl<RentalBookResponse>> getRentalsByCustomer(
		@Login Long memberId, @Valid @RequestParam(name = "rentalState", required = false, defaultValue = "NONE") RentalSearchCondition rentalSearchCondition,
		PageRequest pageRequest) {
		return new ResponseEntity<>(
			rentalService.getRentalsByCustomer(memberId, rentalSearchCondition.getRentalState(),
				pageRequest), HttpStatus.OK);
	}
}
