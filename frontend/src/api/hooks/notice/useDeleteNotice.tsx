import { useMutation } from '@tanstack/react-query';
import { axiosInstance } from '../..';
import { useAppSelector } from '../../../redux/hooks';

const useDeleteNotice = () => {
	const { accessToken } = useAppSelector(state => state.loginInfo);
	return useMutation((alarmId: number) =>
		axiosInstance.delete(`/alarm/${alarmId}`, {
			headers: {
				Authorization: accessToken,
			},
		}),
	);
};

export default useDeleteNotice;
