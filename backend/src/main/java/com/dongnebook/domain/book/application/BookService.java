package com.dongnebook.domain.book.application;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.dongnebook.domain.book.domain.Book;
import com.dongnebook.domain.book.dto.Request.BookRegisterRequest;
import com.dongnebook.domain.book.exception.BookNotFoundException;
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

	public Long delete(Long bookId, Long memberId) {

		//자기가 쓴 글인지 확인하는 로직


		Book book = getByBookId(bookId);

		book.delete();

		return bookId;
	}

	private Location ifDtoHasNoLocationGetMemberLocation(BookRegisterRequest bookRegisterRequest, Member member) {
		return Optional.ofNullable(bookRegisterRequest.getLocation())
			.orElse(Optional.ofNullable(member.getLocation()).orElseThrow(LocationNotCreatedYetException::new));
	}

	private Member getMember() {
		return new Member();
	}

	private Book getByBookId(Long bookId) {
		return bookRepository.findById(bookId).orElseThrow(BookNotFoundException::new);
	}
}
