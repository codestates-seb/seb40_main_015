import { useMutation, useQueryClient } from '@tanstack/react-query';
import { useHistoryAPI } from '../../../api/history';
import { useAppDispatch } from '../../../redux/hooks';
import notify from '../../../utils/notify';

//상인 전용
export const useBookReturn = (rentalId: string) => {
	const { axiosBookReturn } = useHistoryAPI();
	const queryClient = useQueryClient();
	const dispatch = useAppDispatch();
	const { mutate } = useMutation(() => axiosBookReturn(rentalId), {
		onSuccess: () => {
			queryClient.invalidateQueries({ queryKey: ['lendBookList'] });
			notify(dispatch, '반납완료');
		},
	});

	return { mutate };
};
