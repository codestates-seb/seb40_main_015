import axios, { AxiosResponse } from 'axios';
import useAPI from '../hooks/useAPI';

const BASE_URL = process.env.REACT_APP_HOST;

const axiosConfig = {
	headers: { 'Content-Type': 'application/json; charset=UTF-8' },
	baseURL: BASE_URL,
};

const instance = axios.create(axiosConfig);
instance.defaults.withCredentials = true; // withCredentials 전역 설정

// 대여내역 - 빌린 책
export const getRentalBookLists = async () => {
	try {
		const result = await instance.get(`/rental/from`);
		console.log(result);
		return result.data;
	} catch (err) {
		return err;
	}
};

// 대여내역 - 빌려준 책
export const getLendBookLists = async () => {
	try {
		const result = await instance.get(`/rental/to`);
		console.log(result);
		return result.data;
	} catch (err) {
		return err;
	}
};

// 대여 취소 by 상인
export const axiosCancleByMerchant = async (id: string) => {
	try {
		const result = await instance.patch(`/rental/${id}/cancelByMerchant`);
		console.log(result);
		return result.data;
	} catch (err) {
		return err;
	}
};

// 대여 취소 by 주민
export const axiosCancleByCustomer = async (id: string) => {
	try {
		const result = await instance.patch(`/rental/${id}/cancelByCustomer`);
		console.log(result);
		return result.data;
	} catch (err) {
		return err;
	}
};

// 도서 수령
export const axiosBookReceipt = async (id: string) => {
	try {
		const result = await instance.get(`/rental/${id}/receive`);
		console.log(result);
		return result.data;
	} catch (err) {
		return err;
	}
};

// 도서 반납
export const axiosBookReturn = async (id: string) => {
	try {
		const result = await instance.get(`/rental/${id}/return`);
		console.log(result);
		return result.data;
	} catch (err) {
		return err;
	}
};

// 예시)
const useHistoryAPI = () => {
	const api = useAPI();

	// 대여내역 - 빌린 책
	const getRentalBookLists = async () => {
		try {
			const result = await api.get(`/rental/from`);
			return result.data;
		} catch (err) {
			return err;
		}
	};

	// 대여 취소 by 상인
	const axiosCancleByMerchant = async (id: string) => {
		try {
			const result = await api.patch(`/rental/${id}/cancelByMerchant`);
			return result.data;
		} catch (err) {
			return err;
		}
	};

	return { getRentalBookLists, axiosCancleByMerchant };
};
