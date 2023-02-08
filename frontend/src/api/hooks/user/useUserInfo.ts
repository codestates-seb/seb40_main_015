import { useQuery } from '@tanstack/react-query';
import { MemberInfo } from 'queryType/members';
import { useMypageAPI } from '../../mypage';

const useUserInfo = (merchantId: number | string) => {
	const { getMemberInfo } = useMypageAPI();
	const { data, isFetching } = useQuery<MemberInfo>(
		['merchant', merchantId],
		() => getMemberInfo(merchantId),
		{ retry: false, refetchOnWindowFocus: false },
	);

	return { data, isFetching };
};

export default useUserInfo;
