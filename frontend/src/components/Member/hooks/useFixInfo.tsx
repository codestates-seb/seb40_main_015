import { useMutation, useQueryClient } from '@tanstack/react-query';
import { useMypageAPI } from '../../../api/mypage';
import { useAppDispatch } from '../../../redux/hooks';
import notify from '../../../utils/notify';

//회원정보 수정
export const useFixInfo = (content: any) => {
	const { axiosFixMemberInfo } = useMypageAPI();
	const dispatch = useAppDispatch();
	const queryClient = useQueryClient();
	const { mutate } = useMutation(data => axiosFixMemberInfo(content), {
		onSuccess: () => {
			queryClient.invalidateQueries({ queryKey: ['fixprofile'] });
			notify(dispatch, '내 정보 수정 완료');
		},
	});
	return { mutate };
};
