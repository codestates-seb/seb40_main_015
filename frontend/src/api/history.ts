import useAPI from '../hooks/useAPI';

export const useHistoryAPI = () => {
	const api = useAPI();
	// const id = useAppSelector(state => state.loginInfo.userId);
	// 대여내역 - 빌린 책
	const getRentalBookLists = (index?: string, rentalState?: string) => {
		if (rentalState === '') {
			if (index) {
				return api.get(`/rental/to`, {
					params: {
						index,
					},
				});
			} else {
				return api.get(`/rental/to`);
			}
		} else {
			if (index) {
				return api.get(`/rental/to`, {
					params: {
						index,
						rentalState,
					},
				});
			} else {
				return api.get(`/rental/to`, {
					params: {
						rentalState,
					},
				});
			}
		}
	};

	// 대여내역 - 빌려준 책
	const getLendBookLists = (index?: string, rentalState?: string) => {
		if (rentalState === '') {
			if (index) {
				return api.get(`/rental/from`, {
					params: {
						index,
					},
				});
			} else {
				return api.get(`/rental/from`);
			}
		} else {
			if (index) {
				return api.get(`/rental/from`, {
					params: {
						index,
						rentalState,
					},
				});
			} else {
				return api.get(`/rental/from`, {
					params: {
						rentalState,
					},
				});
			}
		}
	};
	// 대여 취소 by 상인
	const axiosCancleByMerchant = (id: number) =>
		api.patch(`/rental/${id}/cancelByMerchant`);

	// 대여 취소 by 주민
	const axiosCancleByCustomer = (id: number) =>
		api.patch(`/rental/${id}/cancelByCustomer`);

	// 도서 수령
	const axiosBookReceipt = (id: number) => api.patch(`/rental/${id}/receive`);

	// 도서 반납
	const axiosBookReturn = (id: number) => api.patch(`/rental/${id}/return`);

	return {
		getRentalBookLists,
		getLendBookLists,
		axiosCancleByMerchant,
		axiosCancleByCustomer,
		axiosBookReceipt,
		axiosBookReturn,
	};
};
