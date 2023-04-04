package com.dongnebook.domain.book.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dongnebook.domain.book.application.port.in.request.PageRequest;
import com.dongnebook.domain.book.application.port.in.request.BookSearchCommand;
import com.dongnebook.domain.book.application.port.in.response.BookCountPerSectorResponse;
import com.dongnebook.domain.book.application.port.in.response.BookDetailResponse;
import com.dongnebook.domain.book.application.port.in.response.BookSimpleResponse;
import com.dongnebook.domain.book.application.port.in.BookPostQueryUseCase;
import com.dongnebook.domain.book.application.port.out.BookRepositoryPort;
import com.dongnebook.domain.book.domain.Book;
import com.dongnebook.domain.model.Location;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookQueryService implements BookPostQueryUseCase {
	private final BookRepositoryPort bookRepositoryPort;

	@Override
	public List<BookCountPerSectorResponse> getBookCountPerSector(BookSearchCommand condition) {

		List<Location> bookLocationList = bookRepositoryPort.getNearByBookLocation(condition);

		Map<Integer, BookCountPerSectorResponse> collect = bookLocationList.stream()
			.flatMap(location ->
				IntStream.iterate(1, sector -> sector <= Math.pow(condition.getLevel(), 2), sector -> sector + 1)
					.filter(sector -> location.checkRange(condition,sector))
					.mapToObj(sector -> new BookCountPerSectorResponse(0L, sector, location)))
			.collect(
				Collectors.toMap(BookCountPerSectorResponse::getSector, BookCountPerSectorResponse::increaseBookCount,
					(exist, newOne) -> exist.increaseBookCount()));

		return new ArrayList<>(collect.values());
	}


	@Override
	public BookDetailResponse getDetail(Long id, Long memberId) {
		return bookRepositoryPort.findBookDetail(id, memberId).orElseThrow(BookNotFoundException::new);
	}

	@Override
	public Book getByBookId(Long bookId) {
		return bookRepositoryPort.findById(bookId).orElseThrow(BookNotFoundException::new);
	}

	@Override
	public Book getWithMerchantByBookId(Long bookId) {
		return bookRepositoryPort.findWithMerchantByBookId(bookId).orElseThrow(BookNotFoundException::new);
	}

	@Override
	@Cacheable(value = "books", key = "#pageRequestImpl", condition = "#pageRequestImpl.index == null",cacheManager = "cacheManager")
	public SliceImpl<BookSimpleResponse> getList(BookSearchCommand bookSearchCondition, PageRequest pageRequestImpl) {
		return bookRepositoryPort.getAll(bookSearchCondition, pageRequestImpl);
	}

	@Override
	public SliceImpl<BookSimpleResponse> getListByMember(Long memberId, PageRequest pageRequestImpl) {
		return bookRepositoryPort.getListByMember(memberId, pageRequestImpl);
	}

}
