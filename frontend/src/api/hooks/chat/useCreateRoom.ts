import { useQuery } from '@tanstack/react-query';
import { useChatAPI } from '../../chat';

interface Iprops {
	merchantId: string | number;
	customerId: string | number;
	bookId: string | number;
}

const useCreateRoom = ({ merchantId, customerId, bookId }: Iprops) => {
	const { axiosCreateRoom } = useChatAPI();

	const { data: roomData } = useQuery({
		queryKey: ['room'],
		queryFn: () => {
			return axiosCreateRoom(merchantId, customerId, bookId);
		},
		onSuccess: data => {
			console.log(data);
		},
	});

	return { roomData };
};

export default useCreateRoom;
