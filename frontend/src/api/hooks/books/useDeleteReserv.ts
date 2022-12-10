import { useMutation } from '@tanstack/react-query';
import { useDispatch } from 'react-redux';
import { useMypageAPI } from '../../mypage';
import notify from '../../../utils/notify';

export const useDeleteReserv = (cancelId: number) => {
	const { deleteReservation } = useMypageAPI();
	const dispatch = useDispatch();

	const { mutate: deleteMutate } = useMutation({
		mutationFn: () => deleteReservation(cancelId),
		onSuccess: () => {
			notify(dispatch, '예약이 취소되었습니다.');
		},
		retry: false,
	});

	return { deleteMutate };
};
