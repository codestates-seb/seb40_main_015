import axios, { AxiosResponse } from 'axios';
const BASE_URL = process.env.REACT_APP_HOST;

const axiosConfig = {
	headers: { 'Content-Type': 'application/json; charset=UTF-8' },
	baseURL: BASE_URL,
};

const instance = axios.create(axiosConfig);
instance.defaults.withCredentials = true; // withCredentials 전역 설정

// 섹터당 상인의 총 갯수
//location 형식
// localhost:8080/member/count?latitude=37.4974939&longitude=127.0270229
export const getTotalMerchant = async () => {
	try {
		const result = await instance.get(`/member/count`, {
			params: {
				latitude: '37.39252645331443',
				longitude: '126.93573269749179',
			},
		});
		console.log(result);
		return result.data;
	} catch (err) {
		return err;
	}
};

// 섹터당 상인 목록
export const getMerchantList = async () => {
	try {
		const result = await instance.get(`/member/sector`);
		return result.data;
	} catch (err) {
		return err;
	}
};

// // 회원정보 수정
// export const patchUserInfo = async (id: string) =>
// 	try {
// 		const result = await instance.patch(`/member/${id}/edit`);
// 		return result.data;
// 	} catch (err) {
// 		return err;
// 	};
