import { useQuery } from '@tanstack/react-query';
import { useBooksAPI } from '../../books';

const initialState = {
	book: {
		bookId: -1,
		title: '',
		publisher: '',
		author: '',
		state: '대여가능',
		content: '',
		rentalfee: 0,
		rentalStart: '',
		rentalEnd: '',
		bookImgUrl: '',
		isDibs: false,
	},
	merchant: {
		merchantId: -1,
		name: '',
		grade: 0,
		avatarUrl: '',
		// avatarUrl:
		// 	'https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png',
	},
};

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
		initialData: initialState,
	});

	return { bookDetailData, isLoading, refetchBookDetail };
};
