import styled from 'styled-components';
import { Link, useNavigate } from 'react-router-dom';

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
import { useAppDispatch, useAppSelector } from '../../redux/hooks';
import notify from '../../utils/notify';
import { useChatAPI } from '../../api/chat';

const BookDetail = ({ book, merchant }: BookDetailProps) => {
	const { id, isLogin } = useAppSelector(state => state.loginInfo);
	const { axiosCreateRoom } = useChatAPI();
	const dispatch = useAppDispatch();
	const navigate = useNavigate();

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

	console.log(book, merchant, id);
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

export default BookDetail;
