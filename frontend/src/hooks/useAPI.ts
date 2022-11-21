import axios from 'axios';
import { BASE_URL } from '../constants/constants';
import { useAppSelector } from '../redux/hooks';

const useAPI = () => {
	const { accessToken } = useAppSelector(state => state.loginInfo);
	const config = {
		baseURL: BASE_URL,
		withCredentials: true,
		headers: { ContentType: 'application/json', Authorization: accessToken },
		timeout: 10000,
	};
	const axiosWithAccessToken = axios.create(config);

	return axiosWithAccessToken;
};

export default useAPI;
