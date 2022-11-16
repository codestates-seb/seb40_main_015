package com.dongnebook.domain.rental.ui;


import com.dongnebook.domain.rental.application.RentalService;
import com.dongnebook.domain.rental.dto.RentalRegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rental")
public class RentalController {

    private final RentalService rentalService;

    @PostMapping
    public ResponseEntity postRental(@RequestBody RentalRegisterRequest rentalRegisterRequest){

        Long rentalId = rentalService.createRental(rentalRegisterRequest);

        return new ResponseEntity<>(rentalId, HttpStatus.ACCEPTED);
    }

}
