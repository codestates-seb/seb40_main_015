import { useMutation, useQueryClient } from '@tanstack/react-query';
import { useHistoryAPI } from '../../../api/history';
import { useAppDispatch } from '../../../redux/hooks';
import notify from '../../../utils/notify';

export const useCancelByMerchant = (rentalId: string) => {
	const { axiosCancleByMerchant } = useHistoryAPI();
	const queryClient = useQueryClient();
	const dispatch = useAppDispatch();
	const { mutate } = useMutation(() => axiosCancleByMerchant(rentalId), {
		onSuccess: () => {
			queryClient.invalidateQueries({ queryKey: ['lendBookList'] });
			notify(dispatch, '취소완료');
		},
	});

	return { mutate };
};
