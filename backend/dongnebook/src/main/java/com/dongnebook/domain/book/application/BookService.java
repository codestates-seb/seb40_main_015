package com.dongnebook.domain.book.application;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.dongnebook.domain.book.domain.Book;
import com.dongnebook.domain.book.dto.Request.BookRegisterRequest;
import com.dongnebook.domain.book.repository.BookRepository;
import com.dongnebook.domain.member.domain.Member;
import com.dongnebook.domain.member.exception.LocationNotCreatedYetException;
import com.dongnebook.domain.model.Location;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Service
@RequiredArgsConstructor
public class BookService {

	private final BookRepository bookRepository;

	public Long create(BookRegisterRequest bookRegisterRequest, Long memberId){

		Member member = getMember();
		Location location = ifDtoHasNoLocationGetMemberLocation(bookRegisterRequest, member);
		Book book = Book.create(bookRegisterRequest, location, member);
		return bookRepository.save(book).getId();

	}

	private Location ifDtoHasNoLocationGetMemberLocation(BookRegisterRequest bookRegisterRequest, Member member) {
		Location location = Optional.ofNullable(bookRegisterRequest.getLocation())
			.orElse(Optional.ofNullable(member.getLocation()).orElseThrow(LocationNotCreatedYetException::new));
		return location;
	}

	private Member getMember() {
		Member member = new Member();
		return member;
	}
}
