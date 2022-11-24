import styled from 'styled-components';
import { useState } from 'react';
import { HiHeart, HiOutlineHeart, HiOutlineTrash } from 'react-icons/hi';

// types
import { BookDetailProps } from './type';
import { useAppSelector } from '../../redux/hooks';
import { useNotifyHook } from '../../hooks/useNotify';
import { useMutation } from '@tanstack/react-query';
import { useBooksAPI } from '../../api/books';

const BookImage = ({ book, merchant }: BookDetailProps) => {
	const notify = useNotifyHook();
	const { id } = useAppSelector(state => state.loginInfo);
	const [active, setActive] = useState(false);
	const { postWishItem } = useBooksAPI();

	// 찜하기 post요청 쿼리
	const { mutate } = useMutation({
		mutationFn: () => postWishItem(book?.bookId),
		onSuccess: () => {
			console.log('wish req complete');
		},
	});
	const HandleDeleteIcon = () => {
		const result = window.confirm('정말로 삭제하시겠습니까?');
		console.log('IsDelete: ', result);
	};

	const HandleWishIcon = () => {
		setActive(!active);
		// active || mutate();
		active ||
			notify(
				'찜 목록에 추가되었습니다. 실제로 요청 가진 않아요. 취소 기능이랑 함께 구현할 예정',
			);
	};
	return (
		<BookImgWrapper>
			<BookImg src={''} alt="Book_image" />

			{book?.state !== '대여가능' ? (
				<BookNotAvailable>
					<span>이미 누가 대여중이에요 😭</span>
					<span>2022/1104~2022/11/18</span>
					<span>{book?.state}</span>
				</BookNotAvailable>
			) : (
				''
			)}

			{id === merchant?.merchantId ? (
				<Deleticon onClick={HandleDeleteIcon} />
			) : (
				''
			)}

			{id !== merchant?.merchantId ? (
				active ? (
					<WishiconOn onClick={HandleWishIcon} />
				) : (
					<WishiconOff onClick={HandleWishIcon} />
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
const Deleticon = styled(HiOutlineTrash)`
	font-size: 30px;
	color: rgba(0, 0, 0, 0.7);
	position: absolute;
	top: 0;
	right: 1rem;
	cursor: pointer;
`;

const WishiconOn = styled(HiHeart)`
	font-size: 32px;
	color: ${props => props.theme.colors.logoGreen};
	position: absolute;
	right: 1rem;
	bottom: 0;
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
	position: absolute;
	right: 1rem;
	bottom: 0;

	cursor: pointer;
`;
const BookImg = styled.img`
	width: 18rem;
	height: 20rem;
`;
const BookNotAvailable = styled.div`
	width: 18rem;
	height: 20rem;
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
		&:nth-child(2) {
			color: red;
		}
		&:last-child {
			color: white;
		}
	}
`;

export default BookImage;
