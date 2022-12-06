import { useQuery } from '@tanstack/react-query';
import { useBooksAPI } from '../../books';

export const useGetBookDetail = (
	isLogin: boolean,
	bookId: string | undefined,
) => {
	const { getBookDetail } = useBooksAPI();
	const {
		data: bookDetailData,
		isLoading,
		refetch: refetchBookDetail,
	} = useQuery({
		queryKey: [bookId, 'bookDetail'],
		queryFn: () => getBookDetail(bookId, isLogin),
	});

	return { bookDetailData, isLoading, refetchBookDetail };
};
