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
			{/* <BookInfo>
				<BookImage>
					<img src={BookImageDummy} alt="Book Image" />
				</BookImage>
				<BookDetail>
					<h1>책 제목은 h1</h1>
					<p>저자 / 출판사</p>
					<p>거래 지역</p>
					<p>상인이름</p>
					<p>대여료</p>
					<p>대여기간</p>
				</BookDetail>
			</BookInfo>

			<BookStateWrapper>
				<Button>대여 가능</Button>
				<Button backgroundColor={'grey'}>대여중</Button>
			</BookStateWrapper> */}
			<BookItemInfo
				title={title}
				bookImage={bookImage}
				publisher={publisher}
				author={author}
				merchantName={merchantName}
				rentalfee={rentalfee}
				rental={rental}
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
	margin: 10px 0;

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
