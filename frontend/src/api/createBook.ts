import { AxiosResponse } from 'axios';
import { axiosInstance } from './instance';

interface bookData {
	title: string;
	authors: string[];
	publisher: string;
}

export const getBooksByTitle = (
	searchText: string,
): Promise<AxiosResponse<bookData[]>> =>
	axiosInstance.get(`/books/bookInfo?bookTitle=${searchText}`);
