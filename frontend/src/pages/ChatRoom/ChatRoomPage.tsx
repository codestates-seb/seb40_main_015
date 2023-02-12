import React, { KeyboardEvent, useEffect, useState } from 'react';
import styled from 'styled-components';
import useGetRoomMessage from 'api/hooks/chat/useGetRoomMessage';
import ScrollToBottom from 'utils/scrollToBottom';
import {
	ChatBookInfo as BookInfo,
	ScrollBottomButton,
	ChatInput as Input,
	Title,
	Animation,
	MessageList,
} from 'components';
import useClient from './hooks/useClient';
import { useNavigate } from 'react-router';

const ChatRoomPage = () => {
	const [text, setText] = useState('');
	const {
		chatList,
		messageList,
		setMessageList,
		myInfo,
		receiverInfo,
		isFetching,
	} = useGetRoomMessage();
	const { newMessage, connect, publish, disconnect } = useClient({
		receiverInfo,
	});
	const navigate = useNavigate();

	const { bookId, bookState, bookUrl, title } = chatList;

	const handleChangeText = (e: React.ChangeEvent<HTMLInputElement>) => {
		setText(e.target.value);
	};

	const handleSendMessage = (e: KeyboardEvent<HTMLInputElement>) => {
		if (e.nativeEvent.isComposing || !text.trim()) return;
		if (e.key === 'Enter' && text) {
			publish(text);
			setText('');
		}
	};

	const handleClickSendMessage = () => {
		if (!text.trim()) return;
		publish(text);
		setText('');
	};

	const handleClickMoveChatLists = (e: React.MouseEvent<HTMLButtonElement>) => {
		e.stopPropagation();
		navigate('/chats');
	};

	useEffect(() => {
		ScrollToBottom();
	}, [messageList]);

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
				{!isFetching && chatList ? (
					<BookInfo
						bookState={bookState}
						bookUrl={bookUrl}
						bookId={bookId}
						title={title}
						onClick={handleClickMoveChatLists}
					/>
				) : null}
			</TopBox>
			<MessageArea>
				{!isFetching ? (
					<MessageList messageList={messageList} />
				) : (
					<Animation />
				)}
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
	padding-bottom: 80px;
	display: flex;
	justify-content: flex-end;
	flex-direction: column;
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
