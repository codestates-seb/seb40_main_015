export const NOTICE_MESSAGES = {
	RESERVATION: [
		'💌',
		'예약',
		'의 대여가 가능합니다.',
		'/merchant/${el.merchantId}',
	],
	RETURN: ['⏰', '대여', '의 대여 반납이 하루 남았습니다.', '/history'],
	RENTAL: ['📚', '등록', '의 대여 신청이 접수되었습니다.', '/history'],
	MERCHANT_CANCELLATION: [
		'❌',
		'신청',
		'의 대여가 취소되었습니다.',
		'/history',
	],
	RESIDENT_CANCELLATION: [
		'❌',
		'등록',
		'의 대여가 취소되었습니다.',
		'/history',
	],
	MESSAGE: ['💬', '', ' 관련 채팅이 도착했습니다.', '/chats'],
};
