package com.dongnebook.domain.rental.domain;

import com.dongnebook.domain.book.domain.Book;
import com.dongnebook.domain.member.domain.Member;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "rental")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false)
    private Long id;

    @CreatedDate
    @Column(name = "rental_started_at", nullable = false)
    private LocalDateTime rentalStartedAt;

    @Column(name = "rental_deadLine", nullable = false)
    private LocalDateTime rentalDeadLine;

    @Column(name = "rental_returned_at")
    private LocalDateTime rentalReturnedAt;

    @Column(name = "canceled_at")
    private LocalDateTime canceledAt;

    @LastModifiedDate
    @Column(name = "last_modified_at")
    private LocalDateTime modifiedAt;

    @Convert(converter = RentalStateConverter.class)
    @Column(name = "rental_state", nullable = false)
    private RentalState rentalState;

    @OneToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Member member;

    @Builder
    public Rental(LocalDateTime rentalDeadLine, RentalState rentalState,
                  Book book, Member member) {
        this.rentalDeadLine = rentalDeadLine;
        this.rentalState = rentalState;
        this.book = book;
        this.member = member;
    }

    public static Rental create(Book book, Member member) {
        return Rental.builder()
                .rentalDeadLine(LocalDateTime.now().plusDays(9).withHour(23).withMinute(59).withSecond(59))
                .rentalState(RentalState.TRADING)
                .book(book)
                .member(member)
                .build();
    }
}

@Converter
class RentalStateConverter implements AttributeConverter<RentalState, String> {

    @Override
    public String convertToDatabaseColumn(RentalState attribute) {
        return String.valueOf(attribute);
    }

    @Override
    public RentalState convertToEntityAttribute(String dbDate){
        return RentalState.valueOf(dbDate);
    }
}