package com.dongnebook.domain.rental.application;

import com.dongnebook.domain.book.domain.Book;
import com.dongnebook.domain.book.domain.BookState;
import com.dongnebook.domain.book.exception.BookNotFoundException;
import com.dongnebook.domain.book.exception.NotRentableException;
import com.dongnebook.domain.book.repository.BookCommandRepository;
import com.dongnebook.domain.member.domain.Member;
import com.dongnebook.domain.member.exception.MemberNotFoundException;
import com.dongnebook.domain.member.repository.MemberRepository;
import com.dongnebook.domain.rental.domain.Rental;
import com.dongnebook.domain.rental.dto.RentalRegisterRequest;
import com.dongnebook.domain.rental.repository.RentalRepository;
import com.dongnebook.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RentalService {

    private final RentalRepository rentalRepository;
    private final BookCommandRepository bookCommandRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long createRental(RentalRegisterRequest rentalRegisterRequest) {
        Optional<Member> optionalMember =  memberRepository.findById(rentalRegisterRequest.getMemberId());
        Member member = optionalMember.orElseThrow(MemberNotFoundException::new);

        Optional<Book> optionalBook = bookCommandRepository.findById(rentalRegisterRequest.getBookId());
        Book book = optionalBook.orElseThrow(BookNotFoundException::new);

        if(book.getBookState() != BookState.RENTABLE)
            throw new NotRentableException();

        Rental rental = Rental.create(book, member);
        rentalRepository.save(rental);

        return rental.getId();
    }

}
