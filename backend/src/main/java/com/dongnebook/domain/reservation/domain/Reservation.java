package com.dongnebook.domain.reservation.domain;

import com.dongnebook.domain.book.domain.Book;
import com.dongnebook.domain.member.domain.Member;
import com.dongnebook.domain.model.BaseTimeEntity;
import com.dongnebook.domain.rental.domain.Rental;
import com.dongnebook.domain.reservation.exception.CanNotChangeReservationStateException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "reservation")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "reserve_expected_at", nullable = false)
    private LocalDateTime reserveExpectedAt;

    @Column(name = "return_expected_at", nullable = false)
    private LocalDateTime returnExpectedAt;

    @Convert(converter = ReservationStateConverter.class)
    @Column(name = "reserve_state", nullable = false)
    private ReservationState reservationState;

    @OneToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "rental_id", nullable = false)
    private Rental rental;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Builder
    public Reservation(Long id, LocalDateTime reserveExpectedAt, LocalDateTime returnExpectedAt,
                       ReservationState reservationState, Rental rental, Member member, Book book){
        this.id = id;
        this.reserveExpectedAt = reserveExpectedAt;
        this.returnExpectedAt = returnExpectedAt;
        this.reservationState = reservationState;
        this.rental = rental;
        this.member = member;
        this.book = book;
    }

    public static Reservation create(Rental rental, Member member, Book book){
        return Reservation.builder()
                .reserveExpectedAt(rental.getRentalDeadLine())
                .returnExpectedAt(rental.getRentalDeadLine().plusDays(9))
                .reservationState(ReservationState.ON_RESERVATION)
                .rental(rental)
                .member(member)
                .book(book)
                .build();
    }

    public void changeReservationState(ReservationState from, ReservationState to){
        if(this.reservationState.equals(from)){
            this.reservationState = to;
            return;
        }
        throw new CanNotChangeReservationStateException();
    }
}

@Converter
class ReservationStateConverter implements AttributeConverter<ReservationState, String>{

    @Override
    public String convertToDatabaseColumn(ReservationState attribute)  {
        return String.valueOf(attribute);
    }

    @Override
    public ReservationState convertToEntityAttribute(String dbDate) {
        return ReservationState.valueOf(dbDate);
    }

}
