import React from 'react';
import { useQuery } from '@tanstack/react-query';
import { useDispatch } from 'react-redux';
import { useNavigate } from 'react-router';
import { login, logout } from '../../../redux/slice/userSlice';
import notify from '../../../utils/notify';
import useAuthAPI, { userInfo } from '../../auth';

interface IAccessTokenRefreshProps extends userInfo {
	accessToken: string;
	isLogin: boolean;
}

// token renew
// 로그인 -> 29분뒤 리덕스 액세스토큰 무효화 -> 갱신요청 -> 29분뒤 리패치 (로그아웃 혹은 리프레시토큰 만료때까지 반복)
const useGetAccessTokenRefresh = (
	loginMutationData: IAccessTokenRefreshProps,
) => {
	const dispatch = useDispatch();
	const navigate = useNavigate();
	const { getAccessTokenRefresh } = useAuthAPI();

	const { isLoading: renewQueryIsLoading, isFetching: isFetchingRenewQuery } =
		useQuery({
			queryKey: ['renew', 'loginInfo'],
			queryFn: getAccessTokenRefresh,
			enabled: loginMutationData.isLogin && !loginMutationData?.id,
			// staleTime: 1000 * 60 * 28,
			staleTime: 1000 * 10,
			onSuccess: res => {
				console.log('token renew complete');
				const {
					headers: { authorization },
				} = res;
				console.log('auth: ', authorization);
				dispatch(
					login({
						...loginMutationData,
						accessToken: authorization,
					}),
				);
				// setTimeout(() => {
				// 	// queryClient.invalidateQueries(['loginInfo']);
				// 	// setIsTokenStaled(true);
				// }, 1000 * 60 * 29);
			},
			onError: err => {
				//리프레시 만료 에러일때만 로그아웃
				console.error('token renew error: ', err);
				notify(
					dispatch,
					'로그인 시간이 만료되었습니다. 다시 로그인 해주시기 바랍니다.',
				);
				dispatch(logout());
				navigate('/login');
			},
		});
	return { renewQueryIsLoading, isFetchingRenewQuery };
};

export default useGetAccessTokenRefresh;
