package com.dongnebook.domain.reservation.repository;

import com.dongnebook.domain.reservation.domain.Reservation;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.dongnebook.domain.rental.domain.QRental.rental;
import static com.dongnebook.domain.reservation.domain.QReservation.reservation;

@Repository
@RequiredArgsConstructor
public class ReservationQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<Reservation> getReservationByRentalId(Long rentalId){
        return jpaQueryFactory.selectFrom(reservation)
                .where(rental.id.eq(rentalId))
                .orderBy(reservation.id.desc())
                .limit(1)
                .fetch();
    }

//    public List<Reservation> getReservationById(Long reservationId){
//        return jpaQueryFactory.selectFrom(reservation)
//                .where(reservation.id.eq(reservationId))
//                .fetch();
//    }

    public Reservation getReservationById(Long reservationId){
        return jpaQueryFactory.selectFrom(reservation)
                .where(reservation.id.eq(reservationId))
                .fetchOne();
    }

}
