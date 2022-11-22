import styled from 'styled-components';
import convertDate from '../../utils/convertDate';
import { BooksProps, RentalProps } from './type';

// 대여목록 페이지에서 대여기간 표시할때 사용하는 함수
const rentalPeriodConversion = ({
	rentalState,
	rentalStartedAt,
	rentalDeadline,
	rentalReturnedAt,
	rentalCanceledAt,
}: RentalProps) => {
	{
		/* <p>대여기간</p> */
	}
	if (rentalState === 'TRADING' || rentalState === 'BEING_RENTED')
		return <p>{convertDate(rentalStartedAt, rentalDeadline, true)}</p>;
	if (rentalState === 'RETURN_UNREVIEWED' || rentalState === 'RETURN_REVIEWED')
		return <p>{convertDate(rentalStartedAt, rentalReturnedAt, true)}</p>;
	if (rentalState === 'CANCELED')
		return <p>{convertDate(rentalStartedAt, rentalCanceledAt, true)}</p>;
};

const BookItemInfo = ({
	title = '',
	bookImage = '',
	publisher = '',
	author = '',
	rentalfee = 0,
	merchantName = '',
	rental = {
		rentalId: '',
		customerName: '',
		rentalState: '',
		rentalStartedAt: '',
		rentalDeadline: '',
		rentalReturnedAt: '',
		rentalCanceledAt: '',
	},
}: BooksProps) => {
	return (
		<BookInfo>
			<BookImage>
				<img src={bookImage} alt="Book" />
			</BookImage>
			<BookDetail>
				<BookTitle>{title}</BookTitle>
				<p>{author ? `${author} / ${publisher}` : ''}</p>
				<p>{merchantName}</p>
				<p>{`${rentalfee} 원`}</p>
				{/* {rentalPeriodConversion(rental)} */}
			</BookDetail>
		</BookInfo>
	);
};

const BookInfo = styled.div`
	display: flex;
`;

const BookImage = styled.div`
	margin-right: 16px;
	img {
		width: 9em;
		height: 11rem;
		background-color: hotpink;
	}
`;
const BookDetail = styled.div`
	display: flex;
	flex-direction: column;
	justify-content: space-between;
	padding: 5px 0;
	p {
		padding: 3px;
	}
`;

const BookTitle = styled.h1`
	font-size: ${props => props.theme.fontSizes.subtitle};
	padding: 5px 0;
`;

export default BookItemInfo;
