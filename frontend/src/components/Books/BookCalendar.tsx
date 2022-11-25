import { useState } from 'react';
import styled from 'styled-components';
import Calendar from 'react-calendar';
import 'react-calendar/dist/Calendar.css';

//책 상태: 대여가능
// const today = new Date();
// const marks = {
// 	rentalStatedAt: today.toISOString(),
// 	rentalDeadline: new Date(
// 		today.getFullYear(),
// 		today.getMonth(),
// 		today.getDate() + 9,
// 	).toISOString(),
// };

//날짜형식: https://gurtn.tistory.com/65

// 책 상태: 대여중
// const mark = {
// 	rentalStatedAt: '2022-11-24T00:17:34.045376400',
// 	rentalDeadline: '2022-12-03T23:59:59.045376400',
// };

// const rentalPeriod = convertDate(marks.rentalStatedAt, marks.rentalDeadline);

// const month = rentalPeriod
// 	.split('~')
// 	.map(el => el.trim().slice(5))
// 	.map(el => +el.split('.')[0]);

// const day = rentalPeriod
// 	.split('~')
// 	.map(el => el.trim().slice(5))
// 	.map(el => +el.split('.')[1]);

const rentalCalc = (date: Date, month: number[], day: number[]): string => {
	const m = date.getMonth() + 1;
	const d = date.getDate();

	if (month[0] === month[1] && month[0] === m) {
		if (d === day[0]) return 'highlightLeft';
		if (d === day[1]) return 'highlightRight';
		if (d > day[0] && d < day[1]) return 'highlight';
	} else {
		if (month[0] === m && d === day[0]) return 'highlightLeft';
		if (month[0] === m && d > day[0]) return 'highlight';
		if (month[1] === m && d < day[1]) return 'highlight';
		if (month[1] === m && d === day[1]) return 'highlightRight';
	}
	return '';
};

//type
interface CalendarProps {
	month: number[];
	day: number[];
}

const BookCalendar = ({ month, day }: CalendarProps) => {
	const [value, setValue] = useState(new Date());

	return (
		<CalendarWrapper>
			<Calendar
				onChange={setValue}
				value={value}
				minDetail="month"
				formatDay={(_, date) => {
					return `${date.getDate()}`;
				}}
				tileClassName={({ date }) => {
					return rentalCalc(date, month, day);
				}}
			/>
			<Screen />
		</CalendarWrapper>
	);
};

const CalendarWrapper = styled.div`
	position: relative;
	border-top: 1px solid black;
	border-bottom: 1px solid black;
	.react-calendar {
		border: none;
		margin: 4px 0;
	}
	.react-calendar__navigation {
		margin: 0;
	}

	.react-calendar__navigation button {
		min-width: 44px;
		background: none;
		font-size: 16px;
		margin-top: 8px;
		color: black;
	}
	.react-calendar__navigation button:enabled:hover,
	.react-calendar__navigation button:enabled:focus {
		background-color: transparent;
	}
	.react-calendar__navigation button[disabled] {
		font-weight: bold;
		background-color: transparent;
	}

	abbr {
		font-size: 1.4rem;
	}
	abbr[title] {
		text-decoration: none;
	}

	.react-calendar__month-view__days__day--weekend {
		// 주말 컬러
		/* color: inherit; */
	}

	.react-calendar__tile:enabled:hover,
	.react-calendar__tile:enabled:focus {
		// 기본 타일
		/* background: #f8f8fa;
		color: #6f48eb;
		border-radius: 6px; */
		background: inherit;
		/* background-color: transparent; */

		cursor: default;
	}

	.react-calendar__tile--now {
		// 오늘 날짜 타일
		/* background: #6f48eb33; */
		background: transparent;
		/* border-radius: 6px; */
		font-weight: bold;
		color: #6f48eb;
	}
	.react-calendar__tile--now:enabled:hover,
	.react-calendar__tile--now:enabled:focus {
		background: #6f48eb33;
		border-radius: 6px;
		font-weight: bold;
		color: #6f48eb;
	}

	.react-calendar__tile--active {
		color: inherit;
		background: transparent;
	}

	.highlight {
		background: rgba(38, 121, 93, 0.2);
	}
	.highlightLeft {
		color: black;
		font-weight: bold;
		background: rgba(38, 121, 93, 0.2);
		border-radius: 30% 0 0 30%;
	}
	.highlightRight {
		color: black;
		font-weight: bold;
		background: rgba(38, 121, 93, 0.2);
		border-radius: 0 30% 30% 0;
	}
`;
const Screen = styled.div`
	position: absolute;
	bottom: 0;
	width: 100%;
	height: 80%;
	background-color: transparent;
`;
export default BookCalendar;
