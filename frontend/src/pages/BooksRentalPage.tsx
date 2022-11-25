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

//책 상태: 대여가능
const today = new Date();
const marks = {
	rentalStatedAt: today.toISOString(),
	rentalDeadline: new Date(
		today.getFullYear(),
		today.getMonth(),
		today.getDate() + 9,
	).toISOString(),
};
const rentalPeriod = convertDate(marks.rentalStatedAt, marks.rentalDeadline);
const month = rentalPeriod
	.split('~')
	.map(el => el.trim().slice(5))
	.map(el => +el.split('.')[0]);

const day = rentalPeriod
	.split('~')
	.map(el => el.trim().slice(5))
	.map(el => +el.split('.')[1]);

const BooksRentalPage = () => {
	return (
		<Main>
			<TitleWrapper>
				<Title text={'대여하기'} />
			</TitleWrapper>

			<BodyContainer>
				<CalendarWrapper>
					<BookCalendar month={month} day={day} />
					<p>* 대여 가능 기간은 10일 입니다.</p>
				</CalendarWrapper>
				<RentalInfo>
					<label>✅</label>
					<label>대여일 : {marks.rentalStatedAt.slice(0, 10)}</label>
					<label>~</label>
					<label>반납일 : {marks.rentalDeadline.slice(0, 10)}</label>
				</RentalInfo>
			</BodyContainer>

			<Button>대여 신청</Button>
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
	display: flex;
	justify-content: space-between;
	label:nth-child(1) {
		margin-right: 1rem;
	}
`;

export default BooksRentalPage;
