import { useInfiniteQuery } from '@tanstack/react-query';
import { useMypageAPI } from '../../mypage';

export const useGetReservList = () => {
	const { getReservationBookList } = useMypageAPI();

	const {
		data: reservBookData,
		fetchNextPage,
		isLoading,
		hasNextPage,
		isFetchingNextPage,
	} = useInfiniteQuery({
		queryKey: ['reservationbooklist'],
		queryFn: ({ pageParam = undefined }) => getReservationBookList(pageParam),
		getNextPageParam: lastPage => {
			return lastPage.content?.slice(-1)[0]?.reservationInfo.reservationId;
		},
	});

	return {
		reservBookData,
		fetchNextPage,
		isLoading,
		hasNextPage,
		isFetchingNextPage,
	};
};
