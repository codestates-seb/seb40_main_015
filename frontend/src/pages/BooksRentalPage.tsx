import styled from 'styled-components';
import { useState } from 'react';
import { useLocation, useParams } from 'react-router-dom';

import Title from '../components/common/Title';
import Button from '../components/common/Button';
import {
	Main,
	BodyContainer,
	TitleWrapper,
	CalendarWrapper,
	RentalCheck,
	RentalInfo,
} from '../components/Books/BookElements';
import BookCalendar from '../components/Books/BookCalendar';

import { usePostBookRental } from '../api/hooks/books/usePostBookRental';
import { calcCalendarDate } from '../utils/calcCalendarDate';
import { title } from 'process';

interface LinkProps {
	state: {
		bookTitle: string;
		rentalFee?: number;
	};
}

const BooksRentalPage = () => {
	const [checkLists, setCheckLists] = useState({
		period: false,
		title: false,
		fee: false,
	});
	const { month, day, rentalPeriod } = calcCalendarDate(
		new Date().toISOString(),
	);
	const { bookId } = useParams();
	const { state } = useLocation() as LinkProps;

	const { mutateBookRental } = usePostBookRental(bookId);

	const handleRentalButton = () => {
		if (!checkLists.period) return alert('대여 기간을 확인해주세요');
		if (!checkLists.title) return alert('도서 제목을 확인해주세요');
		if (!checkLists.fee) return alert('대여료를 확인해주세요');

		const isTrue = window.confirm('대여 신청 하시겠습니까?');
		isTrue && mutateBookRental();
	};

	return (
		<Main>
			<TitleWrapper>
				<Title text={'대여하기'} />
			</TitleWrapper>
			<BodyContainer>
				<CalendarWrapper>
					<BookCalendar month={month} day={day} />
					<p>
						* 대여 기간은 금일부터 <strong>10일</strong> 입니다.
					</p>
				</CalendarWrapper>
				<RentalInfo>
					<legend>대여 기간</legend>
					<RentalCheck>
						<input
							type="checkbox"
							required
							id="rentalPeriod"
							onInput={() => {
								setCheckLists(prev => ({
									...prev,
									period: !checkLists.period,
								}));
							}}
						/>
						<label htmlFor="rentalPeriod" className="checkBoxLabel">
							확인
						</label>
						<label>{rentalPeriod}</label>
					</RentalCheck>
				</RentalInfo>

				<RentalInfo>
					<legend>도서 제목</legend>
					<RentalCheck>
						<input
							type="checkbox"
							required
							id="book_title"
							onInput={() => {
								setCheckLists(prev => ({
									...prev,
									title: !checkLists.title,
								}));
							}}
						/>
						<label htmlFor="book_title" className="checkBoxLabel">
							확인
						</label>
						<label>{state.bookTitle}</label>
					</RentalCheck>
				</RentalInfo>

				<RentalInfo>
					<legend>대여료</legend>
					<RentalCheck>
						<input
							type="checkbox"
							required
							id="book_fee"
							onInput={() => {
								setCheckLists(prev => ({
									...prev,
									fee: !checkLists.fee,
								}));
							}}
						/>
						<label htmlFor="book_fee" className="checkBoxLabel">
							확인
						</label>
						<label>{state.rentalFee}원</label>
					</RentalCheck>
				</RentalInfo>
			</BodyContainer>
			<BtnWrapper>
				<Button onClick={handleRentalButton}>대여 신청</Button>
			</BtnWrapper>
		</Main>
	);
};

const BtnWrapper = styled.div`
	margin-top: 20px;
	width: 400px;

	display: flex;
	justify-content: center;

	button {
		height: 3rem;
		width: inherit;
	}
`;

export default BooksRentalPage;
