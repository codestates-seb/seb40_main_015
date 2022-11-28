import { PayloadType } from "../api/hooks/createBooks/usePostBooks";

export const validateBookCreatePayloads = (payload: PayloadType) => {
	const values = Object.values(payload);
	for (let i = 0; i < values.length; i++) {
		if (!values[i]) return false;
	}
	return true;
};
