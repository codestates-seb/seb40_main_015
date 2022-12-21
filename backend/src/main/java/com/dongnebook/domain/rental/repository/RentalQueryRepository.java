package com.dongnebook.domain.rental.repository;

import static com.dongnebook.domain.book.domain.QBook.*;
import static com.dongnebook.domain.rental.domain.QRental.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import com.dongnebook.domain.book.dto.response.QBookInfoResponse;
import com.dongnebook.domain.rental.domain.Rental;
import com.dongnebook.domain.rental.domain.RentalState;
import com.dongnebook.domain.rental.dto.response.QRentalBookResponse;
import com.dongnebook.domain.rental.dto.response.QRentalInfoResponse;
import com.dongnebook.domain.rental.dto.response.RentalBookResponse;
import com.dongnebook.global.dto.request.PageRequest;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class RentalQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public SliceImpl<RentalBookResponse> findAllByMerchantIdOrderByIdDesc(Long merchantId, String rentalState, PageRequest pageRequest){
        List<RentalBookResponse> rentals = jpaQueryFactory.select(new QRentalBookResponse(
                    new QBookInfoResponse(
                            book.id,
                            book.imgUrl,
                            book.title,
                            book.author,
                            book.publisher,
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
                .where(ltRentalId(pageRequest.getIndex()),
                        rentalStateEq(rentalState),
                        rental.merchantId.eq(merchantId)
                )
                .innerJoin(rental.book, book)
                .innerJoin(book.member)
                .innerJoin(rental.customer)
                .orderBy(rental.id.desc())
                .limit(pageRequest.getSize() + 1)
                .fetch();

        boolean hasNext = false;

        if (rentals.size() > pageRequest.getSize()) {
            hasNext = true;
            rentals.remove(pageRequest.getSize().intValue());
        }

        return new SliceImpl<>(rentals, pageRequest.of(), hasNext);
    }

    public SliceImpl<RentalBookResponse> findAllByCustomerIdOrderByIdDesc(Long customerId, String rentalState, PageRequest pageRequest){

        List<RentalBookResponse> rentals = jpaQueryFactory.select(new QRentalBookResponse(
                                new QBookInfoResponse(
                                        book.id,
                                        book.imgUrl,
                                        book.title,
                                        book.author,
                                        book.publisher,
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
                .where(ltRentalId(pageRequest.getIndex()),
                        rentalStateEq(rentalState),
                        (rental.customer.id.eq(customerId))
                )
                .innerJoin(rental.book, book)
                .innerJoin(book.member)
                .innerJoin(rental.customer)
                .orderBy(rental.id.desc())
                .limit(pageRequest.getSize() + 1)
                .fetch();

        boolean hasNext = false;

        if (rentals.size() > pageRequest.getSize()) {
            hasNext = true;
            rentals.remove(pageRequest.getSize().intValue());
        }

        return new SliceImpl<>(rentals, pageRequest.of(), hasNext);
    }

    public Rental getRentalById(Long rentalId){
        return jpaQueryFactory.selectFrom(rental)
                .where(rental.id.eq(rentalId))
                .fetchOne();
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
        if(rentalState == null){
            return null;
        }

        return rental.rentalState.eq(RentalState.valueOf(rentalState));
    }

    public List<Rental> findAllDeadLineRental(LocalDate alarmDate) {
        LocalDateTime start = LocalDateTime.of(alarmDate, LocalTime.MIN);
        LocalDateTime end = LocalDateTime.of(alarmDate, LocalTime.MAX);

        return jpaQueryFactory.selectFrom(rental)
            .innerJoin(rental.customer).fetchJoin()
            .where(rental.rentalDeadLine.between(start,end))
            .fetch();
    }
}
