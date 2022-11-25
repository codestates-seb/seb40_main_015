package com.dongnebook.domain.reservation.ui;

import com.dongnebook.domain.reservation.application.ReservationService;
import com.dongnebook.global.Login;
import com.dongnebook.global.config.security.auth.userdetails.AuthMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservation")
public class ReservationsController {

    private final ReservationService reservationService;

    @PostMapping("{bookId}")
    public ResponseEntity<Void> postReservation(@PathVariable Long bookId, @Login AuthMember member) {
        reservationService.createReservation(bookId, member.getMemberId());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @DeleteMapping("cancel/{reservationId}")
    public ResponseEntity<Void> deleteReservation(@PathVariable("reservationId") Long reservationId, @Login AuthMember member){
        reservationService.cancelReservation(reservationId, member.getMemberId());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
