import styled from 'styled-components';
import { HiHeart, HiOutlineHeart, HiOutlineTrash } from 'react-icons/hi';
import { Link } from 'react-router-dom';

//components
import {
	BodyContainer,
	BookDsc,
	BookInfo,
	BookRentalFee,
	MerchantInfo,
} from './BookElements';

//type
interface IBookDetail {
	bookId: number;
	content: string;
	publisher: string;
	rentalEnd: string | null;
	rentalFee?: number;
	rentalStart: string | null;
	state?: string;
	title: string;
}
interface BookMerchant {
	grade: number;
	merchantId: number;
	name: string;
}
interface BookDetailProps {
	book: IBookDetail | undefined;
	merchant: BookMerchant | undefined;
}
const BookDetail = ({ book, merchant }: BookDetailProps) => {
	return (
		<BodyContainer>
			<BookImgWrapper>
				<BookImg src={''} />
				<BookNotAvailable>
					<span>이미 누가 대여중이에요 😭</span>
					<span>2022/1104~2022/11/18</span>
					<span>예약 가능</span>
					<span>예약중</span>
				</BookNotAvailable>
				<WishiconOn />
				<WishiconOff />
				<Deleticon />
			</BookImgWrapper>

			<BookInfo>
				<legend>제목</legend>
				<label>{book?.title}</label>
			</BookInfo>

			<BookInfo>
				<legend>저자/출판사</legend>
				<label>author</label>
				<span className="partition">{''}</span>
				<label>{book?.publisher}</label>
			</BookInfo>

			<MerchantInfo>
				<div>
					<Link to={`/profile/merchant/${merchant?.merchantId}`}>
						<UserImg src={''} />
						<span>{merchant?.name}</span>
					</Link>
				</div>
				<div>
					<span>평점</span>
					<span>★★★★★{merchant?.grade}</span>
				</div>
			</MerchantInfo>

			<BookRentalFee>
				<label htmlFor="fee">대여료{book?.rentalFee}</label>
				<input
					id="fee"
					type="number"
					step="100"
					defaultValue={book?.rentalFee}
					disabled
				/>
				<span>원</span>
			</BookRentalFee>

			<BookDsc>
				<div>{book?.content}</div>
			</BookDsc>
		</BodyContainer>
	);
};

const BookImgWrapper = styled.div`
	width: 40vh;
	display: flex;
	justify-content: center;
	align-items: center;
	margin-bottom: 30px;
	position: relative;
`;
const Deleticon = styled(HiOutlineTrash)`
	font-size: 30px;
	position: absolute;
	top: 0;
	right: 1rem;
	cursor: pointer;
`;
const WishiconOn = styled(HiHeart)`
	font-size: 30px;
	position: absolute;
	right: 1rem;
	bottom: 0;
	cursor: pointer;
`;
const WishiconOff = styled(HiOutlineHeart)`
	font-size: 30px;
	position: absolute;
	right: 2rem;
	bottom: 0;
	cursor: pointer;
`;
const BookImg = styled.img`
	width: 200px;
	height: 240px;
`;
const BookNotAvailable = styled.div`
	width: 200px;
	height: 240px;
	background-color: rgba(1, 1, 1, 0.4);
	position: absolute;
	left: 0;

	display: flex;
	flex-direction: column;
	justify-content: center;
	align-items: center;
	span {
		background-color: transparent;
		&:nth-child(2) {
			color: red;
		}
		&:last-child {
			color: white;
		}
	}
`;

const UserImg = styled.img`
	border-radius: 0.3rem;
	width: 2.6rem;
	height: 2.6rem;

	margin-right: 10px;
`;

export default BookDetail;
