import { useAppDispatch } from './../redux/hooks';
import axios from 'axios';
import { Settings } from 'http2';
import useAPI from '../hooks/useAPI';
import { setUserInfo } from '../redux/slice/userInfoSlice';

// 마이페이지 유저정보 및 찜목록 요청 getPickBookLists
interface Member {
	memberId: number;
	name: string;
	location: {
		lat: string | number;
		lon: string | number;
	} | null;
	address: string | null;
	totalBookCount: number;
	avatarUrl: string;
}

interface PickBook {
	content: {
		bookId: number;
		title: string;
		status: string;
		bookImage: string;
		rentalFee: number;
		merchantName: string;
		location: {
			latitude: number | number;
			longitude: number | number;
		};
	}[];
}

//예약목록 조회
interface ReservationBook {
	content: {
		reservationInfo: {
			reservationId: number;
			rentalExpectedAt: string;
		};
		bookInfo: {
			bookId: number;
			title: string;
			bookImage: string;
			rentalFee: number;
			merchantName: string;
		};
	}[];
}

//회원정보 수정
interface FixmemberInfo {
	nickname: string;
	location: { latitude: string | number; longitude: string | number };
	address: string;
	avatarUrl: string;
}

export const useMypageAPI = () => {
	const api = useAPI();
	const dispatch = useAppDispatch();

	// 상인정보용 도서 목록 조회
	const getMerchantBookLists = (
		id: string | undefined,
		index?: string | undefined,
	) => {
		if (index) {
			return api
				.get(`/member/${id}/books`, { params: { index } })
				.then(res => res.data);
		} else {
			return api.get(`/member/${id}/books`).then(res => res.data);
		}
	};

	// 마이페이지 - 회원정보 열람(주용님)
	const getMemberInfo = async (id: string | undefined) =>
		await api.get(`/member/${id}`).then(res => res.data);

	// 마이페이지 - 회원정보 열람(지구)
	const getMyInfo = async (id: string | undefined) =>
		await api.get<Member>(`/member/${id}`).then(res => {
			dispatch(setUserInfo(res.data));
			return res.data;
		});

	//마이페이지 - 찜목록 (infinite scroll)
	const getPickBookList = (id?: number) =>
		api.get<PickBook>(id ? `/dibs?index=${id}` : `/dibs`).then(res => res.data);

	// 마이페이지 - 예약목록(infinite scroll)
	const getReservationBookList = (id?: number) =>
		api
			.get<ReservationBook>(id ? `/reservation?index=${id}` : '/reservation')
			.then(res => res.data);

	// 마이페이지 - 회원정보 수정
	const patchFixMemberInfo = (data: FixmemberInfo) =>
		api.patch(`/member/edit`, data);

	// 예약 취소
	const deleteReservation = (reservationId: string) => {
		api.delete(`/reservation/cancel/${reservationId}`);
	};

	// 사진 등록(endpoint 수정)
	const axiosAddPhoto = (data: any) => {
		axios.post(`/upload`, data).then(res => console.log(res));
	};

	return {
		getMyInfo,
		getMemberInfo,
		patchFixMemberInfo,
		getPickBookList,
		getReservationBookList,
		deleteReservation,
		getMerchantBookLists,
		axiosAddPhoto,
	};
};
