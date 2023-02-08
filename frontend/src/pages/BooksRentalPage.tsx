import styled from 'styled-components';
import { useState } from 'react';
import { useLocation, useParams } from 'react-router-dom';

import {
	Title,
	Button,
	Main,
	BodyContainer,
	TitleWrapper,
	CalendarWrapper,
	BookCalendar,
} from 'components';
import { usePostBookRental } from 'api/hooks/books/usePostBookRental';
import { calcCalendarDate } from 'utils/calcCalendarDate';
import BookRentalInfo from 'components/Books/BookRentalInfo';

interface LinkProps {
	state: {
		bookTitle: string;
		rentalFee: number;
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

	const RentalCheckLists = [
		{
			legend: '대여 기간',
			id: 'rentalPeriod',
			name: 'period',
			checked: checkLists.period,
			label: rentalPeriod,
		},
		{
			legend: '도서 제목',
			id: 'book_title',
			name: 'title',
			checked: checkLists.title,
			label: state.bookTitle,
		},
		{
			legend: '대여료',
			id: 'book_fee',
			name: 'fee',
			checked: checkLists.fee,
			label: `${state.rentalFee}원`,
		},
	];

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
				{RentalCheckLists.map(list => {
					const { legend, id, checked, label, name } = list;
					return (
						<BookRentalInfo
							legend={legend}
							id={id}
							checked={checked}
							setChecked={() =>
								setCheckLists(prev => ({
									...prev,
									[name]: !checked,
								}))
							}
							label={label}
						/>
					);
				})}
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
