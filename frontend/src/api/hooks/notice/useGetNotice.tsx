import { useQuery } from '@tanstack/react-query';
import { axiosInstance } from '../../instance';

export type NoticeItemType = {
	noticeData: noticeDataType[];
};

export type noticeDataType = {
	[key: string]: string | number | boolean;
	alarmId: number;
	alarmType:
		| 'RESERVATION'
		| 'RETURN'
		| 'RENTAL'
		| 'MERCHANT_CANCELLATION'
		| 'RESIDENT_CANCELLATION';
	merchantId: number;
	bookTitle: string;
	isRead: boolean;
};

export const useGetNotice = () => {
	const persistStorage = window.localStorage.getItem('persist:login');
	const accessToken: string =
		persistStorage && JSON.parse(persistStorage).accessToken.replace('"', '');

	return useQuery(['notice'], () =>
		axiosInstance.get('/alarm', {
			headers: {
				Authorization: accessToken,
			},
		}),
	);
};
