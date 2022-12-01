import styled from 'styled-components';
import { Link, useNavigate } from 'react-router-dom';

// types
import { BookDetailProps } from './type';

//components
import {
	BookDsc,
	BookInfo,
	MerchantInfo,
	Chat,
	BookContainer,
	BookTitle,
	BookSubTitle,
	Partition,
	BookRentalInfo,
	LinkStyled,
} from './BookElements';
import { useAppDispatch, useAppSelector } from '../../redux/hooks';
import notify from '../../utils/notify';
import { useChatAPI } from '../../api/chat';
import { HiHeart, HiOutlineHeart } from 'react-icons/hi';
import { useEffect, useState } from 'react';
import { useMutation, useQueryClient } from '@tanstack/react-query';
import { useBooksAPI } from '../../api/books';
import Button from '../common/Button';

const BookDetail = ({ book, merchant }: BookDetailProps) => {
	const { id, isLogin } = useAppSelector(state => state.loginInfo);
	const { axiosCreateRoom } = useChatAPI();
	const dispatch = useAppDispatch();
	const navigate = useNavigate();

	const { deleteBook, postWishItem } = useBooksAPI();

	const handleChatClick = () => {
		if (!isLogin) {
			notify(dispatch, '로그인 후 이용이 가능합니다.');
			navigate('/login');
			return;
		}
		if (merchant && book) {
			axiosCreateRoom(merchant?.merchantId, id, book?.bookId).then(res => {
				console.log(res);
				navigate(`/chats/${res}`);
			});
		}
	};

	// 삭제하기 delete 요청 쿼리
	const { mutate: mutateDelete } = useMutation({
		mutationFn: () => deleteBook(book?.bookId),
	});

	// event handler
	const HandleDelete = () => {
		if (book?.rentalStart) {
			notify(
				dispatch,
				'현재 대여 중 상태의 도서는 대여를 종료 할 수 없습니다.',
			);
			return;
		}

		const result = window.confirm('대여 종료하시겠습니까?');
		if (!result) return;
		mutateDelete();
		navigate('/books');
		notify(dispatch, '해당 도서의 대여 종료 처리가 완료되었습니다.');
	};

	// 찜하기 post요청 쿼리
	const queryClient = useQueryClient();
	const { mutate: mutateWish } = useMutation({
		mutationFn: () => postWishItem(book?.bookId),
		onSuccess: () => {
			queryClient.invalidateQueries(['bookDetail']);
		},
	});

	const HandleWishIcon = () => {
		book?.isDibs || notify(dispatch, '찜 목록에 추가되었습니다.');
		mutateWish();
	};

	return (
		<BookDetailContainer>
			<BookDetailTitleContainer>
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

				{id && id !== merchant?.merchantId ? (
					book?.isDibs ? (
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
			</BookDetailTitleContainer>

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
						{isLogin && merchant?.merchantId !== id ? (
							<ChatButton isLogin={isLogin} onClick={handleChatClick}>
								채팅
							</ChatButton>
						) : (
							<ChatButton isLogin={isLogin} onClick={handleChatClick}>
								채팅
							</ChatButton>
						)}
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

			<BtnWrapper>
				{id === merchant?.merchantId && book?.state !== '거래중단' ? (
					<Button onClick={HandleDelete}>대여 종료</Button>
				) : book?.state === '대여가능' ? (
					<LinkStyled to={isLogin ? `rental` : ''}>
						<Button
							onClick={() =>
								isLogin || notify(dispatch, '로그인이 필요합니다')
							}>
							책 대여하기
						</Button>
					</LinkStyled>
				) : book?.state === '대여중&예약가능' ? (
					<LinkStyled
						to={isLogin ? `booking` : ''}
						state={{
							rentalStart: book.rentalStart || '2023-05-01',
							rentalEnd: book.rentalEnd || '2023-05-11',
						}}>
						<Button
							onClick={() =>
								isLogin || notify(dispatch, '로그인이 필요합니다')
							}>
							책 예약하기
						</Button>
					</LinkStyled>
				) : (
					<Button backgroundColor={'grey'}>대여/예약 불가</Button>
				)}
			</BtnWrapper>
		</BookDetailContainer>
	);
};

const BookDetailContainer = styled.div`
	display: flex;
	flex-direction: column;
	justify-content: center;
	align-items: center;
`;
const BookDetailTitleContainer = styled(BookInfo)`
	position: relative;
	background-color: white;
`;
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

interface ChatButtonProps {
	isLogin: boolean;
}

const ChatButton = styled.button<ChatButtonProps>`
	background-color: ${props => (props.isLogin ? '#ffc700' : '#a7a7a7')};
	border: none;
	padding: 0.5rem 1rem;
	border-radius: 5px;
	cursor: pointer;
	:hover {
		background-color: ${props => (props.isLogin ? '#e8b601' : '#8c8c8c')};
	}
`;

const WishWrapper = styled.div`
	width: 10px;
	display: flex;
	justify-content: flex-end;
	align-items: center;

	position: absolute;
	right: -5px;
	bottom: 5px;
`;

const WishiconOn = styled(HiHeart)`
	font-size: 32px;
	color: ${props => props.theme.colors.logoGreen};
	margin-right: 16px;
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

	margin-right: 16px;
	cursor: pointer;
`;

const BtnWrapper = styled.div`
	margin: 10px 0;
	width: 400px;
	display: flex;
	justify-content: center;
	a {
		width: inherit;
		@media screen and (min-width: 801px) {
			margin-top: 1rem;
		}
	}
	button {
		height: 3rem;
		width: inherit;
	}
`;
export default BookDetail;
