import { axiosInstance } from '.';

// 섹터당 상인의 총 갯수 // useQuery
export const getTotalMerchantQuery = (
	latitude: number,
	longitude: number,
	width: number,
	height: number,
	level: number,
) => {
	return axiosInstance
		.get(`/member/count`, {
			params: {
				latitude,
				longitude,
				width,
				height,
				level,
			},
		})
		.then(res => res.data);
};

// 섹터당 상인 목록 // useQuery
export const getMerchantListQuery = (
	latitude: number,
	longitude: number,
	sector: number,
	level: number,
	width: number,
	heigth: number,
	index?: number | string,
) => {
	return axiosInstance
		.get(`/member/sector`, {
			params: {
				latitude,
				longitude,
				sector,
				level,
				width,
				heigth,
				index,
			},
		})
		.then(res => res.data);
};

// // 섹터당 책의 총 갯수 useQuery
export const getTotalBookQuery = (
	bookTitle: string,
	latitude: number,
	longitude: number,
	width: number,
	height: number,
	level: number,
) => {
	return axiosInstance
		.get(`/books/count`, {
			params: {
				bookTitle,
				latitude,
				longitude,
				width,
				height,
				level,
			},
		})
		.then(res => res.data);
};

// // 섹터당 책 목록 useQuery
export const getBookListQuery = (
	bookTitle: string,
	latitude: number,
	longitude: number,
	sector: number,
	level: number,
	width: number,
	heigth: number,
	index?: number | string,
) =>
	axiosInstance
		.get(`/books`, {
			params: {
				bookTitle,
				latitude,
				longitude,
				sector,
				level,
				width,
				heigth,
				index,
			},
		})
		.then(res => res.data);
