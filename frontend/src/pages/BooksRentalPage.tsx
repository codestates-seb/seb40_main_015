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

/*
//책 상태: 대여가능, 아래 코드 util / calcCalendarDate로 옮김
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

  */
const BooksRentalPage = () => {
	const [isChecked, setIsChecked] = useState(false);
	const { month, day, rentalPeriod } = calcCalendarDate(
		new Date().toISOString(),
	);

	const handleRentalButton = () => {
		if (!isChecked) return alert('대여 기간을 확인해주세요');
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
					{/* <label>✅</label> */}
					{/* <label>대여일 : {marks.rentalStatedAt.slice(0, 10)}</label> */}
					{/* <label>~</label> */}
					{/* <label>반납일 : {marks.rentalDeadline.slice(0, 10)}</label> */}

					<RentalCheck>
						<input
							type="checkbox"
							required
							id="rentalPeriod"
							onInput={() => {
								setIsChecked(!isChecked);
							}}
						/>
						<label htmlFor="rentalPeriod">확인</label>
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
