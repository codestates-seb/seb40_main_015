import styled from 'styled-components';

// components
import Title from '../components/common/Title';
import Button from '../components/common/Button';
import {
	Main,
	BodyContainer,
	TitleWrapper,
	BookInfo,
} from '../components/Books/BookElements';
import BookCalendar from '../components/Books/BookCalendar';

const BooksBookingPage = () => {
	return (
		<Main>
			<TitleWrapper>
				<Title text={'예약하기'} />
			</TitleWrapper>

			<BodyContainer>
				<CalendarWrapper>
					<BookCalendar />
					<p>* 대여 가능 기간은 10일 입니다.</p>
				</CalendarWrapper>
				<RentalInfo>
					<label>✅ 2022.11.09~2022.11.19</label>
				</RentalInfo>
			</BodyContainer>

			<Button>예약 신청</Button>
		</Main>
	);
};

const CalendarWrapper = styled.div`
	p {
		margin: 0.6rem 0;
		font-size: 14px;

		margin-bottom: 3rem;
	}
`;
const RentalInfo = styled(BookInfo)`
	margin-bottom: 6rem;
`;

export default BooksBookingPage;
