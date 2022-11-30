import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import styled from 'styled-components';
import useAPI from '../../hooks/useAPI';
import { useValidate } from '../../hooks/useValidate';
import { useAppDispatch } from '../../redux/hooks';
import { makeSignUpMessages } from '../../utils/makeSignUpMessages';
import notify from '../../utils/notify';
import Button from '../common/Button';
import Clause from './Clause';
import IdSection from './IdSection';
import PasswordSection from './PasswordSection';

export type inputKeys = 'userId' | 'nickname' | 'password' | 'passwordCheck';

const SignUpForm = () => {
	const [inputs, setInputs] = useState({
		userId: '',
		nickname: '',
		password: '',
		passwordCheck: '',
	});
	const [isValid, setIsValid] = useState({
		userId: false,
		nickname: false,
		password: false,
		passwordCheck: false,
	});
	const { userId, nickname, password } = inputs;
	const [isChecked, setIsChecked] = useState(false);

	const api = useAPI();
	const dispatch = useAppDispatch();
	const navigate = useNavigate();
	const notifyMessages = makeSignUpMessages(inputs, isValid, isChecked);
	const goNotify = (message: string) => notify(dispatch, message);

	const handleSubmit = async (e: React.SyntheticEvent) => {
		e.preventDefault();

		notifyMessages.forEach((message, notifyCase) => {
			if (notifyCase) goNotify(message);
		});

		if (!Object.values(isValid).includes(false) && isChecked) {
			const data = { userId, nickname, password };
			try {
				await api.post('/auth/signup', data);
				goNotify('회원가입 완료!');
				navigate('/login');
			} catch {
				goNotify('회원가입에 실패했습니다.');
			}
		}
	};

	const getSectionProps = (label: string, select: inputKeys) => {
		const state = inputs[select];
		const setState = (value: string) =>
			setInputs(pre => {
				return {
					...pre,
					[select]: value,
				};
			});
		const validity = isValid[select];
		const setValidity = (value: boolean) =>
			setIsValid(pre => {
				return {
					...pre,
					[select]: value,
				};
			});
		const type = select;
		return { label, state, setState, validity, setValidity, type };
	};

	useValidate(
		inputs.password,
		inputs.passwordCheck,
		(input: inputKeys, value: boolean) =>
			setIsValid(pre => {
				return { ...pre, [input]: value };
			}),
	);

	return (
		<StyledSignUpForm onSubmit={handleSubmit}>
			<IdSection data={getSectionProps('아이디', 'userId')} notify={goNotify} />
			<IdSection
				data={getSectionProps('닉네임', 'nickname')}
				notify={goNotify}
			/>
			<PasswordSection data={getSectionProps('비밀번호', 'password')} />
			<PasswordSection
				data={getSectionProps('비밀번호 확인', 'passwordCheck')}
			/>
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
