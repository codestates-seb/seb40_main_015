import styled from 'styled-components';
import timeForToday from '../../utils/timeForToday';
import { useNavigate } from 'react-router';
import { useChatAPI } from '../../api/chat';
import { useAppSelector } from '../../redux/hooks';

interface IProps {
	createdAt: string;
	avatarUrl: string;
	bookImageUrl: string;
	latestMessage: string;
	name: string;
	customerId: number;
	merchantId: number;
	roomId: number;
	bookId: number;
}

const ChatLists = ({ list }: { list: IProps }) => {
	const { id } = useAppSelector(state => state.loginInfo);
	const { axiosCreateRoom } = useChatAPI();
	const {
		avatarUrl,
		bookImageUrl,
		name,
		createdAt,
		latestMessage,
		roomId,
		customerId,
		merchantId,
		bookId,
	} = list;

	const navigate = useNavigate();
	const handleMoveChatRoom = () => {
		axiosCreateRoom(merchantId, id, bookId).then(res => {
			navigate(`/chats/${res}`);
		});
	};
	return (
		<>
			<Container onClick={handleMoveChatRoom}>
				<LeftBox>
					<UserImage src={avatarUrl} alt="상대 이미지" />
					<LeftContent>
						<LeftDetail>
							<span>{name}</span>
							<span>{timeForToday(createdAt)}</span>
						</LeftDetail>
						<p>{latestMessage}</p>
					</LeftContent>
				</LeftBox>
				<BookImage src={bookImageUrl} alt="책 이미지" />
			</Container>
		</>
	);
};

const Container = styled.div`
	display: flex;
	justify-content: space-between;
	align-items: center;
	padding: 1.5rem 2rem;
	border-bottom: 1px solid #c7c6c6;
	cursor: pointer;
	:hover {
		background-color: ${props => props.theme.colors.grey};
	}
`;

const LeftBox = styled.div`
	display: flex;
	align-items: center;
`;

const UserImage = styled.img`
	width: 4.5rem;
	height: 4.5rem;
	border-radius: 50%;
`;

const LeftContent = styled.div`
	display: flex;
	flex-direction: column;
	margin-left: 1rem;
	p {
		font-weight: bold;
		font-size: 1.3rem;
	}
`;

const LeftDetail = styled.div`
	display: flex;
	align-items: center;
	margin-bottom: 1rem;

	span {
		&:first-child {
			font-weight: bold;
			font-size: 1.2rem;
		}
		margin-right: 0.5rem;
	}
`;

const BookImage = styled.img`
	border-radius: 5px;
	width: 4rem;
	height: 4rem;
`;

export default ChatLists;
