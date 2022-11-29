import styled, { css } from 'styled-components';
import { Link, useNavigate } from 'react-router-dom';

//components
import Button from '../common/Button';
//data
import BookImageDummy from '../../assets/image/dummy.png';
import BookItemInfo from './BookItemInfo';
import BookItemState from './BookItemState';
import { BooksProps } from './type';

interface IBookContainer {
	styleGrid: boolean;
}

const BookItem = ({
	title = '',
	bookId = '',
	bookImage = '',
	publisher = '',
	author = '',
	rentalfee = 0,
	merchantName = '',
	status = '',
	rental = {
		rentalId: '',
		customerName: '',
		rentalState: '',
		rentalStartedAt: '',
		rentalDeadline: '',
		rentalReturnedAt: '',
		rentalCanceledAt: '',
	},
	styleGrid = false,
}: BooksProps) => {
	const navigate = useNavigate();
	const handleBookDetailPage = () => {
		navigate(`/books/${bookId}`);
	};
	return (
		// <BookContainer to={`/books/${bookId}`}>
		<BookContainer onClick={handleBookDetailPage} styleGrid={styleGrid}>
			<BookItemInfo
				title={title}
				bookImage={bookImage}
				publisher={publisher}
				author={author}
				rentalfee={rentalfee}
				rental={rental}
				merchantName={merchantName}
			/>
			<BookItemState
				status={status}
				merchantName={merchantName}
				rental={rental}
			/>
		</BookContainer>
	);
};

const BookContainer = styled.div<IBookContainer>`
	display: flex;
	justify-content: space-between;
	width: 90vw;

	padding: 14px;
	margin-bottom: 6px;

	border: 1px solid rgba(1, 1, 1, 0.1);
	border-radius: 5px;

	cursor: pointer;

	&:hover {
		background-color: ${props => props.theme.colors.grey};
		/* background-color: rgba(0, 0, 0, 0.1); */
	}

	// 책전체페이지 그리드
	${props =>
		props.styleGrid &&
		css`
			@media screen and (min-width: 801px) {
				width: 380px;
			}
		`}
`;

export default BookItem;
