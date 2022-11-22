import useAPI from '../hooks/useAPI';

export const useMypageAPI = () => {
	const api = useAPI();

	// 마이페이지 - 회원정보 열람
	const getMemberInfo = async (id: string | undefined) => {
		try {
			const result = await api.get(`/member/${id}`);
			console.log(result);
			return result.data;
		} catch (err) {
			return err;
		}
	};

	// 마이페이지 - 회원정보 수정
	const getFixMemberInfo = async (id: string) => {
		try {
			const result = await api.get(`/member/${id}/edit`);
			console.log(result);
			return result.data;
		} catch (err) {
			return err;
		}
	};

	// 마이페이지 - 찜목록
	const getPickBookLists = async (id: string) => {
		try {
			const result = await api.get(`/books/${id}/dibs`);
			console.log(result);
			return result.data;
		} catch (err) {
			return err;
		}
	};

	// 마이페이지 - 예약목록(API 명세서 나오지 않음)
	// const getReservationBookLists = async () => {
	// 	try {
	// 		const result = await api.get();
	// 		console.log(result);
	// 		return result.data;
	// 	} catch (err) {
	// 		return err;
	// 	}
	// };

	// 예약 취소
	const axiosCancleReservation = async (id: string) => {
		try {
			const result = await api.delete(`/books/${id}/reservation`);
			console.log(result);
			return result.data;
		} catch (err) {
			return err;
		}
	};
	// 사진 등록
	const axiosAddPhoto = async () => {
		try {
			const result = await api.post(`image`);
			console.log(result);
			return result.data;
		} catch (err) {
			return err;
		}
	};

	return {
		getMemberInfo,
		getFixMemberInfo,
		getPickBookLists,
		axiosCancleReservation,
		axiosAddPhoto,
	};
};
