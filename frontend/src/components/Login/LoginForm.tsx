import { useState } from 'react';
import styled from 'styled-components';
import Button from '../common/Button';
import Input from '../common/Input';

const LoginForm = () => {
	const [id, setId] = useState('');
	const [password, setPassword] = useState('');

	const handleSubmit = (e: React.SyntheticEvent) => {
		e.preventDefault();
	};

	return (
		<StyledLoginForm onSubmit={handleSubmit}>
			<Input label="아이디" state={id} setState={setId} />
			<Input
				label="비밀번호"
				state={password}
				setState={setPassword}
				type="password"
			/>
			<ButtonWrapper>
				<Button>로그인</Button>
			</ButtonWrapper>
		</StyledLoginForm>
	);
};

const StyledLoginForm = styled.form`
	width: 100%;
	min-width: 250px;
	height: 250px;
	display: grid;
`;

const ButtonWrapper = styled.div`
	display: flex;
	flex-direction: column;
	margin: 2rem 0;
`;

export default LoginForm;
