package com.dongnebook.domain.alarm.domain;





import static com.dongnebook.domain.alarm.domain.QAlarm.*;
import static com.dongnebook.domain.book.domain.QBook.*;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.dongnebook.domain.alarm.dto.AlarmResponse;
import com.dongnebook.domain.alarm.dto.QAlarmResponse;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AlarmQueryRepository {

	private final JPAQueryFactory jpaQueryFactory;
	private final EntityManager em;

	public List<AlarmResponse> getMyAlarm(Long memberId){

		return jpaQueryFactory.select(
				new QAlarmResponse(alarm.id, alarm.type, book.member.id, book.title, alarm.isRead))
			.from(alarm)
			.innerJoin(alarm.book, book)
			.innerJoin(book.member)
			.where(alarmMemberIdEq(memberId))
			.fetch();
	}

	public void read(Long memberId) {
		jpaQueryFactory.update(alarm)
			.set(alarm.isRead,Boolean.TRUE)
			.where(alarmMemberIdEq(memberId))
			.execute();

		em.clear();
		em.flush();
	}

	private BooleanExpression alarmMemberIdEq(Long memberId) {
		return alarm.member.id.eq(memberId);
	}
}
