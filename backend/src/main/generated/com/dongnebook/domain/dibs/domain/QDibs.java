package com.dongnebook.domain.dibs.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDibs is a Querydsl query type for Dibs
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDibs extends EntityPathBase<Dibs> {

    private static final long serialVersionUID = 847102325L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDibs dibs = new QDibs("dibs");

    public final com.dongnebook.domain.model.QBaseTimeEntity _super = new com.dongnebook.domain.model.QBaseTimeEntity(this);

    public final com.dongnebook.domain.book.domain.QBook book;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.dongnebook.domain.member.domain.QMember member;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public QDibs(String variable) {
        this(Dibs.class, forVariable(variable), INITS);
    }

    public QDibs(Path<? extends Dibs> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDibs(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDibs(PathMetadata metadata, PathInits inits) {
        this(Dibs.class, metadata, inits);
    }

    public QDibs(Class<? extends Dibs> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.book = inits.isInitialized("book") ? new com.dongnebook.domain.book.domain.QBook(forProperty("book"), inits.get("book")) : null;
        this.member = inits.isInitialized("member") ? new com.dongnebook.domain.member.domain.QMember(forProperty("member"), inits.get("member")) : null;
    }

}

