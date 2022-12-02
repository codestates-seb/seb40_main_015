import { useState } from 'react';

// components
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
import { calcCalendarDate } from '../utils/calcCalendarDate';
import { useLocation, useNavigate, useParams } from 'react-router-dom';
import { useBooksAPI } from '../api/books';
import { useMutation } from '@tanstack/react-query';
import notify from '../utils/notify';
import { useDispatch } from 'react-redux';
import styled from 'styled-components';

interface LinkProps {
	state: {
		bookTitle: string;
		rentalFee?: number;
	};
}

const BooksRentalPage = () => {
	const [isCheckedPeriod, setIsCheckedPeriod] = useState(false);
	const [isCheckedTitle, setIsCheckedTitle] = useState(false);
	const [isCheckedFee, setIsCheckedFee] = useState(false);
	const { month, day, rentalPeriod } = calcCalendarDate(
		new Date().toISOString(),
	);
	const { bookId } = useParams();
	const { state } = useLocation() as LinkProps;
	const navigate = useNavigate();
	const dispatch = useDispatch();

	const { postBookRental } = useBooksAPI();
	const { mutate } = useMutation({
		mutationFn: () => postBookRental(bookId),
		onSuccess: () => {
			notify(dispatch, '대여 신청이 완료되었습니다.');
			navigate('/history');
		},
	});

	const handleRentalButton = () => {
		if (!isCheckedPeriod) return alert('대여 기간을 확인해주세요');
		if (!isCheckedTitle) return alert('도서 제목을 확인해주세요');
		if (!isCheckedFee) return alert('대여료를 확인해주세요');

		const isTrue = window.confirm('대여 신청 하시겠습니까?');
		isTrue && mutate();
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
								setIsCheckedPeriod(!isCheckedPeriod);
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
								setIsCheckedTitle(!isCheckedTitle);
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
								setIsCheckedFee(!isCheckedFee);
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
