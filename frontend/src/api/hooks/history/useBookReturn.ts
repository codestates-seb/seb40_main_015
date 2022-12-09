import { useMutation, useQueryClient } from '@tanstack/react-query';
import { useHistoryAPI } from '../../../api/history';
import { useAppDispatch } from '../../../redux/hooks';
import notify from '../../../utils/notify';

//상인 전용
export const useBookReturn = (rentalId: number) => {
	const { axiosBookReturn } = useHistoryAPI();
	const queryClient = useQueryClient();
	const dispatch = useAppDispatch();
	const { mutate } = useMutation(() => axiosBookReturn(rentalId), {
		onSuccess: () => {
			queryClient.invalidateQueries({ queryKey: ['lendBookList'] });
			notify(dispatch, '정상적으로 반납 완료 처리되었습니다.');
		},
	});

	return { mutate };
};
