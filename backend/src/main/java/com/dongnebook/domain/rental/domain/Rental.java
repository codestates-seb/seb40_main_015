package com.dongnebook.domain.rental.domain;

import java.time.LocalDateTime;

import javax.persistence.AttributeConverter;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Converter;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.dongnebook.domain.book.domain.Book;
import com.dongnebook.domain.member.domain.Member;
import com.dongnebook.global.error.exception.CanNotChangeStateException;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "rental")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Column(name = "merchant_id")
    private Long merchantId;

    @Convert(converter = RentalStateConverter.class)
    @Column(name = "rental_state", nullable = false)
    private RentalState rentalState;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Member customer;

    @Builder
    public Rental(LocalDateTime rentalDeadLine, RentalState rentalState,
                  Long merchantId, Book book, Member customer) {
        this.rentalDeadLine = rentalDeadLine;
        this.rentalState = rentalState;
        this.merchantId = merchantId;
        this.book = book;
        this.customer = customer;
    }

    public void changeRentalStateFromTo(RentalState from, RentalState to) {

        if(to.equals(RentalState.CANCELED)){
            this.canceledAt=LocalDateTime.now();
        }

        if (this.rentalState.equals(from)) { // 대여가능 , 거래중, 반납완료, 취소
            this.rentalState=to;
            return;
        }

        throw new CanNotChangeStateException();
    }


    public void setCanceledAt(LocalDateTime canceledAt) {
        this.canceledAt = canceledAt;
    }

    public void setReturnedAt(LocalDateTime rentalReturnedAt) {
        this.rentalReturnedAt = rentalReturnedAt;
    }

    public static Rental create(Book book, Member customer) {
        return Rental.builder()
                .rentalDeadLine(LocalDateTime.now().plusDays(9).withHour(23).withMinute(59).withSecond(59))
                .rentalState(RentalState.TRADING)
                .merchantId(book.getMember().getId())
                .book(book)
                .customer(customer)
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