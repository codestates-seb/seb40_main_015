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
	MerchantGrade,
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
				{/* <label>{book?.title}</label>
				<label>author</label>
				<span className="partition">{''}</span>
				<label>{book?.publisher}</label> */}
				<BookContainer>
					<BookTitle>
						<label>{book?.title}</label>
					</BookTitle>
					<BookSubTitle>
						<label>author</label>
						<Partition>|</Partition>
						<label>{book?.publisher}</label>
					</BookSubTitle>
				</BookContainer>
			</BookInfo>

			{/* <BookInfo>
				<legend>저자/출판사</legend>
				<label>author</label>
				<span className="partition">{''}</span>
				<label>{book?.publisher}</label>
			</BookInfo> */}

			<BookInfo>
				<legend>상인 정보</legend>
				<MerchantInfo>
					<Link to={`/profile/merchant/${merchant?.merchantId}`}>
						<MerchantImg src={merchant?.avatarUrl} />
						<span>{merchant?.name}</span>
					</Link>
					<MerchantGrade>
						<span>평점: {merchant?.grade}★★★★★</span>
					</MerchantGrade>
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

			{/* <BookRentalFee>
				<label htmlFor="fee">대여료{book?.rentalFee}</label>
				<input
					id="fee"
					type="number"
					step="100"
					defaultValue={book?.rentalFee}
					disabled
				/>
				<span>원</span>
			</BookRentalFee> */}

			<BookDsc>
				<legend>내용</legend>
				<div>{book?.content}</div>
			</BookDsc>
		</>
	);
};

const MerchantImg = styled.img`
	border-radius: 0.3rem;
	/* border-radius: 50%; */
	width: 2.6rem;
	height: 2.6rem;

	margin-right: 10px;
`;

export default BookDetail;
