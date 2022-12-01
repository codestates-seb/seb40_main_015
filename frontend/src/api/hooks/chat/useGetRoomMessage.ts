import { useQuery } from '@tanstack/react-query';
import { useState } from 'react';
import { useChatAPI } from '../../chat';

const useGetRoomMessage = (roomId: number | string) => {
	const { getRoomAllMessage } = useChatAPI();
	const [chatList, setChatList] = useState<any>([]); // 화면에 표시될 채팅 기록
	const { data: messageData } = useQuery({
		queryKey: ['message'],
		queryFn: () => {
			return getRoomAllMessage(roomId);
		},
		onSuccess: data => {
			setChatList(data);
		},
		refetchOnWindowFocus: false,
	});

	return { chatList, setChatList };
};

export default useGetRoomMessage;
