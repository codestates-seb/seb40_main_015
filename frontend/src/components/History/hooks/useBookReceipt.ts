import { useMutation, useQueryClient } from '@tanstack/react-query';
import { useHistoryAPI } from '../../../api/history';
import { useAppDispatch } from '../../../redux/hooks';
import notify from '../../../utils/notify';

// 상인 전용
export const useBookReceipt = (rentalId: string) => {
	const { axiosBookReceipt } = useHistoryAPI();
	const queryClient = useQueryClient();
	const dispatch = useAppDispatch();
	const { mutate } = useMutation(() => axiosBookReceipt(rentalId), {
		onSuccess: () => {
			queryClient.invalidateQueries({ queryKey: ['rentBookList'] });
			notify(dispatch, '수령완료');
		},
	});

	return { mutate };
};
