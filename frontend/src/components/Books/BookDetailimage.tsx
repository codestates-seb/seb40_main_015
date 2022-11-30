import styled from 'styled-components';
import { useState } from 'react';
import { HiHeart, HiOutlineHeart, HiOutlineTrash } from 'react-icons/hi';
import { useNavigate } from 'react-router-dom';
import { useMutation } from '@tanstack/react-query';
import { useAppDispatch, useAppSelector } from '../../redux/hooks';
import { useBooksAPI } from '../../api/books';

// types
import { BookDetailProps } from './type';
import notify from '../../utils/notify';

const BookImage = ({ book, merchant }: BookDetailProps) => {
	const { id } = useAppSelector(state => state.loginInfo);
	const dispatch = useAppDispatch();

	const [active, setActive] = useState(book?.isDibs);
	const { postWishItem } = useBooksAPI();

	// 찜하기 post요청 쿼리
	const {
		mutate: mutateWish,
		isLoading,
		isSuccess,
	} = useMutation({
		mutationFn: () => postWishItem(book?.bookId),
	});

	const HandleWishIcon = () => {
		setActive(!active);
		mutateWish();
		// active 보다 찜 정보를 이용해서 알림 기능 구현할 것
		active || notify(dispatch, '찜 목록에 추가되었습니다.');
	};
	return (
		<BookImgWrapper>
			<BookImg src={book?.bookImgUrl} alt="Book_image" />

			{book?.state !== '대여가능' ? (
				<BookNotAvailable>
					{book?.state !== '거래중단' ? (
						<>
							<span>이미 누가 대여중이에요 😭</span>
							<span>2022/1104~2022/11/18</span>
							<span
								className={
									book?.state !== '예약불가' ? 'possible' : 'impossible'
								}>
								{book?.state !== '예약불가' ? '예약가능' : '예약불가'}
							</span>
						</>
					) : (
						<span>{book?.state}</span>
					)}
				</BookNotAvailable>
			) : (
				''
			)}
			{/* 
			{id === merchant?.merchantId ? (
				<Deleticon onClick={HandleDeleteIcon} />
			) : (
				''
			)} */}

			{id && id !== merchant?.merchantId ? (
				active ? (
					<WishWrapper>
						<WishiconOn onClick={HandleWishIcon} />
					</WishWrapper>
				) : (
					<WishWrapper>
						<WishiconOff onClick={HandleWishIcon} />
					</WishWrapper>
				)
			) : (
				''
			)}
		</BookImgWrapper>
	);
};

const BookImgWrapper = styled.div`
	width: 40vh;
	display: flex;
	justify-content: center;
	align-items: center;
	position: relative;

	margin-bottom: 2rem;
`;
// const Deleticon = styled(HiOutlineTrash)`
// 	font-size: 30px;
// 	color: rgba(0, 0, 0, 0.7);
// 	position: absolute;
// 	top: 0;
// 	right: 1rem;
// 	cursor: pointer;
// `;
const WishWrapper = styled.div`
	/* background-color: pink; */
	width: 34px;
	height: 34px;
	border-radius: 50%;

	display: flex;
	justify-content: center;
	align-items: center;

	position: absolute;
	right: -1vw;
	bottom: -90px;
`;

const WishiconOn = styled(HiHeart)`
	font-size: 32px;
	color: ${props => props.theme.colors.logoGreen};
	cursor: pointer;
	@keyframes wishBeat {
		50% {
			opacity: 1;
			transform: scale(1.2);
		}
		100% {
			transform: none;
		}
	}
	will-change: transform;
	animation: wishBeat 0.4s linear;
	animation-fill-mode: forwards;
`;

const WishiconOff = styled(HiOutlineHeart)`
	font-size: 30px;
	color: rgba(0, 0, 0, 0.4);
	cursor: pointer;
`;

const BookImg = styled.img`
	width: 18rem;
	height: 21rem;
`;

const BookNotAvailable = styled.div`
	width: 18rem;
	height: 21rem;
	background-color: rgba(1, 1, 1, 0.4);
	position: absolute;
	/* left: 0; */

	display: flex;
	flex-direction: column;
	justify-content: center;
	align-items: center;
	span {
		font-size: ${props => props.theme.fontSizes.subtitle};
		background-color: transparent;
		margin-bottom: 0.7rem;
		color: white;
		/* &:nth-child(2) {
			color: red;
		}
		&:last-child {
			color: white;
		} */
	}
	.possible {
		color: #38e54d;
		font-weight: bold;
	}
	.impossible {
		color: #ff6464;
		font-weight: bold;
	}
`;

export default BookImage;
