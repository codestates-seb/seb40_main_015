package com.dongnebook.domain.book.application.port.in;

import java.util.List;

import org.springframework.data.domain.SliceImpl;

import com.dongnebook.domain.book.application.port.in.request.PageRequest;
import com.dongnebook.domain.book.application.port.in.request.BookSearchCommand;
import com.dongnebook.domain.book.application.port.in.response.BookCountPerSectorResponse;
import com.dongnebook.domain.book.application.port.in.response.BookDetailResponse;
import com.dongnebook.domain.book.application.port.in.response.BookSimpleResponse;
import com.dongnebook.domain.book.domain.Book;

public interface BookPostQueryUseCase {
	List<BookCountPerSectorResponse> getBookCountPerSector(BookSearchCommand condition);
	BookDetailResponse getDetail(Long id, Long memberId);
	Book getByBookId(Long bookId);
	Book getWithMerchantByBookId(Long bookId);
	SliceImpl<BookSimpleResponse> getList(BookSearchCommand bookSearchCondition, PageRequest pageRequestImpl);
	SliceImpl<BookSimpleResponse> getListByMember(Long memberId, PageRequest pageRequestImpl);

}
