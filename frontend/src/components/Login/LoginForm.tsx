import axios, { AxiosPromise } from 'axios';
import { useState } from 'react';
import styled from 'styled-components';
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
//components
import Button from '../common/Button';
import Input from '../common/Input';
import { useNavigate } from 'react-router-dom';

//type
interface loginProps {
	userId: string;
	password: string;
}
interface userInfo {
	id: string;
	userId: string;
	nickname: string;
}

// constant
const BASE_URL = process.env.REACT_APP_HOST;

//axios
const axiosInstance = axios.create({
	baseURL: BASE_URL,
	withCredentials: true,
	timeout: 3000,
});

// login fetch post api
const fetchLogin = async (payload: loginProps) => {
	return await axiosInstance.post<userInfo>('/auth/login', payload);
};
const LoginForm = () => {
	const [id, setId] = useState('');
	const [password, setPassword] = useState('');
	const navigate = useNavigate();
	const payload = {
		userId: id,
		password: password,
	};

	const { mutate, data, isLoading, isSuccess, isError } = useMutation({
		mutationFn: () => fetchLogin(payload),
		onSettled: res => {
			console.log('settled?: ', res);
		},
		onSuccess: res => {
			console.log('success data: ', res);
			navigate('/books');
			//
		},
		onError: res => {
			console.log('error : ', res);
		},
	});

	const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
		e.preventDefault();

		console.log('id:', id, 'pw:', password);
		mutate();
		/*
		axios({
			method: 'post',
			url: BASE_URL + '/auth/login',
			withCredentials: true,
			data: payload,
			timeout: 5000,
			headers: { ContentType: 'application/json' },
		})
			.then(data => console.log('res: ', data))
			.then(err => console.error(err));
      */
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
