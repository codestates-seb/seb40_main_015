import { inputKeys } from './../components/SignUp/SignUpForm';

type Input = { [key in inputKeys]: string | boolean };

export const makeSignUpMessages = (
	input: Input,
	isValid: Input,
	isChecked: boolean,
) =>
	new Map([
		[!isChecked, '약관에 동의해주세요 ☺️'],
		[
			!input.passwordCheck || isValid.passwordCheck,
			'비밀번호 확인을 맞게 입력해주세요',
		],
		[!input.password || isValid.password, '올바른 비밀번호를 입력해주세요'],
		[!isValid.nickname, '닉네임 중복여부를 확인해주세요'],
		[!isValid.id, '아이디 중복여부를 확인해주세요'],
		[!input.nickname, '닉네임을 입력해 주세요'],
		[!input.id, '아이디를 입력해 주세요'],
	]);
