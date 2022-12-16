package com.dongnebook.domain.review.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.dongnebook.domain.member.domain.Member;
import com.dongnebook.domain.model.BaseTimeEntity;
import com.dongnebook.domain.rental.domain.Rental;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "review")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "review_message", updatable = false)
    private String reviewMessage;

    @Column(name = "grade", updatable = false)
    private Long grade;

    @OneToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "rental_id", nullable = false)
    private Rental rental;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "merchant_id", nullable = false)
    private Member merchant;

    @Builder
    public Review(Long id, String reviewMessage, Long grade, Rental rental, Member merchant){
        this.id = id;
        this.reviewMessage = reviewMessage;
        this.grade = grade;
        this.rental = rental;
        this.merchant = merchant;
    }

    public static Review create(String reviewMessage, Long grade, Rental rental, Member merchant){
        return Review.builder()
                .reviewMessage(reviewMessage)
                .grade(grade)
                .rental(rental)
                .merchant(merchant)
                .build();
    }
}
