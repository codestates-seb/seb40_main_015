import { QueryFunction } from '@tanstack/react-query';
import axios, { AxiosResponse } from 'axios';
import { BASE_URL } from '../constants/constants';

export interface bookData {
	title: string;
	authors: string[];
	publisher: string;
}

const api = axios.create({ baseURL: `${BASE_URL}` });

export const getBooksByTitle =
	(searchText: string): QueryFunction<AxiosResponse<bookData[]>> =>
	() =>
		api.get(`/books/bookInfo?bookTitle=${searchText}`);
