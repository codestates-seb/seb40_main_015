import { useQuery } from '@tanstack/react-query';
import { useBooksAPI } from '../../books';

export const useGetCheckBooking = (bookId: number | string | undefined) => {
	const { getCheckBookBooking } = useBooksAPI();
	const { data: checkBooking } = useQuery({
		refetchOnWindowFocus: false,
		refetchOnMount: false,
		retry: 0,
		cacheTime: 0,
		queryKey: ['bookCheckBooking'],
		queryFn: () => getCheckBookBooking(bookId).then(res => res.data),
	});
	return { checkBooking };
};
