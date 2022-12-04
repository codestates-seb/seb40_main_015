package com.dongnebook.global.test;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dongnebook.domain.book.domain.Book;
import com.dongnebook.domain.book.domain.BookState;
import com.dongnebook.domain.book.dto.request.BookRegisterRequest;
import com.dongnebook.domain.book.repository.BookCommandRepository;
import com.dongnebook.domain.book.repository.BookQueryRepository;
import com.dongnebook.domain.member.domain.Member;
import com.dongnebook.domain.member.dto.request.MemberEditRequest;
import com.dongnebook.domain.member.repository.MemberRepository;
import com.dongnebook.domain.model.Location;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class BulkInsertController {

	private final JdbcTemplate jdbcTemplate;
	private final MemberRepository memberRepository;
	private final BookCommandRepository bookCommandRepository;
	int num =1000;
	@GetMapping("insert/member")
	public long BulkMemberInsert() {


		List<Member> list = IntStream.range(0, num)
			.mapToObj(i -> makeMember(Location.builder()
				.latitude(37.4831+((double)i/num))
				.longitude(126.9438+((double)i/num))
				.build(), i))
			.collect(Collectors.toList());
		long startTime = System.currentTimeMillis();
		jdbcTemplate.batchUpdate(
			"insert into member (address,avatar_url,avg_grade,latitude,longitude,nickname,password,user_id,id)"
				+" values(?,?,?,?,?,?,?,?,?)"
			, new BatchPreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					ps.setLong(9,i+1);
					ps.setString(1,list.get(i).getAddress());
					ps.setString(2,list.get(i).getAvatarUrl());
					ps.setLong(3,4L);
					ps.setDouble(4,list.get(i).getLocation().getLatitude());
					ps.setDouble(5,list.get(i).getLocation().getLongitude());
					ps.setString(6,list.get(i).getNickname());
					ps.setString(7,list.get(i).getPassword());
					ps.setString(8,list.get(i).getUserId());

				}

				@Override
				public int getBatchSize() {
					return list.size();
				}
			}
		);
		long endTime = System.currentTimeMillis();

		return endTime-startTime;

	}

	@GetMapping("insert/book")
	public long BulkBookInsert() {

		List<Book> list = IntStream.range(0, num)
			.mapToObj(i -> Book.create(BookRegisterRequest.builder().author("asdf"+i)
				.description("asdf")
				.imageUrl("asdf"+i)
				.publisher("Asdf"+i)
				.rentalFee(i)
				.title("asdf"+i)
				.build(),Location.builder()
				.latitude(37.4831+((double)i/num))
				.longitude(126.9438+((double)i/num))
				.build(), Member.builder().build()))
			.collect(Collectors.toList());
		long startTime = System.currentTimeMillis();
		jdbcTemplate.batchUpdate(
			"insert into book (id,img_url,author,publisher,book_state,latitude,longitude,title,member_id)"
				+" values(?,?,?,?,?,?,?,?,?)"
			, new BatchPreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					ps.setLong(1,i+1);
					ps.setString(2,list.get(i).getImgUrl());
					ps.setString(3,list.get(i).getAuthor());
					ps.setString(4,list.get(i).getPublisher());
					ps.setString(5, String.valueOf(BookState.RENTABLE));
					ps.setDouble(6,list.get(i).getLocation().getLatitude());
					ps.setDouble(7,list.get(i).getLocation().getLongitude());
					ps.setString(8,list.get(i).getTitle());
					ps.setLong(9,i+1);

				}

				@Override
				public int getBatchSize() {
					return list.size();
				}
			}
		);
		long endTime = System.currentTimeMillis();

		return endTime-startTime;

	}

	@GetMapping("insert")
	public void in(){
		int num = 10;
		for (int i = 0; i < num; i++) {
			Member member = makeMember(Location.builder()
				.latitude(37.4831 + ((double)i / num))
				.longitude(126.9438 + ((double)i / num))
				.build(), i);
			memberRepository.save(member);
			bookCommandRepository.save(Book.create(BookRegisterRequest.builder().author("asdf"+i)
				.description("asdf")
				.imageUrl("asdf"+i)
				.publisher("Asdf"+i)
				.rentalFee(i)
				.title("asdf"+i)
				.build(), Location.builder()
				.latitude(37.4831+((double)i/num))
				.longitude(126.9438+((double)i/num))
				.build(),member));
		}
	}

	private Member makeMember(Location location, int i) {
		Member build = Member.builder()
			.avatarUrl("asdf" + i)
			.userId("thwn" + i)
			.password("asdfasdf" + i)
			.nickname("test" + i)
			.build();
		build.edit(MemberEditRequest.builder().
			address("서울시 땡땡구 땡땡동")
			.avatarUrl(build.getAvatarUrl())
			.nickname(build.getNickname())
			.location(location)
			.build());

		return build;
	}

	private List<Location> getLocations() {
		return List.of(Location.builder()
			.latitude(37.4831+0.00001)
			.longitude(126.9438+0.00001)
			.build(), Location.builder()
			.latitude(37.4836)
			.longitude(126.9412)
			.build(), Location.builder()
			.latitude(37.4814)
			.longitude(126.9411)
			.build(), Location.builder()
			.latitude(37.4814)
			.longitude(126.9442)
			.build());
	}


}
