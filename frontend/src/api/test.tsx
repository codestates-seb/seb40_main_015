import axios, { AxiosResponse } from 'axios';
import { BASE_URL } from '../constants/constants';

const axiosConfig = {
	headers: { 'Content-Type': 'application/json; charset=UTF-8' },
	baseURL: BASE_URL,
};

const instance = axios.create(axiosConfig);
instance.defaults.withCredentials = true; // withCredentials 전역 설정

// 섹터당 상인의 총 갯수
//location 형식
// localhost:8080/member/count?latitude=37.4974939&longitude=127.0270229
export const getTotalMerchant = async (
	latitude: string | number,
	longitude: string | number,
) => {
	try {
		const result = await instance.get(`/member/count`, {
			params: {
				latitude,
				longitude,
			},
		});
		console.log(result);
		return result.data;
	} catch (err) {
		return err;
	}
};

// 섹터당 상인 목록
export const getMerchantList = async (
	latitude: string,
	longitude: string,
	sector: number,
) => {
	try {
		const result = await instance.get(`/member/sector`, {
			params: {
				latitude,
				longitude,
				sector,
			},
		});
		console.log(result.data);
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
