import styled from 'styled-components';
import { Calendar } from 'react-calendar';

// components
import Title from '../components/common/Title';
import Button from '../components/common/Button';
import {
	Main,
	BodyContainer,
	TitleWrapper,
	BookInfo,
} from '../components/Books/BookElements';

const BooksRentalPage = () => {
	return (
		<Main>
			<TitleWrapper>
				<Title text={'대여하기'} />
			</TitleWrapper>

			<BodyContainer>
				<CalendarWrapper>
					<div>달력</div>
					<Calendar />
				</CalendarWrapper>

				<RentalInfo>
					<h1>대여 신청하기</h1>
					<p>대여 가능 기간은 10일 입니다.</p>
				</RentalInfo>
				<BookInfo>
					<h1>대여 신청하기</h1>
					<p>대여 가능 기간은 10일 입니다.</p>
				</BookInfo>
				<BookInfo>
					<input type="checkbox" />
					<label>2022.11.09~2022.11.19</label>
				</BookInfo>
			</BodyContainer>

			<Button>대여 신청</Button>
		</Main>
	);
};

const CalendarWrapper = styled.div``;
const RentalInfo = styled.div``;

export default BooksRentalPage;
