package com.dongnebook.domain.dibs.service;

import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dongnebook.domain.book.application.BookQueryService;
import com.dongnebook.domain.book.application.port.in.response.BookSimpleResponse;
import com.dongnebook.domain.book.application.port.out.BookRepositoryPort;
import com.dongnebook.domain.book.domain.Book;
import com.dongnebook.domain.dibs.domain.Dibs;
import com.dongnebook.domain.dibs.exception.DibsNotFoundException;
import com.dongnebook.domain.dibs.repository.DibsRepository;
import com.dongnebook.domain.member.application.MemberService;
import com.dongnebook.domain.member.domain.Member;
import com.dongnebook.global.dto.request.PageRequestImpl;
import com.dongnebook.global.error.exception.BusinessException;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class DibsService {
	private final DibsRepository dibsRepository;
	private final MemberService memberService;
	private final BookQueryService bookQueryService;
	private final BookRepositoryPort bookRepositoryPort;


	public void doDibs(Long bookId,Long memberId){
		Book book = getBookById(bookId);
		Member member = getMemberById(memberId);
		Dibs dibs = Dibs.of(member, book);
		checkAlreadyDibs(book, member, dibs);
	}

	private void checkAlreadyDibs(Book book, Member member, Dibs dibs) {
		try{
			dibs = getDibs(book, member);
		}
		catch(BusinessException e){
			dibsRepository.save(dibs);
			return;
		}
		dibsRepository.delete(dibs);
	}

	public SliceImpl<BookSimpleResponse> findAll(PageRequestImpl pageRequestImpl, Long memberId) {
		return bookRepositoryPort.getAllDibsBook(memberId, pageRequestImpl);
	}

	private Dibs getDibs(Book book, Member member) {
		return dibsRepository.findByBookAndMemberOrderByIdDesc(book, member).orElseThrow(DibsNotFoundException::new);
	}

	private Member getMemberById(Long memberId) {
		return memberService.getById(memberId);
	}

	private Book getBookById(Long bookId) {
		return bookQueryService.getByBookId(bookId);
	}
}
