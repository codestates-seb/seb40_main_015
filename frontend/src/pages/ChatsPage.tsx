import styled from 'styled-components';
import useGetRoomLists from 'api/hooks/chat/useGetRoomLists';
import { Title, ChatLists, Animation } from 'components';

const ChatsPage = () => {
	const { roomListsData, isLoadingRoomList } = useGetRoomLists();

	return (
		<Container>
			<Title text="채팅창 목록" marginBottom={false} />
			<Box>
				{isLoadingRoomList ? (
					<Animation />
				) : roomListsData?.length ? (
					<ChatLists data={roomListsData} />
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
