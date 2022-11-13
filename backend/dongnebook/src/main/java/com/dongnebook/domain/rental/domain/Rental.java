package com.dongnebook.domain.rental.domain;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "Rental")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "rentalStartedAt", nullable = false)
    private LocalDateTime rentalStartedAt;

    @Column(name = "rentalDeadLine", nullable = false)
    private LocalDateTime rentalDeadLine;

    @Column(name = "rentalReturnedAt", nullable = true)
    private LocalDateTime rentalReturnedAt;

    @Column(name = "canceledAt", nullable = true)
    private LocalDateTime canceledAt;

    @Column(name = "isReturned", nullable = false)
    private boolean isReturned;

    @Column(name = "isCanceled", nullable = false)
    private boolean isCanceled;

    @Column(name = "isReviewed", nullable = false)
    private boolean isReviewed;

    @Builder
    public Rental(LocalDateTime rentalStartedAt, LocalDateTime rentalDeadLine, LocalDateTime rentalReturnedAt,
                  LocalDateTime canceledAt, boolean isReturned, boolean isCanceled, boolean isReviewed) {
        this.rentalStartedAt = rentalStartedAt;
        this.rentalDeadLine = rentalDeadLine;
        this.rentalReturnedAt = rentalReturnedAt;
        this.canceledAt = canceledAt;
        this.isReturned = isReturned;
        this.isCanceled = isCanceled;
        this.isReviewed = isReviewed;
    }

}
