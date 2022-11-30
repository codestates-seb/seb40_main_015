import { useEffect } from 'react';
import { PASSWORD_REGEX } from './../constants/constants';

export const useValidate = (
	input: string,
	input2: string,
	setValidity: Function,
) => {
	useEffect(() => {
		if (!input || PASSWORD_REGEX.test(input)) setValidity('password', true);
		else setValidity('password', false);

		if (input === input2) setValidity('passwordCheck', true);
		else setValidity('passwordCheck', false);

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [input, input2]);
};
