package com.dongnebook.domain.rental.repository;

import static com.dongnebook.domain.book.domain.QBook.*;
import static com.dongnebook.domain.rental.domain.QRental.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import com.dongnebook.domain.book.application.port.in.response.QBookInfoResponse;
import com.dongnebook.domain.rental.domain.Rental;
import com.dongnebook.domain.rental.domain.RentalState;
import com.dongnebook.domain.rental.dto.response.QRentalBookResponse;
import com.dongnebook.domain.rental.dto.response.QRentalInfoResponse;
import com.dongnebook.domain.rental.dto.response.RentalBookResponse;
import com.dongnebook.global.dto.request.PageRequestImpl;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class RentalQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public SliceImpl<RentalBookResponse> findAllByMerchantIdOrderByIdDesc(Long merchantId, String rentalState, PageRequestImpl pageRequestImpl){
        List<RentalBookResponse> rentals = jpaQueryFactory.select(new QRentalBookResponse(
                    new QBookInfoResponse(
                            book.id,
                            book.imgUrl,
                            book.bookProduct.title,
                            book.bookProduct.author,
                            book.bookProduct.publisher,
                            book.rentalFee.value,
                            book.description,
                            book.location,
                            book.bookState,
                            book.member.nickname,
                            book.member.id
                    ),
                    new QRentalInfoResponse(
                            rental.id,
                            rental.customer.nickname,
                            rental.rentalState,
                            rental.rentalStartedAt,
                            rental.rentalDeadLine,
                            rental.rentalReturnedAt,
                            rental.canceledAt
                        )
                    )
                )
                .from(rental)
                .where(ltRentalId(pageRequestImpl.getIndex()),
                        rentalStateEq(rentalState),
                        rental.merchantId.eq(merchantId)
                )
                .innerJoin(rental.book, book)
                .innerJoin(book.member)
                .innerJoin(rental.customer)
                .orderBy(rental.id.desc())
                .limit(pageRequestImpl.getSize() + 1)
                .fetch();

        boolean hasNext = false;

        if (rentals.size() > pageRequestImpl.getSize()) {
            hasNext = true;
            rentals.remove(pageRequestImpl.getSize().intValue());
        }

        return new SliceImpl<>(rentals, pageRequestImpl.of(), hasNext);
    }

    public SliceImpl<RentalBookResponse> findAllByCustomerIdOrderByIdDesc(Long customerId, String rentalState, PageRequestImpl pageRequestImpl){

        List<RentalBookResponse> rentals = jpaQueryFactory.select(new QRentalBookResponse(
                                new QBookInfoResponse(
                                        book.id,
                                        book.imgUrl,
                                        book.bookProduct.title,
                                        book.bookProduct.author,
                                        book.bookProduct.publisher,
                                        book.rentalFee.value,
                                        book.description,
                                        book.location,
                                        book.bookState,
                                        book.member.nickname,
                                        book.member.id
                                ),
                                new QRentalInfoResponse(
                                        rental.id,
                                        rental.customer.nickname,
                                        rental.rentalState,
                                        rental.rentalStartedAt,
                                        rental.rentalDeadLine,
                                        rental.rentalReturnedAt,
                                        rental.canceledAt
                                )
                        )
                )
                .from(rental)
                .where(ltRentalId(pageRequestImpl.getIndex()),
                        rentalStateEq(rentalState),
                        (rental.customer.id.eq(customerId))
                )
                .innerJoin(rental.book, book)
                .innerJoin(book.member)
                .innerJoin(rental.customer)
                .orderBy(rental.id.desc())
                .limit(pageRequestImpl.getSize() + 1)
                .fetch();

        boolean hasNext = false;

        if (rentals.size() > pageRequestImpl.getSize()) {
            hasNext = true;
            rentals.remove(pageRequestImpl.getSize().intValue());
        }

        return new SliceImpl<>(rentals, pageRequestImpl.of(), hasNext);
    }

    public Optional<Rental> findRentalById(Long rentalId){
        return Optional.ofNullable(jpaQueryFactory.selectFrom(rental)
            .where(rental.id.eq(rentalId))
            .fetchOne());
    }

    public List<Rental> getRentalByBookId(Long bookId){
        return jpaQueryFactory.selectFrom(rental)
            .where(book.id.eq(bookId))
            .orderBy(rental.id.desc())
            .limit(1)
            .fetch();
    }


    private BooleanExpression ltRentalId(Long rentalId){
        if(rentalId == null){
            return null;
        }

        return rental.id.lt(rentalId);
    }

    private BooleanExpression rentalStateEq(String rentalState){
        if(RentalState.valueOf(rentalState) == RentalState.NONE){
            return null;
        }

        return rental.rentalState.eq(RentalState.valueOf(rentalState));
    }

    public List<Rental> findAllDeadLineRental(LocalDate alarmDate) {
        LocalDateTime start = LocalDateTime.of(alarmDate, LocalTime.MIN);
        LocalDateTime end = LocalDateTime.of(alarmDate, LocalTime.MAX);

        return jpaQueryFactory.selectFrom(rental)
            .innerJoin(rental.customer).fetchJoin()
            .innerJoin(rental.book).fetchJoin()
            .where(rental.rentalDeadLine.between(start,end),rentalStateEq(String.valueOf(RentalState.RETURN_REVIEWED)))
            .fetch();
    }
}
