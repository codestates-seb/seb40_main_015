import { useQuery } from '@tanstack/react-query';
import { useChatAPI } from '../../chat';

const useGetRoomLists = () => {
	const { getAllRoomLists } = useChatAPI();

	const { data: roomListsData } = useQuery({
		queryKey: ['roomLists'],
		queryFn: () => {
			return getAllRoomLists();
		},
		onSuccess: data => {
			// console.log(data);
		},
	});

	return { roomListsData };
};

export default useGetRoomLists;
