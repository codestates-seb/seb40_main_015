package com.dongnebook.domain.reservation.repository;

import static com.dongnebook.domain.rental.domain.QRental.*;
import static com.dongnebook.domain.reservation.domain.QReservation.*;

import java.util.List;

import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import com.dongnebook.domain.book.dto.response.QBookSimpleResponse;
import com.dongnebook.domain.reservation.domain.Reservation;
import com.dongnebook.domain.reservation.dto.response.QReservationInfoResponse;
import com.dongnebook.domain.reservation.dto.response.QReservationResponse;
import com.dongnebook.domain.reservation.dto.response.ReservationInfoResponse;
import com.dongnebook.global.dto.request.PageRequest;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ReservationQueryRepository {
	private final JPAQueryFactory jpaQueryFactory;

	public List<Reservation> getReservationByRentalId(Long rentalId) {
		return jpaQueryFactory
			.selectFrom(reservation)
			.where(rental.id.eq(rentalId))
			.orderBy(reservation.id.desc())
			.limit(1)
			.fetch();
	}

	public Reservation getReservationById(Long reservationId) {
		return jpaQueryFactory
			.selectFrom(reservation)
			.where(reservation.id.eq(reservationId))
			.fetchOne();
	}

	public SliceImpl<ReservationInfoResponse> findAllByMemberIdOrderByIdDesc(Long memberId, PageRequest pageRequest) {

		List<ReservationInfoResponse> reservations = jpaQueryFactory
			.select(
				new QReservationInfoResponse(
					new QReservationResponse(
						reservation.id,
						reservation.rentalExpectedAt),
					new QBookSimpleResponse(
						reservation.book.id,
						reservation.book.title,
						reservation.book.rentalFee,
						reservation.book.imgUrl,
						reservation.book.member.nickname)))
			.from(reservation)
			.innerJoin(reservation.book)
			.where(ltReservationId(pageRequest.getIndex()),
				reservation.member.id.eq(memberId))
			.orderBy(reservation.id.desc())
			.limit(pageRequest.getSize() + 1)
			.fetch();

		boolean hasNext = false;

		if (reservations.size() > pageRequest.getSize()) {
			hasNext = true;
			reservations.remove(pageRequest.getSize().intValue());
		}

		return new SliceImpl<>(reservations, pageRequest.of(), hasNext);

	}

	private BooleanExpression ltReservationId(Long reservationId) {
		if (reservationId == null) {
			return null;
		}

		return reservation.id.lt(reservationId);
	}
}
