package com.dongnebook.domain.rental.ui;


import com.dongnebook.domain.rental.application.RentalService;
import com.dongnebook.domain.rental.dto.Response.RentalBookResponse;
import com.dongnebook.global.config.security.auth.userdetails.AuthMember;
import com.dongnebook.global.dto.request.PageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.SliceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rental")
public class RentalController {

    private final RentalService rentalService;

    @PostMapping("/{bookId}")
    public ResponseEntity<Long> postRental(@PathVariable Long bookId, @AuthenticationPrincipal AuthMember merchant){

        rentalService.createRental(bookId, merchant.getMemberId());

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
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

    @GetMapping("{id}/from")
    public ResponseEntity<SliceImpl<RentalBookResponse>> getBooksFromMerchantInfinite(@PathVariable("id") Long merchantId, PageRequest pageRequest){
        return new ResponseEntity<>(rentalService.getBooksByMerchantInfinite(merchantId, pageRequest), HttpStatus.OK);
    }

}
