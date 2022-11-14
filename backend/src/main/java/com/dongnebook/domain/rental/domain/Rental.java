package com.dongnebook.domain.rental.domain;

import com.dongnebook.domain.book.domain.Book;
import com.dongnebook.domain.member.domain.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "rental")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "rental_started_at", nullable = false)
    private LocalDateTime rentalStartedAt;

    @Column(name = "rental_deadLine", nullable = false)
    private LocalDateTime rentalDeadLine;

    @Column(name = "rental_returned_at")
    private LocalDateTime rentalReturnedAt;

    @Column(name = "canceled_at")
    private LocalDateTime canceledAt;

    @Column(name = "is_returned", nullable = false)
    private boolean isReturned;

    @Column(name = "is_canceled", nullable = false)
    private boolean isCanceled;

    @Column(name = "is_reviewed", nullable = false)
    private boolean isReviewed;

    @OneToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Builder
    public Rental(LocalDateTime rentalStartedAt, LocalDateTime rentalDeadLine, LocalDateTime rentalReturnedAt,
                  LocalDateTime canceledAt, boolean isReturned, boolean isCanceled, boolean isReviewed,
                  Book book, Member member) {
        this.rentalStartedAt = rentalStartedAt;
        this.rentalDeadLine = rentalDeadLine;
        this.rentalReturnedAt = rentalReturnedAt;
        this.canceledAt = canceledAt;
        this.isReturned = isReturned;
        this.isCanceled = isCanceled;
        this.isReviewed = isReviewed;
        this.book = book;
        this.member = member;
    }

}
