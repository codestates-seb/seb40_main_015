import { useEffect, useState } from 'react';
import { IoAlertCircle } from 'react-icons/io5';
import styled from 'styled-components';
import Button from '../common/Button';
import Input from '../common/Input';
import Bubble from './Bubble';
import Clause from './Clause';

const SignUpForm = () => {
	const [id, setId] = useState('');
	const [nickname, setNickname] = useState('');
	const [password, setPassword] = useState('');
	const [passwordCheck, setPasswordCheck] = useState('');
	const [isChecked, setIsChecked] = useState(false);
	const [passwordError, setPasswordError] = useState(false);
	const [passwordCheckError, setPasswordCheckError] = useState(false);
	const passwordRegExp = /^(?=.*?[a-z])(?=.*?[0-9])(?=.*?[~?!@#$%^&*_-]).{8,}$/;

	useEffect(() => {
		if (password && !passwordRegExp.test(password)) setPasswordError(true);
		else if (passwordRegExp.test(password)) setPasswordError(false);

		if (password !== passwordCheck) setPasswordCheckError(true);
		else if (password === passwordCheck) setPasswordCheckError(false);
	}, [password, passwordCheck]);

	const handleSubmit = (e: React.SyntheticEvent) => {
		e.preventDefault();
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
	display: flex;
	align-items: flex-end;
	grid-template-columns: 260px 1px;
	.overlapCheck {
		color: ${props => props.theme.colors.buttonGreen};
		font-weight: bold;
		width: 4.1rem;
		position: relative;
		bottom: 1rem;
		right: 4.1rem;
	}
`;

const PasswordWrapper = styled.div<{ error: boolean }>`
	/* min-width: ${props => (props.error ? '138.3%' : '100%')}; */
	display: grid;
	align-items: flex-end;
	grid-template-columns: 260px 1px;
	.icon {
		font-size: 25px;
		color: #ff6a00;
		position: absolute;
		cursor: pointer;
	}
`;

const AlertSection = styled.div<{ error: boolean }>`
	/* width: 10rem; */
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
