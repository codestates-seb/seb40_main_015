package com.dongnebook.domain.rental.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRental is a Querydsl query type for Rental
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRental extends EntityPathBase<Rental> {

    private static final long serialVersionUID = -1842540271L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRental rental = new QRental("rental");

    public final com.dongnebook.domain.book.domain.QBook book;

    public final DateTimePath<java.time.LocalDateTime> canceledAt = createDateTime("canceledAt", java.time.LocalDateTime.class);

    public final com.dongnebook.domain.member.domain.QMember customer;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> merchantId = createNumber("merchantId", Long.class);

    public final DateTimePath<java.time.LocalDateTime> modifiedAt = createDateTime("modifiedAt", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> rentalDeadLine = createDateTime("rentalDeadLine", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> rentalReturnedAt = createDateTime("rentalReturnedAt", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> rentalStartedAt = createDateTime("rentalStartedAt", java.time.LocalDateTime.class);

    public final EnumPath<RentalState> rentalState = createEnum("rentalState", RentalState.class);

    public QRental(String variable) {
        this(Rental.class, forVariable(variable), INITS);
    }

    public QRental(Path<? extends Rental> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRental(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRental(PathMetadata metadata, PathInits inits) {
        this(Rental.class, metadata, inits);
    }

    public QRental(Class<? extends Rental> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.book = inits.isInitialized("book") ? new com.dongnebook.domain.book.domain.QBook(forProperty("book"), inits.get("book")) : null;
        this.customer = inits.isInitialized("customer") ? new com.dongnebook.domain.member.domain.QMember(forProperty("customer"), inits.get("customer")) : null;
    }

}

