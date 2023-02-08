import { useRef, useState } from 'react';
import * as StompJs from '@stomp/stompjs';
import { FrameImpl } from '@stomp/stompjs';
import { useAppSelector } from 'redux/hooks';
import { useParams } from 'react-router';

interface NewMessage {
	createdAt: string;
	message: string;
	roomId: number;
	senderId: number;
}

interface Member {
	avatarUrl: string;
	memberId: number;
	nickName: string;
}

interface IProps {
	receiverInfo?: Member;
}

const useClient = (props: IProps) => {
	const { receiverInfo } = props;
	const [newMessage, setNewMessage] = useState<NewMessage>();
	const { id } = useAppSelector(state => state.loginInfo);
	const { roomId } = useParams(); // 채널을 구분하는 식별자를 URL 파라미터로 받는다.
	const client: any = useRef({});

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

	return { newMessage, connect, publish, disconnect };
};

export default useClient;
