export const NOTICE_MESSAGES = {
	RESERVATION: {
		icon: '💌',
		type: '예약',
		message: '의 대여가 가능합니다.',
		link: '/merchant/${el.merchantId}',
	},
	RETURN: {
		icon: '⏰',
		type: '대여',
		message: '의 대여 반납이 하루 남았습니다.',
		link: '/history',
	},
	RENTAL: {
		icon: '📚',
		type: '등록',
		message: '의 대여 신청이 접수되었습니다.',
		link: '/history',
	},
	MERCHANT_CANCELLATION: {
		icon: '❌',
		type: '신청',
		message: '의 대여가 취소되었습니다.',
		link: '/history',
	},
	RESIDENT_CANCELLATION: {
		icon: '❌',
		type: '등록',
		message: '의 대여가 취소되었습니다.',
		link: '/history',
	},
	MESSAGE: {
		icon: '💬',
		type: '',
		message: ' 관련 채팅이 도착했습니다.',
		link: '/chats',
	},
};
