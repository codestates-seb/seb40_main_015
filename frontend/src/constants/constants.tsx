export const BASE_URL = process.env.REACT_APP_HOST;

export const PASSWORD_REGEX =
	/^(?=.*?[a-z])(?=.*?[0-9])(?=.*?[~?!@#$%^&*_-]).{8,}$/;

export const USERID_REGEX = /^[a-zA-z0-9]/;

export const CONFIRM_MESSAGES = {
	delete: '정말 삭제하시겠습니까?',
};

export const MESSAGES = {
	noNotice: '도착한 알림이 없어요',
};
