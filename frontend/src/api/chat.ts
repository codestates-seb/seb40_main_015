import useAPI from '../hooks/useAPI';

export const useChatAPI = () => {
	const api = useAPI();

	const getRoomAllMessage = (roomId: string | number) =>
		api.get(`/chats/message/${roomId}`).then(res => res.data);

	const getAllRoomLists = () => api.get(`/rooms`).then(res => res.data);

	const axiosCreateRoom = (
		merchantId: string | number,
		customerId: string | number,
		bookId: string | number,
	) =>
		api
			.get(`/room`, {
				params: {
					merchantId,
					customerId,
					bookId,
				},
			})
			.then(res => res.data);

	return {
		getRoomAllMessage,
		getAllRoomLists,
		axiosCreateRoom,
	};
};
