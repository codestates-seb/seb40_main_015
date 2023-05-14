import styled from 'styled-components';
import { useLocation, useNavigate, useParams } from 'react-router-dom';
import { useEffect, useState } from 'react';

// components
import {
	Title,
	Button,
	Main,
	BodyContainer,
	TitleWrapper,
	CalendarWrapper,
	RentalCheck,
	RentalInfo,
	BookCalendar,
} from 'components';

import { calcCalendarDate } from 'utils/calcCalendarDate';
import { usePostBookBooking } from 'api/hooks/books/usePostBookBooking';
import { useGetCheckBooking } from 'api/hooks/books/useCheckBooking';
import notify from 'utils/notify';
import { useDispatch } from 'react-redux';

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
	const { bookId } = useParams();
	const navigate = useNavigate();
	const dispatch = useDispatch();

	const { month, day, rentalPeriod } = calcCalendarDate(state.rentalEnd);

	const { mutateBookBooking } = usePostBookBooking(bookId);
	const { checkBooking } = useGetCheckBooking(bookId);

	useEffect(() => {
		if (!checkBooking) {
			notify(dispatch, '해당 도서의 대여자는 예약할 수 없습니다.');
			navigate(`/books/${bookId}`);
		}
	}, []);

	// 예약 요청
	const handleRentalButton = () => {
		if (!isCheckedPeriod) return alert('대여 기간을 확인해주세요');
		if (!isCheckedTitle) return alert('도서 제목을 확인해주세요');
		if (!isCheckedFee) return alert('대여료를 확인해주세요');

		const isTrue = window.confirm('예약 신청 하시겠습니까?');
		isTrue && mutateBookBooking();
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
