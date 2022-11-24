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
	bookId: number;
	title: string;
	status: string;
	bookImage:string;
	rentalFee:number;
	merchantName: string;
}


interface ReservationBook {
	bookId: number;
	title: string;
	imageUrl: string;
	rentalFee: number;
	status: string;
}

//회원정보 수정
interface ReservationBook {
	bookId: number;
	title: string;
	imageUrl: string;
	rentalFee: number;
	status: string;
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
			api.get(`/dibs`);
	

	// 마이페이지 - 예약목록(API 미완성)
	const getReservationBookList = () => 
		api.get(`/reservations`);


	// 마이페이지 - 회원정보 수정
	const axiosFixMemberInfo = async (id: string) => {
		try {
			const result = await api.patch(`/member/edit`);
			console.log(result);
			return result.data;
		} catch (err) {
			return err;
		}
	};
 


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
	
	// 사진 등록(endpoint 수정)
	const axiosAddPhoto = async (File:any) => {
		try {
			const result = await api.post(
				`/upload`,
				{File},
				 {
					headers: {
						'Content-Type': 'multipart/form-data'
				},				
				},
			);
			console.log(result);
			return result.data;
		} catch (err) {
			return err;
		}
	};


	return {
		getMyInfo,
		getMemberInfo,
		axiosFixMemberInfo,
		getPickBookList,
		getReservationBookList,
		axiosCancleReservation,
		axiosAddPhoto,
		getMerchantBookLists
	};
};
