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
	rentalfee = 100,
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
				{/* <BookTitle>{title}</BookTitle> */}
				<BookTitle>
					{title.length < 15 ? title : title.slice(0, 15) + '...'}
				</BookTitle>
				{author !== '' ? (
					<p>
						{author} / {publisher}
					</p>
				) : (
					''
				)}
				<p>{merchantName}</p>
				<p>{`${rentalfee} 원`}</p>
				{/* {rentalPeriodConversion(rental)} */}
			</BookDetail>
		</BookInfo>
	);
};

const BookInfo = styled.div`
	/* width: 100vw; */
	display: flex;
`;

const BookImage = styled.div`
	margin-right: 16px;
	img {
		width: 9em;
		height: 11rem;
		background-color: ${props => props.theme.colors.grey};
	}
`;
const BookDetail = styled.div`
	width: 58%;
	display: flex;
	flex-direction: column;
	/* justify-content: space-between; */
	padding: 5px 0;
	p {
		font-size: 1.2rem;
		margin: 0.4rem 0;
	}
`;

const BookTitle = styled.h1`
	font-size: ${props => props.theme.fontSizes.subtitle};
	@media screen and (min-width: 801px) {
		font-size: 20px;
	}
	padding: 5px 0;
`;

export default BookItemInfo;
