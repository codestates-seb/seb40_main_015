import { useEffect } from 'react';
import styled from 'styled-components';
import useCreateRoom from '../api/hooks/chat/useCreateRoom';
import useGetRoomLists from '../api/hooks/chat/useGetRoomLists';
import ChatLists from '../components/Chat/ChatLists';
import Title from '../components/common/Title';
import Animation from '../components/Loading/Animation';

interface RoomList {
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

const ChatsPage = () => {
	const { roomListsData, isLoadingRoomList } = useGetRoomLists();
	console.log(roomListsData);
	return (
		<Container>
			<Title text="채팅창 목록" marginBottom={false} />
			<Box>
				{isLoadingRoomList ? (
					<Animation />
				) : roomListsData ? (
					roomListsData.map((list: RoomList) => {
						return <ChatLists list={list} key={list.roomId} />;
					})
				) : (
					<Empty>
						<p>대화중인 방이 없어요</p>
					</Empty>
				)}
			</Box>
		</Container>
	);
};

const Container = styled.div`
	width: 100vw;
	display: flex;
	justify-content: center;
	flex-direction: column;
	align-items: center;
`;

const Box = styled.div`
	width: 100vw;
	max-width: 800px;
	height: 100%;
	overflow-y: auto;
	background-color: white;
	@media screen and (min-width: 800px) {
		border: 1px solid #eaeaea;
		border-radius: 5px;
	}
`;

const Empty = styled.div`
	width: 100%;
	height: 75vh;
	display: flex;
	justify-content: center;
	align-items: center;
	p {
		font-size: ${props => props.theme.fontSizes.subtitle};
		font-weight: 600;
	}
`;

export default ChatsPage;
