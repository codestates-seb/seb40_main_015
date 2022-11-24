import { useMutation, useQueryClient } from '@tanstack/react-query';
import { useMypageAPI } from '../../../api/mypage';
import { useAppDispatch } from '../../../redux/hooks';
import notify from '../../../utils/notify';

//프로필 이미지 등록
export const useInputImage = (data: any) => {
	const { axiosAddPhoto } = useMypageAPI();
	const dispatch = useAppDispatch();
	const queryClient = useQueryClient();
	const { mutate } = useMutation(data => axiosAddPhoto(data), {
		onSuccess: () => {
			queryClient.invalidateQueries({ queryKey: ['myprofile'] });
			notify(dispatch, '이미지 등록 완료');
		},
	});
	return { mutate };
};
