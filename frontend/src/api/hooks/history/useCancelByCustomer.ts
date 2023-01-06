import { useMutation, useQueryClient } from '@tanstack/react-query';
import { useHistoryAPI } from '../../history';
import { useAppDispatch } from '../../../redux/hooks';
import notify from '../../../utils/notify';

export const useCancelByCustomer = (rentalId: number) => {
	const { axiosCancleByCustomer } = useHistoryAPI();
	const queryClient = useQueryClient();
	const dispatch = useAppDispatch();
	const { mutate } = useMutation(() => axiosCancleByCustomer(rentalId), {
		onSuccess: () => {
			queryClient.invalidateQueries({ queryKey: ['rentBookList'] });
			notify(dispatch, '정상적으로 취소 처리되었습니다.');
		},
	});

	return { mutate };
};
