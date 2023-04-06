package com.dongnebook.domain.alarm.repository;

import com.dongnebook.config.TestConfig;
import com.dongnebook.domain.alarm.domain.Alarm;
import com.dongnebook.domain.alarm.dto.AlarmResponse;
import com.dongnebook.domain.book.adapter.out.BookCommandRepository;
import com.dongnebook.domain.book.domain.Book;
import com.dongnebook.domain.book.domain.BookProduct;
import com.dongnebook.domain.book.domain.Money;
import com.dongnebook.domain.member.domain.Member;
import com.dongnebook.domain.member.repository.MemberRepository;
import com.dongnebook.domain.model.Location;
import com.dongnebook.global.enums.AlarmType;
import com.dongnebook.support.DataClearExtension;
import com.dongnebook.support.DatabaseCleaner;
import com.github.gavlyukovskiy.boot.jdbc.decorator.DataSourceDecoratorAutoConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(showSql = false)
@ImportAutoConfiguration(DataSourceDecoratorAutoConfiguration.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({TestConfig.class, DatabaseCleaner.class})
@ExtendWith(DataClearExtension.class)
public class AlarmQueryRepositoryTest {
    @Autowired
    AlarmQueryRepository alarmQueryRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    BookCommandRepository bookCommandRepository;
    @Autowired
    AlarmRepository alarmRepository;

    static Member savedMember;
    static Alarm savedAlarm1;

    @BeforeEach
    public void setting() {
        Member merchant = Member.builder()
                .userId("test")
                .avatarUrl("aaa@aa.com")
                .nickname("tester")
                .password("12341234")
                .build();
        savedMember = memberRepository.save(merchant);

        BookProduct bookProduct1 = new BookProduct("수학의 정석1", "남궁성", "도우출판");
        Book book1 = Book.builder()
                .id(1L)
                .bookProduct(bookProduct1)
                .imgUrl("aaa@abc.com")
                .description("기본이 중요")
                .rentalFee(Money.of(1000))
                .location(new Location(37.5340, 126.7064))
                .member(savedMember)
                .build();
        Book savedBook1 = bookCommandRepository.save(book1);

        Alarm alarm1 = Alarm.create(savedMember, savedBook1, AlarmType.RENTAL);
        savedAlarm1 = alarmRepository.save(alarm1);

        BookProduct bookProduct2 = new BookProduct("수학의 정석2", "남궁성", "도우출판");
        Book book2 = Book.builder()
                .id(1L)
                .bookProduct(bookProduct2)
                .imgUrl("aaa@abc.com")
                .description("기본이 중요")
                .rentalFee(Money.of(1000))
                .location(new Location(37.5340, 126.7064))
                .member(savedMember)
                .build();
        Book savedBook2 = bookCommandRepository.save(book2);

        Alarm alarm2 = Alarm.create(savedMember, savedBook2, AlarmType.RETURN);
        Alarm savedAlarm2 = alarmRepository.save(alarm2);
    }

    @Test
    public void getMyAlarmTest() {
        // given

        // when
        List<AlarmResponse> alarmResponseList = alarmQueryRepository.getMyAlarm(1L);

        // then
        assertThat(alarmResponseList).hasSize(2);
        assertThat(alarmResponseList.get(alarmResponseList.size()-1).getAlarmId()).isEqualTo(1);
        assertThat(alarmResponseList.get(alarmResponseList.size()-1).getAlarmType()).isEqualTo(savedAlarm1.getType());
    }

    @Test
    public void findByAlarmWithMemberIdTest(){
        // given

        // when
        Optional<Alarm> alarmOptional = alarmQueryRepository.findByAlarmWithMemberId(savedAlarm1.getId());

        // then
        assertThat(alarmOptional.get().getId()).isEqualTo(savedAlarm1.getId());
    }

    @Test
    public void readAlarmTest() {
        // given

        // when
        alarmQueryRepository.read(savedMember.getId());
        List<AlarmResponse> alarmResponseList = alarmQueryRepository.getMyAlarm(savedMember.getId());

        // then
        assertThat(alarmResponseList.get(0).getIsRead()).isEqualTo(true);
    }

    @Test
    public void deleteAllByMemberIdTest() {
        // given

        // when
        alarmQueryRepository.deleteAllByMemberId(savedMember.getId());

        // then
        List<AlarmResponse> alarmResponseList = alarmQueryRepository.getMyAlarm(savedMember.getId());
        assertThat(alarmResponseList.size()).isEqualTo(0);
    }

}
