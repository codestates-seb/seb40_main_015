import styled from 'styled-components';
import { Link } from 'react-router-dom';
import { HiOutlineHeart, HiHeart, HiOutlineTrash } from 'react-icons/hi';

//conmponents
import Title from '../components/common/Title';
import BookImage from '../assets/image/dummy.png';
import UserImage from '../assets/image/user.png';
import Button from '../components/common/Button';
import {
	Main,
	BodyContainer,
	TitleWrapper,
	BookInfo,
	BookRentalFee,
	MerchantInfo,
	BookDsc,
	LinkStyled,
} from '../components/Books/BookElements';
const BooksDetailPage = () => {
	return (
		<Main>
			<TitleWrapper>
				<Title text="상세 조회" />
			</TitleWrapper>

			<BodyContainer>
				<BookImgWrapper>
					<BookImg src={BookImage} />
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
					<label>모던 자바스크립트 Deep Dive</label>
				</BookInfo>
				<BookInfo>
					<legend>저자/출판사</legend>
					<label>이웅모</label>
					<span className="partition">{''}</span>
					<label>위키북스</label>
				</BookInfo>

				<MerchantInfo>
					<div>
						<Link to={'/profile/merchant'}>
							<UserImg src={UserImage} />
							<span>자바북스</span>
						</Link>
					</div>
					<div>
						<span>평점</span>
						<span>★★★★★</span>
					</div>
				</MerchantInfo>
				<BookRentalFee>
					<label htmlFor="fee">대여료</label>
					<input
						id="fee"
						type="number"
						step="100"
						defaultValue={'1000'}
						disabled
					/>
					<span>원</span>
				</BookRentalFee>
				<BookDsc>
					<span>재미있어요</span>
					<div>재미있어요</div>
					<div>재미있어요</div>
					<div>재미있어요</div>
					<div>재미있어요</div>
					<div>재미있어요</div>
				</BookDsc>
			</BodyContainer>

			<LinkStyled to={`rental`}>
				<Button>책 대여하기</Button>
			</LinkStyled>
			<LinkStyled to={`booking`}>
				<Button>책 예약하기</Button>
				<Button backgroundColor={'grey'}>예약 불가</Button>
			</LinkStyled>
			<Button backgroundColor={'grey'}>예약 불가</Button>
		</Main>
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

export default BooksDetailPage;
