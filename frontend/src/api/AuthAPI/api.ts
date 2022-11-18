// 로그인 부분 API

import axios from 'axios';

interface loginFormShape {
	id: string;
	password: string;
}

// export const getAccessToken = async (payload: loginFormShape) => {
// 	try {
// 		const data = await axios.post('/auth/login', JSON.stringify(payload));
// 		console.log('login fetch data: ', data);
// 	} catch {}
// };
