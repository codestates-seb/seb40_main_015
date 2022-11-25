package com.dongnebook.domain.reservation.ui;

import com.dongnebook.domain.reservation.application.ReservationService;
import com.dongnebook.global.Login;
import com.dongnebook.global.config.security.auth.userdetails.AuthMember;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservation")
public class ReservationsController {

    private final ReservationService reservationService;

    @PostMapping("{bookId}")
    public void postReservation(@PathVariable Long bookId, @Login AuthMember member) {
        reservationService.createReservation(bookId, member.getMemberId());
    }


//    @GetMapping("/")
//    public ResponseEntity<SliceImpl<>>

}
