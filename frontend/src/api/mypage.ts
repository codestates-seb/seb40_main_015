
import useAPI from '../hooks/useAPI';



// 마이페이지 유저정보 및 찜목록 요청 getPickBookLists
interface Member {
	memberId: number;
	name: string;
	location:{
		lat: string | number;
		lon: string | number;
} | null,
	address: string | null;
	totalBookCount: number;
	avatarUrl: string | null;
}

interface PickBook {
	bookId:      string;
	title:       string;
	rentalFee:   number;
	status:      string;
}


export const useMypageAPI = () => {
	const api = useAPI();

	// 상인정보용 도서 목록 조회(주용님)
	const getMerchantBookLists = (id: string | undefined) =>
	api.get(`/member/${id}/books`).then(res => res.data);
	

	// 마이페이지 - 회원정보 열람(주용님)
		const getMemberInfo = async (id: string | undefined) =>
		await api.get(`/member/${id}`).then(res => res.data)


	// 마이페이지 - 회원정보 열람
			const getMyInfo = async (id: string | undefined) =>
			await api.get<Member>(`/member/${id}`).then(res => res.data);
		
			
	//마이페이지 - 찜목록
		const getPickBookList = () => 
			api.get<PickBook>(`/dibs`);
	
	

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

	// 예약 취소(지구)
	const axiosCancleReservation = async (id: string) => {
		try {
			const result = await api.delete(`/books/${id}/reservation`);
			console.log(result);
			return result.data;
		} catch (err) {
			return err;
		}
	};
	
	// 사진 등록(지구)
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
		getMyInfo,
		getMemberInfo,
		getFixMemberInfo,
		getPickBookList,
		axiosCancleReservation,
		axiosAddPhoto,
		getMerchantBookLists
	};
};
