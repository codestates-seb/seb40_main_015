package com.dongnebook.domain.rental.application;

import com.dongnebook.domain.rental.domain.Rental;

import lombok.Getter;

@Getter
public class RentalCreateEvent {
private Rental rental;

	public RentalCreateEvent(Rental rental) {
		this.rental = rental;
	}
}
