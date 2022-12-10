import React, { KeyboardEvent, useEffect, useRef, useState } from 'react';
import styled from 'styled-components';
import BookInfo from '../components/Chat/BookInfo';
import Input from '../components/Chat/Input';
import Title from '../components/common/Title';
import useGetRoomMessage from '../api/hooks/chat/useGetRoomMessage';
import { useAppSelector } from '../redux/hooks';
import { useParams } from 'react-router';
import ScrollToBottom from '../utils/scrollToBottom';
import ScrollBottomButton from '../components/common/ScrollBottomButton';
import Animation from '../components/Loading/Animation';
import * as StompJs from '@stomp/stompjs';
import MessageList from '../components/Chat/MessageList';
import { FrameImpl } from '@stomp/stompjs';

interface NewMessage {
	createdAt: string;
	message: string;
	roomId: number;
	senderId: number;
}

const ChatRoomPage = () => {
	const [text, setText] = useState('');
	const { id } = useAppSelector(state => state.loginInfo);
	const { roomId } = useParams(); // 채널을 구분하는 식별자를 URL 파라미터로 받는다.
	const {
		chatList,
		messageList,
		setMessageList,
		myInfo,
		receiverInfo,
		isLoading,
	} = useGetRoomMessage(roomId!);
	const { bookId, bookState, bookUrl, title } = chatList;
	const [newMessage, setNewMessage] = useState<NewMessage>();
	const client: any = useRef({});

	const handleChangeText = (e: React.ChangeEvent<HTMLInputElement>) => {
		setText(e.target.value);
	};

	const handleSendMessage = (e: KeyboardEvent<HTMLInputElement>) => {
		if (e.nativeEvent.isComposing || !text.trim()) return;
		if (e.key === 'Enter' && text) {
			publish(text);
		}
	};

	const handleClickSendMessage = () => {
		if (!text.trim()) return;
		publish(text);
	};

	const handleClickEndOfChat = () => {
		let isEnd = window.confirm('정말 종료하시겠어요?');
		if (isEnd) {
			disconnect();
		}
	};

	useEffect(() => {
		ScrollToBottom();
	}, [messageList]);

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
		client.current.subscribe(`/sub/room/${roomId}`, (body: FrameImpl) => {
			const json_body = JSON.parse(body.body);
			setNewMessage(json_body);
		});
	};

	const disconnect = () => {
		client.current.deactivate();
	};

	useEffect(() => {
		const data = {
			content: newMessage?.message,
			dateTime: newMessage?.createdAt,
		};
		if (newMessage?.senderId === myInfo?.memberId) {
			setMessageList([...messageList, { ...myInfo, ...data }]);
		} else {
			setMessageList([...messageList, { ...receiverInfo, ...data }]);
		}
	}, [newMessage]);

	useEffect(() => {
		connect();

		return () => disconnect();
	}, []);

	return (
		<Container>
			<TopBox>
				<Title text="대화내역" marginBottom={false} />
				{!isLoading && chatList ? (
					<BookInfo
						bookState={bookState}
						bookUrl={bookUrl}
						bookId={bookId}
						title={title}
						onClick={handleClickEndOfChat}
					/>
				) : null}
			</TopBox>
			<MessageArea>
				{!isLoading ? <MessageList messageList={messageList} /> : <Animation />}
			</MessageArea>
			<InputWrapper>
				<Input
					text={text}
					onChange={handleChangeText}
					onKeyDown={handleSendMessage}
					onCick={handleClickSendMessage}
				/>
				<ScrollBottomButton />
			</InputWrapper>
		</Container>
	);
};

const Container = styled.div`
	display: flex;
	flex-direction: column;
	@media screen and (min-width: 800px) {
		width: 100%;
		justify-content: center;
		align-items: center;
	}
`;

const TopBox = styled.div`
	position: sticky;
	top: 0;
	background-color: white;
	@media screen and (min-width: 800px) {
		width: 800px;
		border: 1px solid #eaeaea;
		border-bottom: none;
		box-sizing: border-box;
	}
`;

const MessageArea = styled.div`
	padding: 1rem;
	box-sizing: border-box;
	margin-bottom: 80px;
	@media screen and (min-width: 800px) {
		width: 800px;
		border: 1px solid #eaeaea;
		border-top: none;
		background-color: white;
	}
`;

const InputWrapper = styled.div`
	position: fixed;
	width: 100%;
	bottom: 60px;
	display: flex;
	justify-content: center;
`;

export default ChatRoomPage;
