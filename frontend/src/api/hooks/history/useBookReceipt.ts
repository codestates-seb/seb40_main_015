import { useMutation, useQueryClient } from '@tanstack/react-query';
import { useHistoryAPI } from '../../history';
import { useAppDispatch } from '../../../redux/hooks';
import notify from '../../../utils/notify';

// 주민 전용
export const useBookReceipt = (rentalId: number) => {
	const { axiosBookReceipt } = useHistoryAPI();
	const queryClient = useQueryClient();
	const dispatch = useAppDispatch();
	const { mutate } = useMutation(() => axiosBookReceipt(rentalId), {
		onSuccess: () => {
			queryClient.invalidateQueries({ queryKey: ['rentBookList'] });
			notify(dispatch, '정상적으로 수령 완료 처리되었습니다.');
		},
	});

	return { mutate };
};
