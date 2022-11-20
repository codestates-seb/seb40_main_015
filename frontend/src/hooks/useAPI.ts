import axios from 'axios';
import { BASE_URL } from '../constants/constants';
import { useAppSelector } from '../redux/hooks';

// axios({
// 	method: 'post',
// 	url: BASE_URL + '/auth/login',
// 	withCredentials: true,
// 	data: payload,
// 	timeout: 5000,
// 	headers: { ContentType: 'application/json' },
// })
// 	.then(data => console.log('res: ', data))
// 	.then(err => console.error(err));

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
