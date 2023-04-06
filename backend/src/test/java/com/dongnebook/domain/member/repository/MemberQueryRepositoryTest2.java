package com.dongnebook.domain.member.repository;

import com.dongnebook.config.TestConfig;
import com.dongnebook.domain.book.adapter.out.BookCommandRepository;
import com.dongnebook.domain.book.domain.Book;
import com.dongnebook.domain.book.domain.BookProduct;
import com.dongnebook.domain.book.domain.Money;
import com.dongnebook.domain.member.domain.Member;
import com.dongnebook.domain.member.dto.request.MerchantSearchRequest;
import com.dongnebook.domain.member.dto.response.MemberDetailResponse;
import com.dongnebook.domain.member.dto.response.MemberResponse;
import com.dongnebook.domain.model.Location;
import com.dongnebook.global.dto.request.PageRequestImpl;
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
import org.springframework.data.domain.SliceImpl;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest(showSql = false)
@ImportAutoConfiguration(DataSourceDecoratorAutoConfiguration.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({TestConfig.class, DatabaseCleaner.class})
@ExtendWith(DataClearExtension.class)
public class MemberQueryRepositoryTest2 {
    @Autowired
    MemberQueryRepository memberQueryRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    BookCommandRepository bookCommandRepository;

    static Member savedMerchant;
    static Book savedBook;


    @BeforeEach
    public void setting() {
        Member merchant = Member.builder()
                .userId("test")
                .avatarUrl("aaa@aa.com")
                .nickname("tester")
                .password("12341234")
                .build();
        savedMerchant = memberRepository.save(merchant);

        BookProduct bookProduct1 = new BookProduct("수학의 정석1", "남궁성", "도우출판");
        Book book1 = Book.builder()
                .id(1L)
                .bookProduct(bookProduct1)
                .imgUrl("aaa@abc.com")
                .description("기본이 중요")
                .rentalFee(Money.of(1000))
                .location(new Location(37.5340, 126.7064))
                .member(savedMerchant)
                .build();
        savedBook = bookCommandRepository.save(book1);
    }

    @Test
    public void getAllTest() {
        // given
        Location location = new Location(37.482475661, 126.941669283);
        MerchantSearchRequest condition = new MerchantSearchRequest(location.getLongitude(), location.getLatitude(),
                10, 10, 5, 3);
        List<Double> latRangeList = Location.latRangeList(condition.getLatitude(), condition.getHeight(),
                condition.getLevel());
        List<Double> lonRangeList = Location.lonRangeList(condition.getLongitude(), condition.getWidth(),
                condition.getLevel());
        PageRequestImpl pageRequest = new PageRequestImpl(1L);

        // when
        SliceImpl<MemberResponse> memberResponseSlice = memberQueryRepository.getAll(latRangeList, lonRangeList, condition, pageRequest);

        // then
        assertThat(memberResponseSlice.getContent()).isEmpty();
    }

    @Test
    public void findByMemberWithRentalTest(){
        // given

        // when
        Optional<Member> memberOptional = memberQueryRepository.findByMemberWithRental(savedMerchant.getId());

        // then
        assertTrue(memberOptional.isPresent());
    }

    @Test
    public void findMyInfoTest(){
        // given

        // when
        Optional<MemberDetailResponse> memberDetailResponseOptional = memberQueryRepository.findMyInfo(savedMerchant.getId());

        // then
        assertTrue(memberDetailResponseOptional.isPresent());
    }
}
