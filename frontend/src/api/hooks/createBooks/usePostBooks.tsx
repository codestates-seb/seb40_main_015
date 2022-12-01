import { useMutation } from '@tanstack/react-query';
import { axiosInstance } from '../..';

export interface PayloadType {
	title: string;
	author: string;
	publisher: string;
	rentalFee: number;
	description: string;
	imageUrl: string;
}

const usePostBooks = () => {
	const persistStorage = window.localStorage.getItem('persist:login');
	const accessToken: string =
		persistStorage && JSON.parse(persistStorage).accessToken.replace('"', '');

	return useMutation((payload: PayloadType) =>
		axiosInstance.post('/books', payload, {
			headers: {
				Authorization: accessToken,
			},
		}),
	);
};

export default usePostBooks;
