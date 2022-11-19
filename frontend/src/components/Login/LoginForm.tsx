import axios, { AxiosPromise } from 'axios';
import { useState } from 'react';
import styled from 'styled-components';
import Button from '../common/Button';
import Input from '../common/Input';

const LoginForm = () => {
	const [id, setId] = useState('');
	const [password, setPassword] = useState('');

	const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
		e.preventDefault();

		console.log('id:', id, 'pw:', password);
		const payload = {
			userId: id,
			password: password,
		};

		axios({
			method: 'post',
			url: 'http://13.124.11.174:8080/auth/login',
			withCredentials: true,
			data: payload,
			timeout: 5000,
			headers: { ContentType: 'application/json' },
		})
			// axios
			// 	.post('http://13.124.11.174:8080/auth/login', payload, {
			// 		headers: { ContentType: 'application/json' },
			// 		withCredentials: true,
			// 	})
			.then(data => console.log('res: ', data))
			.then(err => console.error(err));
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
			<StyledButton>로그인</StyledButton>
		</StyledLoginForm>
	);
};

const StyledLoginForm = styled.form`
	width: 100%;
	min-width: 22rem;
	height: 300px;
	display: grid;
`;

const StyledButton = styled(Button)`
	height: 3.5rem;
	margin: 2rem 0;
`;

export default LoginForm;
