import { useMutation, useQueryClient } from '@tanstack/react-query';
import { useMypageAPI } from '../../../api/mypage';
import useGeoLocation from '../../../hooks/useGeoLocation';
import { useAppDispatch } from '../../../redux/hooks';
import notify from '../../../utils/notify';

//프로필 내 동네 설정 수정
export const useInputPlace = () => {
	// const [current, setCurrent, handleCurrentLocationMove] = useGeoLocation();
	// const dispatch = useAppDispatch();
	// const queryClient = useQueryClient();
	// const { mutate: place } = useMutation(() => axiosAddPhoto(), {
	// 	onSuccess: () => {
	// 		queryClient.invalidateQueries({ queryKey: [''] });
	// 		notify(dispatch, '내 동네 설정 완료');
	// 	},
	// });
	// return { mutate: place };
};
