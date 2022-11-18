// 로그인 부분 API

import axios from 'axios';

export const getBooks = async () => {
	return axios.get('/auth/login');
};
