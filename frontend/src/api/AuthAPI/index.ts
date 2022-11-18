import { AxiosResponse } from 'axios';
import { useQuery } from 'react-query';
import { getBooks } from './api';

export const useGetBooks = () =>
	useQuery<AxiosResponse<string>>('booksPage', getBooks);
