import styled from 'styled-components';
import { Link } from 'react-router-dom';

//components
import Button from '../common/Button';
//data
import BookImageDummy from '../../assets/image/dummy.png';
import BookItemInfo from './BookItemInfo';
import BookItemState from './BookItemState';
import { BooksProps } from './type';

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
}: BooksProps) => {
	return (
		<BookContainer to={`/books/${bookId}`}>
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

const BookContainer = styled(Link)`
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
`;
// const BookImage = styled.div`
// 	background-color: pink;
// 	margin-right: 20px;

// 	img {
// 		width: 100px;
// 		height: 100px;
// 	}
// `;
// const BookInfo = styled.div`
// 	display: flex;
// `;
// const BookStateWrapper = styled.div`
// 	display: flex;
// 	justify-content: center;
// 	align-items: center;
// `;
// const BookDetail = styled.div`
// 	display: flex;
// 	flex-direction: column;
// 	justify-content: space-between;
// 	padding: 5px 0;
// 	div {
// 		margin-bottom: 10px;
// 	}
// `;

export default BookItem;
