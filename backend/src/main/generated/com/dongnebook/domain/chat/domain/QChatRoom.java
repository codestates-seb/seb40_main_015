package com.dongnebook.domain.chat.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QChatRoom is a Querydsl query type for ChatRoom
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChatRoom extends EntityPathBase<ChatRoom> {

    private static final long serialVersionUID = -1968533900L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QChatRoom chatRoom = new QChatRoom("chatRoom");

    public final com.dongnebook.domain.book.domain.QBook book;

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final com.dongnebook.domain.member.domain.QMember customer;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.dongnebook.domain.member.domain.QMember merchant;

    public QChatRoom(String variable) {
        this(ChatRoom.class, forVariable(variable), INITS);
    }

    public QChatRoom(Path<? extends ChatRoom> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QChatRoom(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QChatRoom(PathMetadata metadata, PathInits inits) {
        this(ChatRoom.class, metadata, inits);
    }

    public QChatRoom(Class<? extends ChatRoom> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.book = inits.isInitialized("book") ? new com.dongnebook.domain.book.domain.QBook(forProperty("book"), inits.get("book")) : null;
        this.customer = inits.isInitialized("customer") ? new com.dongnebook.domain.member.domain.QMember(forProperty("customer"), inits.get("customer")) : null;
        this.merchant = inits.isInitialized("merchant") ? new com.dongnebook.domain.member.domain.QMember(forProperty("merchant"), inits.get("merchant")) : null;
    }

}

