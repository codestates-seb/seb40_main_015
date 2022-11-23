import useAPI from '../hooks/useAPI';

// 예시)
export const useHistoryAPI = () => {
	const api = useAPI();
	// const id = useAppSelector(state => state.loginInfo.userId);
	// 대여내역 - 빌린 책
	const getRentalBookLists = () => api.get(`/rental/to`);

	// 대여내역 - 빌려준 책
	const getLendBookLists = () => api.get(`/rental/from`);

	// 대여 취소 by 상인
	const axiosCancleByMerchant = (id: string) =>
		api.patch(`/rental/${id}/cancelByMerchant`);

	// 대여 취소 by 주민
	const axiosCancleByCustomer = (id: string) =>
		api.patch(`/rental/${id}/cancelByCustomer`);

	// 도서 수령
	const axiosBookReceipt = (id: string) => api.patch(`/rental/${id}/receive`);

	// 도서 반납
	const axiosBookReturn = (id: string) => api.patch(`/rental/${id}/return`);

	return {
		getRentalBookLists,
		getLendBookLists,
		axiosCancleByMerchant,
		axiosCancleByCustomer,
		axiosBookReceipt,
		axiosBookReturn,
	};
};
