import { useQuery } from '@tanstack/react-query';
import { useMypageAPI } from '../../mypage';

export const useGetAddress = (id: any) => {
	const { getMyInfo } = useMypageAPI();

	return useQuery({
		queryKey: ['myprofile'],
		queryFn: () => getMyInfo(id),
		retry: false,
	}).data?.address;
};
