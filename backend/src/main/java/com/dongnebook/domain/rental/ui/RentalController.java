package com.dongnebook.domain.rental.ui;


import com.dongnebook.domain.book.exception.NotRentableException;
import com.dongnebook.domain.rental.application.RentalService;
import com.dongnebook.domain.rental.dto.request.RentalSearchCondition;
import com.dongnebook.domain.rental.dto.response.RentalBookResponse;
import com.dongnebook.global.config.security.auth.userdetails.AuthMember;
import com.dongnebook.global.dto.request.PageRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.dao.ConcurrencyFailureException;
import org.springframework.data.domain.SliceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/rental")
public class RentalController {

    private final RentalService rentalService;

    @PostMapping("/{bookId}")
    public ResponseEntity<Void> postRental(@PathVariable Long bookId, @AuthenticationPrincipal AuthMember customer){
        try{
            rentalService.createRental(bookId, customer.getMemberId());
        }
        catch(ConcurrencyFailureException e){
            log.info("이미 누군가 대여한 책입니다.");
            throw new NotRentableException();
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/{rentalId}/cancelByCustomer")
    public ResponseEntity<Void> cancelRentalByCustomer(@PathVariable Long rentalId,
                                                       @AuthenticationPrincipal AuthMember customer){
        rentalService.cancelRentalByCustomer(rentalId, customer.getMemberId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{rentalId}/cancelByMerchant")
    public ResponseEntity<Void> cancelRentalByMerchant(@PathVariable Long rentalId,
                                                       @AuthenticationPrincipal AuthMember merchant){
        rentalService.cancelRentalByMerchant(rentalId, merchant.getMemberId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{rentalId}/receive")
    public ResponseEntity<Void> receiveBook(@PathVariable Long rentalId,
                                            @AuthenticationPrincipal AuthMember customer){
        rentalService.receiveBook(rentalId, customer.getMemberId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("{rentalId}/return")
    public ResponseEntity<Void> returnRental(@PathVariable Long rentalId,
                                             @AuthenticationPrincipal AuthMember merchant){
        rentalService.returnRental(rentalId, merchant.getMemberId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("from")
    public ResponseEntity<SliceImpl<RentalBookResponse>> getRentalsByMerchant(@AuthenticationPrincipal AuthMember merchant, @Valid RentalSearchCondition RentalSearchCondition, PageRequest pageRequest){
        return new ResponseEntity<>(rentalService.getRentalsByMerchant(merchant.getMemberId(), RentalSearchCondition.getRentalState(), pageRequest), HttpStatus.OK);
    }

    @GetMapping("to")
    public ResponseEntity<SliceImpl<RentalBookResponse>> getRentalsByCustomer(@AuthenticationPrincipal AuthMember customer, @Valid RentalSearchCondition RentalSearchCondition, PageRequest pageRequest){
        return new ResponseEntity<>(rentalService.getRentalsByCustomer(customer.getMemberId(), RentalSearchCondition.getRentalState(), pageRequest), HttpStatus.OK);
    }



}
