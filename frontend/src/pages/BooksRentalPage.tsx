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
import { useNavigate, useParams } from 'react-router-dom';
import { useBooksAPI } from '../api/books';
import { useMutation } from '@tanstack/react-query';
import notify from '../utils/notify';
import { useDispatch } from 'react-redux';

const BooksRentalPage = () => {
	const [isChecked, setIsChecked] = useState(false);
	const { month, day, rentalPeriod } = calcCalendarDate(
		new Date().toISOString(),
	);
	const { bookId } = useParams();
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
		if (!isChecked) return alert('대여 기간을 확인해주세요');
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

			<Button onClick={handleRentalButton}>대여 신청</Button>
		</Main>
	);
};

export default BooksRentalPage;
