import { useMutation, useQueryClient } from '@tanstack/react-query';
import { useMypageAPI } from '../../../api/mypage';
import { useAppDispatch } from '../../../redux/hooks';
import notify from '../../../utils/notify';

//프로필 닉네임 수정
export const useInputUsername = () => {
	const { getMyInfo } = useMypageAPI();
	const dispatch = useAppDispatch();
	const queryClient = useQueryClient();
	// const { mutate } = useMutation(() => getMyInfo(id), {
	// 	onSuccess: () => {
	// 		queryClient.invalidateQueries({ queryKey: ['fixusername'] });
	// 		notify(dispatch, '닉네임 수정 완료');
	// 	},
	// });
	// return { mutate };
};
