import { axiosInstanceAuth } from '.';
import useAPI from '../hooks/useAPI';

//type
interface loginProps {
	userId: string;
	password: string;
}
interface IAccessToken {
	authorization: string;
}
interface userInfo {
	id: string;
	userId: string;
	nickname: string;
	headers?: IAccessToken;
}
interface IAccessTokenRefresh {
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
