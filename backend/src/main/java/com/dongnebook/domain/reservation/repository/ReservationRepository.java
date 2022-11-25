package com.dongnebook.domain.reservation.repository;

import com.dongnebook.domain.reservation.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation,Long> {
    Optional<Reservation> findByRentalId(Long rentalId);
}
