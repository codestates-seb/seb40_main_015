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
import convertDate from '../utils/convertDate';
import { useLocation } from 'react-router-dom';

//책 상태: 예약가능

interface LinkProps {
	state: {
		rentalStart: string;
		rentalEnd: string;
	};
}
const BooksBookingPage = () => {
	const { state } = useLocation() as LinkProps;
	const rentalPeriod = convertDate(state.rentalStart, state.rentalEnd);
	const month = rentalPeriod
		.split('~')
		.map(el => el.trim().slice(5))
		.map(el => +el.split('.')[0]);

	const day = rentalPeriod
		.split('~')
		.map(el => el.trim().slice(5))
		.map(el => +el.split('.')[1]);

	return (
		<Main>
			<TitleWrapper>
				<Title text={'예약하기'} />
			</TitleWrapper>

			<BodyContainer>
				<CalendarWrapper>
					<BookCalendar month={month} day={day} />
					{/* <p>* 대여 가능 기간은 10일 입니다.</p> */}
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
	margin-bottom: 4rem;
`;

export default BooksBookingPage;
