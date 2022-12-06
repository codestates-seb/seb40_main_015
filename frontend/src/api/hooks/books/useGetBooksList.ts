import { useInfiniteQuery } from '@tanstack/react-query';
import { useBooksAPI } from '../../books';

export const useGetBooksList = () => {
	const { getAllBooksListInfinite } = useBooksAPI();

	const {
		data: booksListData,
		fetchNextPage,
		isLoading,
		hasNextPage,
		isFetchingNextPage,
	} = useInfiniteQuery({
		queryKey: ['allBooks'],
		queryFn: ({ pageParam = undefined }) => getAllBooksListInfinite(pageParam),
		getNextPageParam: lastPage => {
			return lastPage?.content?.slice(-1)[0]?.bookId;
		},
	});

	return {
		booksListData,
		fetchNextPage,
		isLoading,
		hasNextPage,
		isFetchingNextPage,
	};
};
