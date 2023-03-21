package com.dongnebook.domain.book.application.port.in;

import com.dongnebook.domain.book.application.port.in.request.BookEditCommand;
import com.dongnebook.domain.book.application.port.in.request.BookPostRegisterCommand;

public interface BookPostCommandUseCase {
	Long register(BookPostRegisterCommand request, Long memberId);
	void edit(BookEditCommand bookEditCommand, Long memberId, Long bookId);
	Long delete(Long bookId, Long requestMemberId);

}
