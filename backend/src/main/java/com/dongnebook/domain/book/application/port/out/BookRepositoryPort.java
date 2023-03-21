package com.dongnebook.domain.book.application.port.out;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.SliceImpl;

import com.dongnebook.domain.book.application.port.in.request.PageRequest;
import com.dongnebook.domain.book.application.port.in.request.BookSearchCommand;
import com.dongnebook.domain.book.application.port.in.response.BookDetailResponse;
import com.dongnebook.domain.book.application.port.in.response.BookSimpleResponse;
import com.dongnebook.domain.book.domain.Book;
import com.dongnebook.domain.member.domain.Member;
import com.dongnebook.domain.model.Location;

public interface BookRepositoryPort {

	Book save(Book book);
	List<Location> getNearByBookLocation(BookSearchCommand condition);
	Optional<Book> findWithMerchantByBookId(Long bookId);
	Optional<BookDetailResponse> findBookDetail(Long bookId, Long memberId);
	SliceImpl<BookSimpleResponse> getAll(BookSearchCommand condition, PageRequest pageRequestImpl);
	SliceImpl<BookSimpleResponse> getAllDibsBook(Long memberId, PageRequest pageRequestImpl);
	SliceImpl<BookSimpleResponse> getListByMember(Long memberId, PageRequest pageRequestImpl);
	void updateBookLocation(Member member, Location location);

	Optional<Book> findById(Long bookId);
}
