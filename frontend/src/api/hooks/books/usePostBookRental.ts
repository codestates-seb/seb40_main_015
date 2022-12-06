import { useMutation } from '@tanstack/react-query';
import { useDispatch } from 'react-redux';
import { useNavigate } from 'react-router-dom';

import { useBooksAPI } from '../../books';

import notify from '../../../utils/notify';

export const usePostBookRental = (bookId: string | undefined) => {
	const { postBookRental } = useBooksAPI();
	const navigate = useNavigate();
	const dispatch = useDispatch();

	const { mutate: mutateBookRental } = useMutation({
		mutationFn: () => postBookRental(bookId),
		onSuccess: res => {
			notify(dispatch, '대여 신청이 완료되었습니다.');
			navigate('/history');
		},
		onError: err => {
			console.log('rental error: ', err);
		},
	});

	return { mutateBookRental };
};
