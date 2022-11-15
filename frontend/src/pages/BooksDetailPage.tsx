import styled from 'styled-components';
import Title from '../components/common/Title';
import BookImage from '../assets/image/dummy.png';
import UserImage from '../assets/image/user.png';
import Button from '../components/common/Button';
import { Link } from 'react-router-dom';
import { HiOutlineHeart, HiHeart, HiOutlineTrash } from 'react-icons/hi';

function BooksDetailPage() {
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
					<span>{''}</span>
					<label>위키북스</label>
				</BookInfo>

				<MerchantInfo>
					<div>
						<UserImg src={UserImage} />
						<span>자바북스</span>
					</div>
					<div>
						<span>평점</span>
						<span>★★★★★</span>
					</div>
				</MerchantInfo>
				<BookRentalFee>
					<label htmlFor="fee">대여료</label>
					<input id="fee" type="number" step="100" value={'1000'} />
					<span>원</span>
				</BookRentalFee>
				<BookDsc>
					<div>재미있어요</div>
					<div>재미있어요</div>
					<div>재미있어요</div>
					<div>재미있어요</div>
					<div>재미있어요</div>
					<div>재미있어요</div>
				</BookDsc>
			</BodyContainer>

			<LinkStyled to={`booking`}>
				<Button>책 대여하기</Button>
				<Button>책 예약하기</Button>
				<Button backgroundColor={'grey'}>예약 불가</Button>
			</LinkStyled>
		</Main>
	);
}
const Div = styled.fieldset`
	width: 40vh;
	border-radius: 4px;
	border: 1px solid rgba(1, 1, 1, 0.2);

	padding: 0.8rem;
	margin: 0.4rem 0;

	legend {
		padding: 0 0.4rem;
	}
`;
const Main = styled.div`
	display: flex;
	flex-direction: column;

	font-size: 20px;

	padding-bottom: 30px;
`;
const TitleWrapper = styled.div``;
const BodyContainer = styled.div`
	display: flex;
	flex-direction: column;
	justify-content: center;
	align-items: center;
`;
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
const BookInfo = styled(Div)`
	display: flex;
	align-items: center;

	label {
		font-size: 30px;
		margin-left: 1rem;
	}
	span {
		width: 2px;
		height: 20px;
		background-color: rgba(1, 1, 1, 0.3);
		margin-left: 1rem;
	}
`;

const BookRentalFee = styled(Div)`
	border: none;
	display: flex;
	justify-content: flex-end;
	align-items: center;

	input {
		margin-left: 10px;
		width: 4rem;
		height: 1.6rem;
		text-align: right;
		padding: 0 0.4rem;
		background-color: inherit;
		border: none;
		border-bottom: 1px solid rgba(1, 1, 1, 0.3);
		&:focus {
			border-bottom: 1px solid rgba(1, 1, 1, 0.7);
			outline: none;
		}
	}
	input::-webkit-inner-spin-button {
		-webkit-appearance: none;
		margin: 0;
	}
`;
const MerchantInfo = styled(Div)`
	font-size: 1.4rem;
	display: flex;

	justify-content: space-between;
	align-items: center;

	div {
		&:nth-child(1) {
			display: flex;
			justify-content: space-between;
			align-items: center;
			cursor: pointer;
		}

		&:nth-child(2) {
			font-size: 1.1rem;

			display: flex;
			justify-content: space-between;
			align-items: center;

			width: 30%;
			height: 2rem;
			border-radius: 4px;
			border: 1px solid rgba(1, 1, 1, 0.4);
			padding: 0 0.6rem;
		}
	}
`;

const UserImg = styled.img`
	border-radius: 0.3rem;
	width: 2.6rem;
	height: 2.6rem;

	margin-right: 10px;
`;
const BookDsc = styled(Div)`
	height: 20vh;
	margin-bottom: 1rem;
`;
const LinkStyled = styled(Link)`
	display: flex;
	flex-direction: column;
`;

export default BooksDetailPage;
