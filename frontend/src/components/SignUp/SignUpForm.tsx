import { useEffect, useState } from 'react';
import styled from 'styled-components';
import { PASSWORD_REGEX } from '../../constants/constants';
import { useAppDispatch } from '../../redux/hooks';
import notify from '../../utils/notify';
import Button from '../common/Button';
import Clause from './Clause';
import IdSection from './IdSection';
import PasswordSection from './PasswordSection';

const SignUpForm = () => {
	const [id, setId] = useState('');
	const [isValidId, setIsValidId] = useState(false);
	const [nickname, setNickname] = useState('');
	const [isValidNickname, setIsValidNickname] = useState(false);
	const [password, setPassword] = useState('');
	const [passwordError, setPasswordError] = useState(false);
	const [passwordCheck, setPasswordCheck] = useState('');
	const [passwordCheckError, setPasswordCheckError] = useState(false);
	const [isChecked, setIsChecked] = useState(false);
	const idSectionData = [
		{ label: '아이디', state: id, setState: setId },
		{ label: '닉네임', state: nickname, setState: setNickname },
	];
	const passwordSectionData = [
		{
			label: '비밀번호',
			state: password,
			setState: setPassword,
			error: passwordError,
			type: 'password',
		},
		{
			label: '비밀번호 확인',
			state: passwordCheck,
			setState: setPasswordCheck,
			error: passwordCheckError,
			type: 'passwordCheck',
		},
	];

	const dispatch = useAppDispatch();

	const validateInput = () => {
		if (password && !PASSWORD_REGEX.test(password)) setPasswordError(true);
		else if (PASSWORD_REGEX.test(password)) setPasswordError(false);

		if (password !== passwordCheck) setPasswordCheckError(true);
		else if (password === passwordCheck) setPasswordCheckError(false);
	};

	useEffect(() => {
		validateInput();
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [password, passwordCheck]);

	const handleSubmit = (e: React.SyntheticEvent) => {
		e.preventDefault();
		validateInput();
		switch (true) {
			case !id:
				notify(dispatch, '아이디를 입력해 주세요');
				break;
			case !nickname:
				notify(dispatch, '닉네임을 입력해 주세요');
				break;
			case !password:
				notify(dispatch, '비밀번호를 입력해 주세요');
				break;
			case !isValidId:
				notify(dispatch, '아이디 중복여부를 확인해주세요');
				break;
			case !isValidNickname:
				notify(dispatch, '닉네임 중복여부를 확인해주세요');
				break;
			case !password || passwordError:
				notify(dispatch, '올바른 비밀번호를 입력해주세요');
				break;
			case !passwordCheck || passwordCheckError:
				notify(dispatch, '비밀번호 확인을 맞게 입력해주세요');
				break;
			case !isChecked:
				notify(dispatch, '약관에 동의해주세요 ☺️');
				break;
			default:
				notify(dispatch, '전부완료!');
		}
	};

	return (
		<StyledSignUpForm onSubmit={handleSubmit}>
			<IdSection idSectionData={idSectionData} />
			<PasswordSection passwordSectionData={passwordSectionData} />
			<Clause isChecked={isChecked} setIsChecked={setIsChecked} />
			<StyledButton>회원가입</StyledButton>
		</StyledSignUpForm>
	);
};

const StyledSignUpForm = styled.form`
	width: 100%;
	max-width: 22rem;
	margin-top: 2rem;
	display: flex;
	flex-direction: column;
`;

const StyledButton = styled(Button)`
	height: 3.5rem;
	margin-bottom: 1.5rem;
`;

export default SignUpForm;
