import styled from 'styled-components';
import BookImageDummy from '../../assets/image/dummy.png';
import { BooksProps } from './type';

const BookItemInfo = ({
	title = '',
	bookImage = '',
	publisher = '',
	author = '',
	rentalfee = 0,
	merchantName = '',
}: BooksProps) => {
	return (
		<BookInfo>
			<BookImage>
				<img src={bookImage} alt="Book" />
			</BookImage>
			<BookDetail>
				<BookTitle>{title}</BookTitle>
				<p>
					{author} / {publisher}
				</p>
				<p>{merchantName}</p>
				<p>{`${rentalfee} 원`}</p>
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
