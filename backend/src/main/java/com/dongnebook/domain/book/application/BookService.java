package com.dongnebook.domain.book.application;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dongnebook.domain.book.domain.Book;
import com.dongnebook.domain.book.dto.request.BookRegisterRequest;
import com.dongnebook.domain.book.dto.response.BookDetailResponse;
import com.dongnebook.domain.book.exception.BookNotFoundException;
import com.dongnebook.domain.book.repository.BookCommandRepository;
import com.dongnebook.domain.book.repository.BookQueryRepository;
import com.dongnebook.domain.member.domain.Member;
import com.dongnebook.domain.member.exception.LocationNotCreatedYetException;
import com.dongnebook.domain.member.repository.MemberRepository;
import com.dongnebook.domain.model.Location;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookService {

	private final BookCommandRepository bookCommandRepository;
	private final BookQueryRepository bookQueryRepository;
	private final MemberRepository memberRepository;

	@Transactional
	public Long create(BookRegisterRequest bookRegisterRequest, Long memberId){

		Member member = getMember();
		Location location = ifDtoHasNoLocationGetMemberLocation(bookRegisterRequest, member);
		Book book = Book.create(bookRegisterRequest, location, member);
		return bookCommandRepository.save(book).getId();
	}

	@Transactional
	public Long delete(Long bookId, Long memberId) {

		//자기가 쓴 글인지 확인하는 로직


		Book book = getByBookId(bookId);

		book.delete();

		return bookId;
	}

	public BookDetailResponse getDetail(Long id) {
		return bookQueryRepository.getDetail(id);
	}

	private Location ifDtoHasNoLocationGetMemberLocation(BookRegisterRequest bookRegisterRequest, Member member) {
		return Optional.ofNullable(bookRegisterRequest.getLocation())
			.orElse(Optional.ofNullable(member.getLocation()).orElseThrow(LocationNotCreatedYetException::new));
	}

	private Member getMember() {
		return memberRepository.findById(1L).orElseThrow(IllegalStateException::new);
	}

	private Book getByBookId(Long bookId) {
		return bookCommandRepository.findById(bookId).orElseThrow(BookNotFoundException::new);
	}


}
