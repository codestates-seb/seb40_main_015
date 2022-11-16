package com.dongnebook.domain.rental.ui;


import com.dongnebook.domain.rental.application.RentalService;
import com.dongnebook.domain.rental.dto.Request.RentalRegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rental")
public class RentalController {

    private final RentalService rentalService;

    @PostMapping
    public ResponseEntity<Long> postRental(@RequestBody RentalRegisterRequest rentalRegisterRequest){

        Long rentalId = rentalService.createRental(rentalRegisterRequest);

        return new ResponseEntity<>(rentalId, HttpStatus.ACCEPTED);
    }

    // 추후 cancel 추체에 따른 알림 대상이 달라지기에 cancel 주체별로 메서드 분리가 필요함
    @PatchMapping("{id}/cancel")
    public ResponseEntity<Void> cancelRental(@PathVariable("id") Long rentalId){

        rentalService.cancelRental(rentalId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("{id}/receive")
    public ResponseEntity<Void> receiveRental(@PathVariable("id") Long rentalId){

        rentalService.receiveRental(rentalId);

        return new ResponseEntity<>(HttpStatus.OK);
    }



}
