import { useQuery } from '@tanstack/react-query';
import { AxiosResponse } from 'axios';
import { axiosInstance } from '..';

interface bookData {
	title: string;
	authors: string[];
	publisher: string;
}

const getBooksByTitle = (
	searchText: string,
): Promise<AxiosResponse<bookData[]>> =>
	axiosInstance.get(`/books/bookInfo?bookTitle=${searchText}`);

const useGetBooksByTitle = (searchText: string) =>
	useQuery(['books'], () => getBooksByTitle(searchText), {
		refetchOnWindowFocus: false,
		enabled: false,
	});

export default useGetBooksByTitle;
