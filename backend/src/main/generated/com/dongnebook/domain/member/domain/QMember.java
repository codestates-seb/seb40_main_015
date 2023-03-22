package com.dongnebook.domain.member.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = 878929277L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMember member = new QMember("member1");

    public final com.dongnebook.domain.model.QBaseTimeEntity _super = new com.dongnebook.domain.model.QBaseTimeEntity(this);

    public final StringPath address = createString("address");

    public final EnumPath<Authority> authority = createEnum("authority", Authority.class);

    public final StringPath avatarUrl = createString("avatarUrl");

    public final NumberPath<Double> avgGrade = createNumber("avgGrade", Double.class);

    public final ListPath<com.dongnebook.domain.book.domain.Book, com.dongnebook.domain.book.domain.QBook> bookList = this.<com.dongnebook.domain.book.domain.Book, com.dongnebook.domain.book.domain.QBook>createList("bookList", com.dongnebook.domain.book.domain.Book.class, com.dongnebook.domain.book.domain.QBook.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final ListPath<com.dongnebook.domain.dibs.domain.Dibs, com.dongnebook.domain.dibs.domain.QDibs> dibsList = this.<com.dongnebook.domain.dibs.domain.Dibs, com.dongnebook.domain.dibs.domain.QDibs>createList("dibsList", com.dongnebook.domain.dibs.domain.Dibs.class, com.dongnebook.domain.dibs.domain.QDibs.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.dongnebook.domain.model.QLocation location;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath nickname = createString("nickname");

    public final StringPath password = createString("password");

    public final NumberPath<Long> receivedReviewCount = createNumber("receivedReviewCount", Long.class);

    public final StringPath userId = createString("userId");

    public QMember(String variable) {
        this(Member.class, forVariable(variable), INITS);
    }

    public QMember(Path<? extends Member> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMember(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMember(PathMetadata metadata, PathInits inits) {
        this(Member.class, metadata, inits);
    }

    public QMember(Class<? extends Member> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.location = inits.isInitialized("location") ? new com.dongnebook.domain.model.QLocation(forProperty("location")) : null;
    }

}

