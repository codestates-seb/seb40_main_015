package com.dongnebook.domain.book.application;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dongnebook.domain.member.application.MemberQueryUseCase;
import com.dongnebook.domain.book.application.port.in.request.BookPostEditCommand;
import com.dongnebook.domain.book.application.port.in.BookPostCommandUseCase;
import com.dongnebook.domain.book.application.port.in.request.BookPostRegisterCommand;
import com.dongnebook.domain.book.application.port.out.BookRepositoryPort;
import com.dongnebook.domain.book.domain.Book;
import com.dongnebook.domain.book.domain.Money;
import com.dongnebook.domain.book.domain.BookProduct;
import com.dongnebook.domain.member.domain.Member;
import com.dongnebook.domain.model.Location;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class BookCommandService implements BookPostCommandUseCase {
	private final BookRepositoryPort bookRepositoryPort;
	private final MemberQueryUseCase memberQueryUseCase;

	@Override
	public Long register(BookPostRegisterCommand command, Long memberId) {
		Member member = getMember(memberId);
		Location location = member.hasLocation();
		Money money = Money.of(command.getRentalFee());
		BookProduct bookProduct = BookProduct.of(command.getTitle(), command.getAuthor(), command.getPublisher());
		Book book = Book.create(bookProduct, command.getImageUrl(), command.getDescription(), money, location, member);
		return bookRepositoryPort.save(book).getId();
	}

	@Override
	public void edit(BookPostEditCommand bookPostEditCommand, Long requestMemberId, Long bookId) {
		Book book = getByBookId(bookId);
		book.edit(bookPostEditCommand.getImageUrl(), bookPostEditCommand.getDescription(), requestMemberId);
	}

	@Caching(
		evict = {
			@CacheEvict(value = "books", allEntries = true),
		}
	)
	@Override
	public Long delete(Long bookId, Long requestMemberId) {
		Book book = getByBookId(bookId);
		book.delete(requestMemberId);
		return bookId;
	}

	private Member getMember(Long memberId) {
		return memberQueryUseCase.getMember(memberId);
	}

	private Book getByBookId(Long bookId) {
		return bookRepositoryPort.findById(bookId).orElseThrow(BookNotFoundException::new);
	}


}
