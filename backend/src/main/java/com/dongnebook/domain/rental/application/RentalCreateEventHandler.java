package com.dongnebook.domain.rental.application;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import com.dongnebook.domain.book.application.BookService;
import com.dongnebook.domain.book.domain.Book;
import com.dongnebook.domain.rental.repository.RentalRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class RentalCreateEventHandler {

	private final RentalRepository rentalRepository;
	private final EntityManager em;

	@TransactionalEventListener
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void changeBookStateFromTo(RentalCreateEvent rentalCreateEvent) {
		rentalRepository.save(rentalCreateEvent.getRental());
	}
}
