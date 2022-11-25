import { axiosInstance } from '.';

// 섹터당 상인의 총 갯수
//location 형식
// localhost:8080/member/count?latitude=37.4974939&longitude=127.0270229
export const getTotalMerchant = async (
	latitude: number,
	longitude: number,
	width: number,
	height: number,
	level: number,
) => {
	try {
		const result = await axiosInstance.get(`/member/count`, {
			params: {
				latitude,
				longitude,
				width,
				height,
				level,
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
	latitude: number,
	longitude: number,
	sector: number,
	level: number,
	width: number,
	heigth: number,
	index?: number | string,
) => {
	try {
		const result = await axiosInstance.get(`/member/sector`, {
			params: {
				latitude,
				longitude,
				sector,
				level,
				width,
				heigth,
				index,
			},
		});
		console.log(result);
		return result.data;
	} catch (err) {
		return err;
	}
};

// // 섹터당 책의 총 갯수
export const getTotalBook = async (
	bookTitle: string,
	latitude: number,
	longitude: number,
	width: number,
	height: number,
	level: number,
) => {
	try {
		const result = await axiosInstance.get(`/books/count`, {
			params: {
				bookTitle,
				latitude,
				longitude,
				width,
				height,
				level,
			},
		});
		console.log(result);
		return result.data;
	} catch (err) {
		return err;
	}
};

// // 섹터당 책 목록
export const getBookList = async (
	bookTitle: string,
	latitude: number,
	longitude: number,
	sector: number,
	level: number,
	width: number,
	heigth: number,
	index?: number | string,
) => {
	try {
		const result = await axiosInstance.get(`/books`, {
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
		});
		console.log(result);
		return result.data;
	} catch (err) {
		return err;
	}
};
