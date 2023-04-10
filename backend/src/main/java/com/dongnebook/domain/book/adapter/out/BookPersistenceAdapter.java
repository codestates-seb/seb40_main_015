package com.dongnebook.domain.book.adapter.out;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Component;

import com.dongnebook.domain.book.application.port.in.request.BookSearchCommand;
import com.dongnebook.domain.book.application.port.in.request.PageRequest;
import com.dongnebook.domain.book.application.port.in.response.BookDetailResponse;
import com.dongnebook.domain.book.application.port.in.response.BookSimpleResponse;
import com.dongnebook.domain.book.application.port.out.BookRepositoryPort;
import com.dongnebook.domain.book.domain.Book;
import com.dongnebook.domain.member.domain.Member;
import com.dongnebook.domain.model.Location;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BookPersistenceAdapter implements BookRepositoryPort {
	private final BookCommandRepository commandRepository;
	private final BookQueryRepository queryRepository;

	@Override
	public Book save(Book book) {
		return commandRepository.save(book);
	}

	@Override
	public List<Location> getNearByBookLocation(BookSearchCommand condition) {
		return queryRepository.getNearByBookLocation(condition);
	}

	@Override
	public Optional<Book> findWithMerchantByBookId(Long bookId) {
		return queryRepository.findWithMerchantByBookId(bookId);
	}

	@Override
	public Optional<BookDetailResponse> findBookDetail(Long bookId, Long memberId) {
		return queryRepository.findBookDetail(bookId, memberId);
	}

	@Override
	public SliceImpl<BookSimpleResponse> getAll(BookSearchCommand condition, PageRequest pageRequest) {
		return queryRepository.getAll(condition, pageRequest);
	}

	@Override
	public SliceImpl<BookSimpleResponse> getAllDibsBook(Long memberId, PageRequest pageRequest) {
		return queryRepository.getAllDibsBook(memberId, pageRequest);
	}

	@Override
	public SliceImpl<BookSimpleResponse> getListByMember(Long memberId, PageRequest pageRequest) {
		return queryRepository.getListByMember(memberId, pageRequest);
	}

	@Override
	public void updateBookLocation(Member member, Location location) {
		queryRepository.updateBookLocation(member, location);
	}

	@Override
	public Optional<Book> findById(Long bookId) {
		return commandRepository.findById(bookId);
	}
}
