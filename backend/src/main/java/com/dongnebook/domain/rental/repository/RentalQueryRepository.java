package com.dongnebook.domain.rental.repository;

import com.dongnebook.domain.book.dto.response.QBookInfoResponse;
import com.dongnebook.domain.rental.dto.Response.QRentalBookResponse;
import com.dongnebook.domain.rental.dto.Response.QRentalInfoResponse;
import com.dongnebook.domain.rental.dto.Response.RentalBookResponse;
import com.dongnebook.global.dto.request.PageRequest;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.dongnebook.domain.book.domain.QBook.book;
import static com.dongnebook.domain.rental.domain.QRental.rental;

@Repository
@RequiredArgsConstructor
public class RentalQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public SliceImpl<RentalBookResponse> findAllByMerchantIdOrderByIdDesc(Long merchantId, PageRequest pageRequest){

        List<RentalBookResponse> rentals = jpaQueryFactory.select(new QRentalBookResponse(
                    new QBookInfoResponse(
                            book.id,
                            book.ImgUrl,
                            book.title,
                            book.author,
                            book.publisher,
                            book.rentalFee.value,
                            book.description,
                            book.location,
                            book.bookState,
                            book.member.nickname
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
                        (rental.merchantId.eq(merchantId))
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

    public SliceImpl<RentalBookResponse> findAllByCustomerIdOrderByIdDesc(Long customerId, PageRequest pageRequest){

        List<RentalBookResponse> rentals = jpaQueryFactory.select(new QRentalBookResponse(
                                new QBookInfoResponse(
                                        book.id,
                                        book.ImgUrl,
                                        book.title,
                                        book.author,
                                        book.publisher,
                                        book.rentalFee.value,
                                        book.description,
                                        book.location,
                                        book.bookState,
                                        book.member.nickname
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


    private BooleanExpression ltRentalId(Long rentalId){
        if(rentalId == null){
            return null;
        }
        return rental.id.lt(rentalId);
    }

}
