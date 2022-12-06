import { useMutation } from '@tanstack/react-query';
import { useDispatch } from 'react-redux';
import { useNavigate } from 'react-router-dom';

import { useBooksAPI } from '../../books';

import notify from '../../../utils/notify';

export const usePostBookBooking = (bookId: string | undefined) => {
	const { postBookBooking } = useBooksAPI();
	const navigate = useNavigate();
	const dispatch = useDispatch();

	const { mutate: mutateBookBooking } = useMutation({
		mutationFn: () => postBookBooking(bookId),
		onSuccess: res => {
			notify(dispatch, '예약 신청이 완료되었습니다.');
			navigate('/profile');
		},
		onError: res => {
			notify(dispatch, '이미 대여 신청한 도서 입니다.');
		},
	});

	return { mutateBookBooking };
};
