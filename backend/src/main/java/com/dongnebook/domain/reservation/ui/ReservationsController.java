package com.dongnebook.domain.reservation.ui;

import org.springframework.data.domain.SliceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.dongnebook.domain.reservation.application.ReservationService;
import com.dongnebook.domain.reservation.dto.response.ReservationInfoResponse;
import com.dongnebook.global.dto.request.PageRequestImpl;
import com.dongnebook.global.security.auth.annotation.Login;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ReservationsController {
	private final ReservationService reservationService;

	@PostMapping("/reservation/{bookId}")
	public ResponseEntity<Void> postReservation(@PathVariable Long bookId, @Login Long memberId) {
		reservationService.createReservation(bookId, memberId);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	// 해당 유저 도서 예약가능유무 확인 API
	@GetMapping("/reservation/check/{bookId}")
	public ResponseEntity<Void> getReservationAvailability(@PathVariable Long bookId, @Login Long memberId) {
		reservationService.checkReservationAvailability(bookId, memberId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/reservation")
	public ResponseEntity<SliceImpl<ReservationInfoResponse>> getReservations(@Login Long memberId,
		PageRequestImpl pageRequestImpl) {
		return new ResponseEntity<>(reservationService.readReservations(memberId, pageRequestImpl), HttpStatus.OK);
	}

	@DeleteMapping("/reservation/cancel/{reservationId}")
	public ResponseEntity<Void> deleteReservation(@Login Long memberId,
		@PathVariable("reservationId") Long reservationId) {
		reservationService.cancelReservation(reservationId, memberId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
