import { useMutation } from '@tanstack/react-query';
import { axiosInstance } from '../..';
import { useAppDispatch } from '../../../redux/hooks';
import notify from '../../../utils/notify';

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
	const dispatch = useAppDispatch();

	return useMutation((payload: PayloadType) =>
		axiosInstance
			.post('/books', payload, {
				headers: {
					Authorization: accessToken,
				},
			})
			.then(res => {
				console.log(res);
				notify(dispatch, '게시글이 작성되었습니다.');
			})
			.catch(() => notify(dispatch, '게시글 작성에 실패했습니다.')),
	);
};

export default usePostBooks;
