import styled from 'styled-components';
import BookInfo from '../components/Chat/BookInfo';
import ReceptionMessage from '../components/Chat/ReceptionMessage';
import SendingMessage from '../components/Chat/SendingMessage';
import Title from '../components/common/Title';
import React, { KeyboardEvent, useEffect, useRef, useState } from 'react';
import Input from '../components/Chat/Input';
import * as StompJs from '@stomp/stompjs';
import useGetRoomMessage from '../api/hooks/chat/useGetRoomMessage';
import { useAppSelector } from '../redux/hooks';
import { useParams } from 'react-router';

interface Member {
	avatarUrl: string;
	memberId: number;
	nickName: string;
}

const ChatRoomPage = () => {
	const [text, setText] = useState('');
	const { nickname, id } = useAppSelector(state => state.loginInfo);
	const { roomId } = useParams(); // 채널을 구분하는 식별자를 URL 파라미터로 받는다.
	const { chatList, setChatList } = useGetRoomMessage(roomId!);
	const { bookId, bookState, bookUrl, title, chatResponses, members } =
		chatList;
	const client: any = useRef({});
	let prevNickname = { nickName: '' };
	let prevDate = '';
	const [myInfo, setMyInfo] = useState<Member>();
	const [receiverInfo, setReceiverInfo] = useState<Member>();
	console.log(chatList);

	const handleChangeText = (e: React.ChangeEvent<HTMLInputElement>) => {
		setText(e.target.value);
	};

	const handleSendMessage = (e: KeyboardEvent<HTMLInputElement>) => {
		if (e.nativeEvent.isComposing) return;
		if (e.key === 'Enter') {
			publish(text);
		}
	};

	useEffect(() => {
		members?.map((member: Member) => {
			if (member.memberId === id) {
				return setMyInfo(member);
			} else {
				return setReceiverInfo(member);
			}
		});
	}, [members]);

	const connect = () => {
		client.current = new StompJs.Client({
			brokerURL: `${process.env.REACT_APP_WS_HOST}/stomp/chat/websocket`,
			connectHeaders: {
				login: 'user',
				passcode: 'password',
			},
			reconnectDelay: 2000,
			heartbeatIncoming: 4000,
			heartbeatOutgoing: 4000,
			onConnect: () => {
				subscribe();
			},
			onStompError: frame => {
				console.error(frame);
			},
		});
		client.current.activate();
	};

	const publish = (text: string) => {
		if (!client.current.connected) return;
		client.current.publish({
			destination: `/pub/chats/message/${roomId}`,
			body: JSON.stringify({
				senderId: id,
				receiverId: receiverInfo?.memberId,
				content: text,
			}),
		});
		setText('');
	};

	const subscribe = () => {
		client.current.subscribe(`/sub/room/${roomId}`, (body: any) => {
			// const json_body = JSON.parse(body.body);
			console.log('답장옴');
			console.log(body);
			// console.log(json_body);
			// setChatList((_chat_list: any) => [..._chat_list, json_body]);
		});
	};

	const disconnect = () => {
		console.log('연결 끊김');
		client.current.deactivate();
	};

	useEffect(() => {
		connect();

		return () => disconnect();
	}, []);

	return (
		<Container>
			<TopBox>
				<Title text="대화내역" marginBottom={false} />
				<BookInfo
					bookState={bookState}
					bookUrl={bookUrl}
					title={title}
					disconnect={disconnect}
				/>
			</TopBox>
			<MessageArea>
				{/* {chatResponses
					? chatResponses.map((list: any) => {
							const currentDate = new Date(list.dateTime).toLocaleDateString();
							if (currentDate !== prevDate) {
								prevDate = currentDate;
								return <DateDisplay>{prevDate}</DateDisplay>;
							}
					  })
					: null} */}
				{chatResponses ? (
					<>
						{chatResponses?.map((list: any, i: number) => {
							const { dateTime, content } = list;
							const newList = { content, dateTime };
							// const currentDate = new Date(dateTime).toLocaleDateString();
							// if (currentDate !== prevDate) {
							// 	prevDate = currentDate;
							// 	return <DateDisplay>{prevDate}</DateDisplay>;
							// }
							if (list?.nickName === nickname) {
								if (prevNickname === list.nickName) {
									return <SendingMessage key={dateTime} list={newList} />;
								} else {
									prevNickname = list.nickName;
									return <SendingMessage key={dateTime} list={list} />;
								}
							} else {
								if (prevNickname === list.nickName) {
									return <ReceptionMessage key={dateTime} list={newList} />;
								} else {
									prevNickname = list.nickName;
									return <ReceptionMessage key={dateTime} list={list} />;
								}
							}
						})}
					</>
				) : (
					<Empty>
						<p>대화를 시작해보세요</p>
					</Empty>
				)}
			</MessageArea>
			<Input
				text={text}
				onChange={handleChangeText}
				onKeyDown={handleSendMessage}
			/>
		</Container>
	);
};

const Container = styled.div``;

const TopBox = styled.div`
	position: sticky;
	top: 0;
	background-color: white;
`;

const MessageArea = styled.div`
	padding: 1rem;
	margin-bottom: 80px;
`;

const DateDisplay = styled.p`
	text-align: center;
	font-size: 1.5rem;
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
		color: #a7a7a7;
	}
`;

export default ChatRoomPage;
