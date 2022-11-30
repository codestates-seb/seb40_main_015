import styled from 'styled-components';
import { Link } from 'react-router-dom';

// types
import { BookDetailProps } from './type';

//components
import {
	BookDsc,
	BookInfo,
	BookRentalFee,
	MerchantInfo,
	Chat,
	BookContainer,
	BookTitle,
	BookSubTitle,
	Partition,
	BookRentalInfo,
} from './BookElements';

const BookDetail = ({ book, merchant }: BookDetailProps) => {
	return (
		<>
			<BookInfo>
				<legend>도서 정보</legend>
				<BookContainer>
					<BookTitle>
						<label>{book?.title}</label>
					</BookTitle>
					<BookSubTitle>
						<label>{book?.author}</label>
						<Partition>|</Partition>
						<label>{book?.publisher}</label>
					</BookSubTitle>
				</BookContainer>
			</BookInfo>

			<BookInfo>
				<legend>상인 정보</legend>
				<MerchantInfo>
					<Link to={`/profile/merchant/${merchant?.merchantId}`}>
						<MerchantImg src={merchant?.avatarUrl} />
						<MerchantName>
							<span>{merchant?.name}</span>
							<span>평점: {merchant?.grade} /5</span>
						</MerchantName>
					</Link>
					<Chat>
						<span>(채팅하기)</span>
					</Chat>
				</MerchantInfo>
			</BookInfo>

			<BookInfo>
				<legend>대여 정보</legend>
				<BookRentalInfo>
					<label>대여료: {book?.rentalFee?.toLocaleString()}원</label>
					{/* <Partition>|</Partition> */}
					<label>대여기간: 10일</label>
				</BookRentalInfo>
			</BookInfo>

			<BookDsc>
				<legend>내용</legend>
				<div>{book?.content}</div>
			</BookDsc>
		</>
	);
};

const MerchantImg = styled.img`
	border-radius: 0.3rem;
	border-radius: 50%;
	width: 2.2rem;
	height: 2.2rem;

	margin-right: 8px;
`;
const MerchantName = styled.div`
	display: flex;
	flex-direction: column;
	span:last-child {
		margin-left: 2px;
		font-size: 12px;
	}
`;

export default BookDetail;
