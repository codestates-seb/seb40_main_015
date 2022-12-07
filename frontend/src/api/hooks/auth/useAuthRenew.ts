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
			enabled:
				loginMutationData.isLogin &&
				loginMutationData?.accessToken === 'Bearer ',
			retry: false,
			onSuccess: res => {
				const renew = setTimeout(() => {
					dispatch(login({ accessToken: 'Bearer ', isLogin: true }));
				}, 1000 * 60 * 29);

				if (!loginMutationData.id) {
					dispatch(logout());
					clearTimeout(renew);
					return;
				}
				const {
					headers: { authorization },
				} = res;
				dispatch(
					login({
						accessToken: authorization,
						isLogin: true,
					}),
				);
			},
			onError: err => {
				//리프레시 만료 에러일때만 로그아웃
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
