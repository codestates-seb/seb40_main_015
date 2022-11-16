import { useState } from 'react';
import styled from 'styled-components';
import Calendar from 'react-calendar';
import 'react-calendar/dist/Calendar.css';

const BookCalendar = () => {
	const [value, setValue] = useState(new Date());

	return (
		<CalendarWrapper>
			<Calendar onChange={setValue} value={value} minDetail="month" />
		</CalendarWrapper>
	);
};

const CalendarWrapper = styled.div`
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
	}

	.react-calendar__navigation button[disabled] {
		background-color: transparent;
	}

	abbr[title] {
		text-decoration: none;
	}

	.react-calendar__month-view__days__day--weekend {
		// 주말 컬러
		/* color: blue; */
	}

	.react-calendar__tile:enabled:hover,
	.react-calendar__tile:enabled:focus {
		// 기본 타일
		background: #f8f8fa;
		color: #6f48eb;
		border-radius: 6px;
	}

	.react-calendar__tile--now {
		// 오늘 날짜 타일
		/* background: #6f48eb33; */
		background: transparent;
		border-radius: 6px;
		font-weight: bold;
		/* color: #6f48eb; */
	}
	.react-calendar__tile--now:enabled:hover,
	.react-calendar__tile--now:enabled:focus {
		/* background: #6f48eb33; */
		border-radius: 6px;
		font-weight: bold;
		/* color: #6f48eb; */
	}

	.react-calendar__tile--active {
		color: inherit;
		background: transparent;
	}
`;

export default BookCalendar;
