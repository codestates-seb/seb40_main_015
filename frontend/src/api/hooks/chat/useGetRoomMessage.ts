import { useQuery } from '@tanstack/react-query';
import { useState } from 'react';
import { useParams } from 'react-router';
import { useAppSelector } from '../../../redux/hooks';
import { useChatAPI } from '../../chat';

interface Member {
	avatarUrl: string;
	memberId: number;
	nickName: string;
}

const useGetRoomMessage = () => {
	const { getRoomAllMessage } = useChatAPI();
	const { id } = useAppSelector(state => state.loginInfo);
	const { roomId } = useParams(); // 채널을 구분하는 식별자 URL
	const [chatList, setChatList] = useState<any>([]); // 화면에 표시될 채팅 기록
	const [messageList, setMessageList] = useState<any[]>([]);
	const [myInfo, setMyInfo] = useState<Member>();
	const [receiverInfo, setReceiverInfo] = useState<Member>();

	const { refetch, isLoading, isFetching } = useQuery({
		queryKey: ['message', roomId],
		queryFn: () => {
			return getRoomAllMessage(roomId!);
		},
		onSuccess: data => {
			setChatList(data);
			setMessageList(data.chatResponses);
			data.members.forEach((member: any) => {
				if (member.memberId === id) {
					setMyInfo(member);
				} else {
					setReceiverInfo(member);
				}
			});
		},
		refetchOnWindowFocus: false,
	});

	return {
		chatList,
		refetch,
		messageList,
		setMessageList,
		myInfo,
		receiverInfo,
		isLoading,
		isFetching,
	};
};

export default useGetRoomMessage;
