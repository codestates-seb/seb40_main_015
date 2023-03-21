package com.dongnebook.domain.reservation.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReservation is a Querydsl query type for Reservation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReservation extends EntityPathBase<Reservation> {

    private static final long serialVersionUID = 912920913L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReservation reservation = new QReservation("reservation");

    public final com.dongnebook.domain.model.QBaseTimeEntity _super = new com.dongnebook.domain.model.QBaseTimeEntity(this);

    public final com.dongnebook.domain.book.domain.QBook book;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.dongnebook.domain.member.domain.QMember member;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final com.dongnebook.domain.rental.domain.QRental rental;

    public final DateTimePath<java.time.LocalDateTime> rentalExpectedAt = createDateTime("rentalExpectedAt", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> returnExpectedAt = createDateTime("returnExpectedAt", java.time.LocalDateTime.class);

    public QReservation(String variable) {
        this(Reservation.class, forVariable(variable), INITS);
    }

    public QReservation(Path<? extends Reservation> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReservation(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReservation(PathMetadata metadata, PathInits inits) {
        this(Reservation.class, metadata, inits);
    }

    public QReservation(Class<? extends Reservation> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.book = inits.isInitialized("book") ? new com.dongnebook.domain.book.domain.QBook(forProperty("book"), inits.get("book")) : null;
        this.member = inits.isInitialized("member") ? new com.dongnebook.domain.member.domain.QMember(forProperty("member"), inits.get("member")) : null;
        this.rental = inits.isInitialized("rental") ? new com.dongnebook.domain.rental.domain.QRental(forProperty("rental"), inits.get("rental")) : null;
    }

}

