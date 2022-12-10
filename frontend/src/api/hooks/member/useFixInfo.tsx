import { useMutation, useQueryClient } from '@tanstack/react-query';
import { useMypageAPI } from '../../mypage';
import { useAppDispatch } from '../../../redux/hooks';
import notify from '../../../utils/notify';

interface FixmemberInfo {
	nickname: string;
	location: { latitude: string | number; longitude: string | number };
	address: string;
	avatarUrl: string;
}

//회원정보 수정
export const useFixInfo = (content: FixmemberInfo) => {
	const { patchFixMemberInfo } = useMypageAPI();
	const dispatch = useAppDispatch();
	const queryClient = useQueryClient();
	const { mutate } = useMutation(() => patchFixMemberInfo(content), {
		onSuccess: () => {
			queryClient.invalidateQueries({ queryKey: ['myprofile'] });
			notify(dispatch, '내 정보 수정 완료');
		},
	});
	return { mutate };
};
