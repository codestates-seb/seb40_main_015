package com.dongnebook.domain.book.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBook is a Querydsl query type for Book
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBook extends EntityPathBase<Book> {

    private static final long serialVersionUID = -1901544549L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBook book = new QBook("book");

    public final com.dongnebook.domain.model.QBaseTimeEntity _super = new com.dongnebook.domain.model.QBaseTimeEntity(this);

    public final QBookProduct bookProduct;

    public final EnumPath<BookState> bookState = createEnum("bookState", BookState.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath description = createString("description");

    public final ListPath<com.dongnebook.domain.dibs.domain.Dibs, com.dongnebook.domain.dibs.domain.QDibs> dibsList = this.<com.dongnebook.domain.dibs.domain.Dibs, com.dongnebook.domain.dibs.domain.QDibs>createList("dibsList", com.dongnebook.domain.dibs.domain.Dibs.class, com.dongnebook.domain.dibs.domain.QDibs.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imgUrl = createString("imgUrl");

    public final com.dongnebook.domain.model.QLocation location;

    public final com.dongnebook.domain.member.domain.QMember member;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final QMoney rentalFee;

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public QBook(String variable) {
        this(Book.class, forVariable(variable), INITS);
    }

    public QBook(Path<? extends Book> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBook(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBook(PathMetadata metadata, PathInits inits) {
        this(Book.class, metadata, inits);
    }

    public QBook(Class<? extends Book> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.bookProduct = inits.isInitialized("bookProduct") ? new QBookProduct(forProperty("bookProduct")) : null;
        this.location = inits.isInitialized("location") ? new com.dongnebook.domain.model.QLocation(forProperty("location")) : null;
        this.member = inits.isInitialized("member") ? new com.dongnebook.domain.member.domain.QMember(forProperty("member"), inits.get("member")) : null;
        this.rentalFee = inits.isInitialized("rentalFee") ? new QMoney(forProperty("rentalFee")) : null;
    }

}

