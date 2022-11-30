import { axiosInstanceAuth } from '.';
import useAPI from '../hooks/useAPI';

//type
export interface loginProps {
	userId: string;
	password: string;
}
export interface IAccessToken {
	authorization: string;
}
export interface userInfo {
	id: string;
	userId: string;
	nickname: string;
	headers?: IAccessToken;
}
export interface IAccessTokenRefresh {
	headers: IAccessToken;
}

const useAuthAPI = () => {
	const api = useAPI();

	// login post api
	const postLogin = async (payload: loginProps) => {
		return await axiosInstanceAuth.post<userInfo>('/auth/login', payload);
	};

	// access token refresh
	const getAccessTokenRefresh = async () =>
		await api.get<IAccessTokenRefresh>('/reissue');

	return { getAccessTokenRefresh, postLogin };
};

export default useAuthAPI;
