package com.dongnebook.domain.reservation.ui;

import org.springframework.data.domain.SliceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dongnebook.domain.reservation.application.ReservationService;
import com.dongnebook.domain.reservation.dto.response.ReservationInfoResponse;
import com.dongnebook.global.security.auth.annotation.Login;
import com.dongnebook.global.dto.request.PageRequest;

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

	@GetMapping("/reservation")
	public ResponseEntity<SliceImpl<ReservationInfoResponse>> getReservations(@Login Long memberId,
		PageRequest pageRequest) {
		return new ResponseEntity<>(reservationService.readReservations(memberId, pageRequest), HttpStatus.OK);
	}

	@DeleteMapping("/reservation/cancel/{reservationId}")
	public ResponseEntity<Void> deleteReservation(@Login Long memberId,
		@PathVariable("reservationId") Long reservationId) {
		reservationService.cancelReservation(reservationId, memberId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
