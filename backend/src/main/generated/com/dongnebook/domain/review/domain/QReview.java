package com.dongnebook.domain.review.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReview is a Querydsl query type for Review
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReview extends EntityPathBase<Review> {

    private static final long serialVersionUID = -1574430087L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReview review = new QReview("review");

    public final com.dongnebook.domain.model.QBaseTimeEntity _super = new com.dongnebook.domain.model.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> grade = createNumber("grade", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.dongnebook.domain.member.domain.QMember merchant;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final com.dongnebook.domain.rental.domain.QRental rental;

    public final StringPath reviewMessage = createString("reviewMessage");

    public QReview(String variable) {
        this(Review.class, forVariable(variable), INITS);
    }

    public QReview(Path<? extends Review> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReview(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReview(PathMetadata metadata, PathInits inits) {
        this(Review.class, metadata, inits);
    }

    public QReview(Class<? extends Review> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.merchant = inits.isInitialized("merchant") ? new com.dongnebook.domain.member.domain.QMember(forProperty("merchant"), inits.get("merchant")) : null;
        this.rental = inits.isInitialized("rental") ? new com.dongnebook.domain.rental.domain.QRental(forProperty("rental"), inits.get("rental")) : null;
    }

}

