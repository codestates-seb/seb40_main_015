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

//책 상태: 예약가능

interface LinkProps {
	state: {
		rentalStart: string;
		rentalEnd: string;
	};
}
const BooksBookingPage = () => {
	const [isChecked, setIsChecked] = useState(false);
	// const { state } = useLocation() as LinkProps;
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
	// 외부에서 예약하기 페이지 접근시
	if (state === null) return <h1>Page Not found</h1>;

	const { month, day, rentalPeriod } = calcCalendarDate(state.rentalEnd);
	// 예약 요청
	const handleRentalButton = () => {
		if (!isChecked) return alert('대여 기간을 확인해주세요');
		const isTrue = window.confirm('예약 신청 하시겠습니까?');
		isTrue && mutate();
	};

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
								setIsChecked(!isChecked);
							}}
						/>
						<label htmlFor="rentalPeriod" className="checkBoxLabel">
							확인
						</label>
						<label>{rentalPeriod}</label>
					</RentalCheck>
				</RentalInfo>

				<RentalInfo>
					<legend>주의 사항</legend>
					<label>*아직 준비중 입니다*</label>
				</RentalInfo>
				<RentalInfo>
					<legend>결제 내용</legend>
					<label>*아직 준비중 입니다*</label>
				</RentalInfo>
			</BodyContainer>

			<Button onClick={handleRentalButton}>예약 신청</Button>
		</Main>
	);
};

export default BooksBookingPage;
