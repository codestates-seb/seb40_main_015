import { useState } from 'react';
import styled from 'styled-components';
import { useMutation } from '@tanstack/react-query';

//components
import Button from '../common/Button';
import Input from '../common/Input';
import { useNavigate } from 'react-router-dom';
import { useDispatch } from 'react-redux';
import { login } from '../../redux/slice/userSlice';
import { axiosInstanceAuth } from '../../api';

//type
interface loginProps {
	userId: string;
	password: string;
}
interface userInfo {
	id: string;
	userId: string;
	nickname: string;
	headers?: { authorization: string };
}

// login post api
const fetchLogin = async (payload: loginProps) => {
	return await axiosInstanceAuth.post<userInfo>('/auth/login', payload);
};

const LoginForm = () => {
	const [id, setId] = useState('');
	const [password, setPassword] = useState('');

	const distpatch = useDispatch();

	const navigate = useNavigate();

	const { mutate, data, isLoading, isSuccess, isError } = useMutation({
		mutationFn: () =>
			fetchLogin({
				userId: id,
				password: password,
			}),
		onSuccess: res => {
			const {
				data,
				headers: { authorization },
			} = res;
			distpatch(login({ ...data, accessToken: authorization, isLogin: true }));
			navigate('/books');
		},
		onError: res => {
			console.log('error : ', res);
		},
	});

	const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
		e.preventDefault();
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
