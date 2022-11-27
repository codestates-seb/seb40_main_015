package com.dongnebook.domain.reservation.ui;

import com.dongnebook.domain.reservation.application.ReservationService;
import com.dongnebook.domain.reservation.dto.request.ReservationInfoResponse;
import com.dongnebook.global.Login;
import com.dongnebook.global.config.security.auth.userdetails.AuthMember;
import com.dongnebook.global.dto.request.PageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.SliceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ReservationsController {

    private final ReservationService reservationService;

    @PostMapping("/reservation/{bookId}")
    public ResponseEntity<Void> postReservation(@PathVariable Long bookId, @Login AuthMember member) {
        reservationService.createReservation(bookId, member.getMemberId());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/reservation")
    public ResponseEntity<SliceImpl<ReservationInfoResponse>> getReservations(@Login AuthMember member, PageRequest pageRequest){
        return new ResponseEntity<>(reservationService.readReservations(member.getMemberId(), pageRequest), HttpStatus.OK);
    }

    @DeleteMapping("/reservation/cancel/{reservationId}")
    public ResponseEntity<Void> deleteReservation(@PathVariable("reservationId") Long reservationId, @Login AuthMember member){
        reservationService.cancelReservation(reservationId, member.getMemberId());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
