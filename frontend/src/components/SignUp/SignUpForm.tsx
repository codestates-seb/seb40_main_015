import { useEffect, useState } from 'react';
import { IoAlertCircle } from 'react-icons/io5';
import styled from 'styled-components';
import { useAppDispatch } from '../../redux/hooks';
import notify from '../../utils/notify';
import Button from '../common/Button';
import Input from '../common/Input';
import Bubble from './Bubble';
import Clause from './Clause';

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

	const passwordRegExp = /^(?=.*?[a-z])(?=.*?[0-9])(?=.*?[~?!@#$%^&*_-]).{8,}$/;
	const dispatch = useAppDispatch();

	const validateInput = () => {
		if (password && !passwordRegExp.test(password)) setPasswordError(true);
		else if (passwordRegExp.test(password)) setPasswordError(false);

		if (password !== passwordCheck) setPasswordCheckError(true);
		else if (password === passwordCheck) setPasswordCheckError(false);
	};

	useEffect(() => {
		validateInput();
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
			<IdWrapper>
				<Input label="아이디" state={id} setState={setId} />
				<div className="overlapCheck">중복확인</div>
			</IdWrapper>
			<IdWrapper>
				<Input label="닉네임" state={nickname} setState={setNickname} />
				<div className="overlapCheck">중복확인</div>
			</IdWrapper>
			<PasswordWrapper error={passwordError}>
				<Input
					label="비밀번호"
					state={password}
					setState={setPassword}
					type="password"
				/>
				<AlertSection error={passwordError}>
					<IoAlertCircle className="icon" />
					<Bubble type="password" />
				</AlertSection>
			</PasswordWrapper>
			<PasswordWrapper error={passwordCheckError}>
				<Input
					label="비밀번호 확인"
					state={passwordCheck}
					setState={setPasswordCheck}
					type="password"
				/>
				<AlertSection error={passwordCheckError}>
					<IoAlertCircle className="icon" />
					<Bubble type="passwordCheck" />
				</AlertSection>
			</PasswordWrapper>
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

const IdWrapper = styled.div`
	min-width: 117.5%;
	display: grid;
	grid-template-columns: 22rem 1px;
	.overlapCheck {
		width: 4.1rem;
		height: 1rem;
		background-color: transparent;
		color: ${props => props.theme.colors.buttonGreen};
		font-weight: bold;
		position: relative;
		top: 3.4rem;
		right: 4.1rem;
		cursor: pointer;
	}
`;

const PasswordWrapper = styled.div<{ error: boolean }>`
	display: grid;
	align-items: flex-end;
	grid-template-columns: 22rem 1px;
	.icon {
		font-size: 25px;
		color: #ff6a00;
		position: absolute;
		cursor: pointer;
	}
`;

const AlertSection = styled.div<{ error: boolean }>`
	position: relative;
	left: 1rem;
	top: 2.3rem;
	display: ${props => (props.error ? 'flex' : 'none')};
	flex-direction: ${props => props.error && 'column'};
	.bubble {
		visibility: hidden;
	}
	:hover {
		.bubble {
			visibility: visible;
		}
	}
`;

const StyledButton = styled(Button)`
	height: 3.5rem;
	margin-bottom: 1.5rem;
`;

export default SignUpForm;
