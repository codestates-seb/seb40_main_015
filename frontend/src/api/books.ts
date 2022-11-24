import { axiosInstance } from '.';
import useAPI from '../hooks/useAPI';

// getAllBooksList
interface AllBooks {
	bookId: string;
	title: string;
	status: string;
	rentalfee: number;
	bookImage: string;
	merchantName: string;
	rentalFee: number;
}

interface IAllBooks {
	content: AllBooks[];
}
// interface IAllBooksInfinite {
// 	content: AllBooks[];
// 	pages: { content: AllBooks }[];
// }

// getBookDetail
interface IBook {
	book: {
		bookId: number;
		title: string;
		publisher: string;
		author: string;
		state: string;
		content: string;
		rentalfee: number;
		rentalStart: string;
		rentalEnd: string;
	};
	merchant: {
		merchantId: number;
		name: string;
		grade: number;
	};
}

export const useBooksAPI = () => {
	const api = useAPI();

	// books page query
	const getAllBooksList = async () =>
		await axiosInstance.get<IAllBooks>('/books').then(res => res.data);

	//books page infinite query
	const getAllBooksListInfinite = async (id?: number) => {
		return await axiosInstance
			.get<IAllBooks>(id ? `/books?index=${id}` : '/books')
			.then(res => res.data);
	};

	// book detail page
	const getBookDetail = async (id: string | undefined) =>
		await axiosInstance.get<IBook>(`/books/${id}`).then(res => res.data);

	// book detail page wish
	const postWishItem = async (bookid: number | undefined) =>
		await api.post(`/dibs/${bookid}`);
	return {
		getAllBooksList,
		getBookDetail,
		getAllBooksListInfinite,
		postWishItem,
	};
};
