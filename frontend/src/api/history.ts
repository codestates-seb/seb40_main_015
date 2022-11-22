import useAPI from '../hooks/useAPI';

// 예시)
export const useHistoryAPI = () => {
	const api = useAPI();
	// const id = useAppSelector(state => state.loginInfo.userId);
	// 대여내역 - 빌린 책
	const getRentalBookLists = async () => {
		try {
			const result = await api.get(`/rental/from`);
			console.log(result);
			return result.data;
		} catch (err) {
			return err;
		}
	};

	// 대여내역 - 빌려준 책
	const getLendBookLists = async () => {
		try {
			const result = await api.get(`/rental/to`);
			console.log(result);
			return result.data;
		} catch (err) {
			return err;
		}
	};

	// 대여 취소 by 상인
	const axiosCancleByMerchant = async (id: string) => {
		try {
			const result = await api.patch(`/rental/${id}/cancelByMerchant`);
			console.log(result);
			return result.data;
		} catch (err) {
			return err;
		}
	};

	// 대여 취소 by 주민
	const axiosCancleByCustomer = async (id: string) => {
		try {
			const result = await api.patch(`/rental/${id}/cancelByCustomer`);
			console.log(result);
			return result.data;
		} catch (err) {
			return err;
		}
	};

	// 도서 수령
	const axiosBookReceipt = async (id: string) => {
		try {
			const result = await api.get(`/rental/${id}/receive`);
			console.log(result);
			return result.data;
		} catch (err) {
			return err;
		}
	};

	// 도서 반납
	const axiosBookReturn = async (id: string) => {
		try {
			const result = await api.get(`/rental/${id}/return`);
			console.log(result);
			return result.data;
		} catch (err) {
			return err;
		}
	};

	return {
		getRentalBookLists,
		getLendBookLists,
		axiosCancleByMerchant,
		axiosCancleByCustomer,
		axiosBookReceipt,
		axiosBookReturn,
	};
};
