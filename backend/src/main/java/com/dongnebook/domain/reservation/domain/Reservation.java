package com.dongnebook.domain.reservation.domain;

import com.dongnebook.domain.book.domain.Book;
import com.dongnebook.domain.member.domain.Member;
import com.dongnebook.domain.model.BaseTimeEntity;
import com.dongnebook.domain.rental.domain.Rental;
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
    private LocalDateTime rentalExpectedAt;

    @Column(name = "return_expected_at", nullable = false)
    private LocalDateTime returnExpectedAt;

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
    public Reservation(Long id, LocalDateTime rentalExpectedAt, LocalDateTime returnExpectedAt,
                       Rental rental, Member member, Book book){
        this.id = id;
        this.rentalExpectedAt = rentalExpectedAt;
        this.returnExpectedAt = returnExpectedAt;
        this.rental = rental;
        this.member = member;
        this.book = book;
    }

    public static Reservation create(Rental rental, Member member, Book book){
        return Reservation.builder()
                .rentalExpectedAt(rental.getRentalDeadLine())
                .returnExpectedAt(rental.getRentalDeadLine().plusDays(9))
                .rental(rental)
                .member(member)
                .book(book)
                .build();
    }

}
