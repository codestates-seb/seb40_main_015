import { useLocation, useNavigate, useParams } from 'react-router-dom';
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
import { useBooksAPI } from '../api/books';
import { useMutation } from '@tanstack/react-query';
import { useDispatch } from 'react-redux';
import notify from '../utils/notify';
import styled from 'styled-components';

//책 상태: 예약가능

interface LinkProps {
	state: {
		rentalStart: string;
		rentalEnd: string;
		bookTitle: string;
		rentalFee?: number;
	};
}
const BooksBookingPage = () => {
	const [isCheckedPeriod, setIsCheckedPeriod] = useState(false);
	const [isCheckedTitle, setIsCheckedTitle] = useState(false);
	const [isCheckedFee, setIsCheckedFee] = useState(false);

	const { state } = useLocation() as LinkProps;
	const navigate = useNavigate();
	const { bookId } = useParams();
	const { postBookBooking } = useBooksAPI();
	const dispatch = useDispatch();
	const { mutate } = useMutation({
		mutationFn: () => postBookBooking(bookId),
		onSuccess: res => {
			notify(dispatch, '예약 신청이 완료되었습니다.');
			navigate('/profile');
		},
		onError: res => {
			notify(dispatch, '이미 대여 신청한 도서 입니다.');
		},
	});

	const { month, day, rentalPeriod } = calcCalendarDate(state.rentalEnd);
	// 예약 요청
	const handleRentalButton = () => {
		if (!isCheckedPeriod) return alert('대여 기간을 확인해주세요');
		if (!isCheckedTitle) return alert('도서 제목을 확인해주세요');
		if (!isCheckedFee) return alert('대여료를 확인해주세요');

		const isTrue = window.confirm('예약 신청 하시겠습니까?');
		isTrue && mutate();
	};

	// 외부에서 예약하기 페이지 접근시
	if (state === null)
		return (
			<Main>
				<h1>Page Not found</h1>
			</Main>
		);

	return (
		<Main>
			<TitleWrapper>
				<Title text={'예약하기'} />
			</TitleWrapper>
			<BodyContainer>
				<CalendarWrapper>
					<BookCalendar
						year={+state.rentalEnd.slice(0, 4)}
						month={month}
						day={day}
					/>
					<p>
						* 대여 기간은 반납일로부터 <strong>10일</strong> 입니다.
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
				{/* <RentalInfo>
					<legend>주의 사항</legend>
					<label>*아직 준비중 입니다*</label>
				</RentalInfo>
				<RentalInfo>
					<legend>결제 내용</legend>
					<label>*아직 준비중 입니다*</label>
				</RentalInfo> */}
			</BodyContainer>

			<BtnWrapper>
				<Button onClick={handleRentalButton}>예약 신청</Button>
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

export default BooksBookingPage;
