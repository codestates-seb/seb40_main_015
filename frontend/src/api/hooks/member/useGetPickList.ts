import { useInfiniteQuery } from '@tanstack/react-query';
import { useMypageAPI } from '../../mypage';

export const useGetPickList = () => {
	const { getPickBookList } = useMypageAPI();

	const {
		data: pickBookData,
		fetchNextPage,
		isLoading,
		hasNextPage,
		isFetchingNextPage,
	} = useInfiniteQuery({
		queryKey: ['pickbooklist'],
		queryFn: ({ pageParam = undefined }) => getPickBookList(pageParam),
		getNextPageParam: lastPage => {
			return lastPage?.content?.slice(-1)[0]?.bookId;
		},
	});

	return {
		pickBookData,
		fetchNextPage,
		isLoading,
		hasNextPage,
		isFetchingNextPage,
	};
};
