import { useQuery } from '@tanstack/react-query';
import { useChatAPI } from '../../chat';

const useGetRoomLists = () => {
	const { getAllRoomLists } = useChatAPI();

	const { data: roomListsData, isLoading: isLoadingRoomList } = useQuery({
		queryKey: ['roomLists'],
		queryFn: () => {
			return getAllRoomLists();
		},
		onSuccess: data => {
			// console.log(data);
		},
	});

	return { roomListsData, isLoadingRoomList };
};

export default useGetRoomLists;
