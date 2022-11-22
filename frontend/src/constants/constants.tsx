export const BASE_URL = process.env.REACT_APP_HOST;

export const PASSWORD_REGEX =
	/^(?=.*?[a-z])(?=.*?[0-9])(?=.*?[~?!@#$%^&*_-]).{8,}$/;

export const USERID_REGEX = /^[a-zA-z0-9]/;
