import { useMutation, useQueryClient } from '@tanstack/react-query';
import { useHistoryAPI } from '../../../api/history';
import { useAppDispatch } from '../../../redux/hooks';
import notify from '../../../utils/notify';

export const useCancelByCustomer = (rentalId: string) => {
	const { axiosCancleByCustomer } = useHistoryAPI();
	const queryClient = useQueryClient();
	const dispatch = useAppDispatch();
	const { mutate } = useMutation(() => axiosCancleByCustomer(rentalId), {
		onSuccess: () => {
			queryClient.invalidateQueries({ queryKey: ['rentBookList'] });
			// notify(dispatch, '취소완료');
		},
	});

	return { mutate };
};
