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

// getBookDetail
interface IBook {
	book: {
		bookId: number;
		title: string;
		publisher: string;
		author: string;
		status: string;
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
	// const getAllBooksList = (): Promise<IAllBooks[]> =>
	// 	axiosInstance.get('/books');
	const api = useAPI();
	const getAllBooksList = async () =>
		await axiosInstance.get<IAllBooks>('/books').then(res => res.data);

	const getBookDetail = async (id: string | undefined) =>
		await axiosInstance.get<IBook>(`/books/${id}`).then(res => res.data);

	return { getAllBooksList, getBookDetail };
};
