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
// 현재까지는 로그인 -> 갱신요청 1회 -> 29분뒤 리패치  방식으로 진행됨.
// 로그인 이후 29분뒤 갱신요청을 보내는 방법 강구 -로그인 요청과 합치기?
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
