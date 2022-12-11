package com.dongnebook.domain.rental.application;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import com.dongnebook.domain.rental.repository.RentalRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class RentalCreateEventHandler {
	private final RentalRepository rentalRepository;

	@TransactionalEventListener
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void changeBookStateFromTo(RentalCreateEvent rentalCreateEvent) {
		rentalRepository.save(rentalCreateEvent.getRental());
	}
}
