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

//etc
import notify from '../../utils/notify';

//type
interface loginProps {
	userId: string;
	password: string;
}
interface userInfo {
	id: string;
	userId: string;
	nickName: string;
	headers?: { authorization: string };
}

// login post api
const fetchLogin = async (payload: loginProps) => {
	return await axiosInstanceAuth.post<userInfo>('/auth/login', payload);
};

const LoginForm = () => {
	const [id, setId] = useState('');
	const [password, setPassword] = useState('');
	const [isValidID, setIsValidID] = useState(true);
	const [isValidPW, setIsValidPW] = useState(true);

	const distpatch = useDispatch();
	const navigate = useNavigate();

	// login query
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
			notify(distpatch, `${data.nickName}님 안녕하세요`);
			navigate('/books');
		},
		onError: res => {
			console.log('login failed: ', res);
			alert('아이디 혹은 비밀번호를 다시 한 번 확인해주세요');
		},
	});

	const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
		e.preventDefault();

		if (id === '') setIsValidID(false);
		else setIsValidID(true);
		if (password === '') setIsValidPW(false);
		else setIsValidPW(true);
		if (id === '' || password === '') return;

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
			<IdWrapper>
				<Input label="아이디" state={id} setState={setId} />
				{isValidID ? '' : <ErrorMsg>아이디를 입력해주세요</ErrorMsg>}
			</IdWrapper>
			<PwWrapper>
				<Input
					label="비밀번호"
					state={password}
					setState={setPassword}
					type="password"
				/>
				{isValidPW ? '' : <ErrorMsg>비밀번호를 입력해주세요</ErrorMsg>}
			</PwWrapper>

			<StyledButton>로그인</StyledButton>
		</StyledLoginForm>
	);
};
const IdWrapper = styled.div``;
const PwWrapper = styled.div``;
const ErrorMsg = styled.p`
	color: #de4f55;
	padding: 0.4rem;
`;

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
