package com.dongnebook.domain.rental.repository;

import com.dongnebook.domain.rental.domain.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RentalRepository extends JpaRepository<Rental,Long> {
}
