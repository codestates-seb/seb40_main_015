import styled from 'styled-components';
import timeForToday from '../../utils/timeForToday';
import { useNavigate } from 'react-router';
import { useChatAPI } from '../../api/chat';

interface Item {
	avatarUrl: string;
	bookId: number;
	bookImageUrl: string;
	createdAt: string;
	customerId: number;
	merchantId: number;
	latestMessage: string;
	name: string;
	roomId: number;
}

interface IProps {
	data: Item[];
}

const ChatLists = ({ data }: IProps) => {
	const { axiosCreateRoom } = useChatAPI();
	const navigate = useNavigate();

	const handleMoveChatRoom = (
		merchantId: number,
		customerId: number,
		bookId: number,
	) => {
		axiosCreateRoom(merchantId, customerId, bookId).then(res => {
			navigate(`/chats/${res}`);
		});
	};

	return (
		<>
			{data.map((item: Item) => {
				const {
					avatarUrl,
					bookImageUrl,
					name,
					createdAt,
					latestMessage,
					roomId,
					merchantId,
					customerId,
					bookId,
				} = item;
				return (
					<Container
						onClick={() => {
							handleMoveChatRoom(merchantId, customerId, bookId);
						}}
						key={roomId}>
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
				);
			})}
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
		font-size: 1.1rem;
		word-break: break-word;
		max-width: 30rem;
		overflow: hidden;
		text-overflow: ellipsis;
		display: -webkit-box;
		line-height: 1.3rem;
		max-height: 3rem;
		-webkit-line-clamp: 2;
		-webkit-box-orient: vertical;
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
	height: 4.5rem;
`;

export default ChatLists;
