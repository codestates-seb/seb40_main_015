import { useState } from 'react';
import styled from 'styled-components';
import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';

//components
import Button from '../common/Button';
import Input from '../common/Input';
import { useNavigate } from 'react-router-dom';
import { useDispatch } from 'react-redux';
import { login, logout } from '../../redux/slice/userSlice';

//etc
import notify from '../../utils/notify';
import useAuthAPI from '../../api/auth';

const LoginForm = () => {
	const [id, setId] = useState('');
	const [password, setPassword] = useState('');
	const [isValidID, setIsValidID] = useState(true);
	const [isValidPW, setIsValidPW] = useState(true);
	const [isTokenStaled, setIsTokenStaled] = useState(false);

	const dispatch = useDispatch();
	const navigate = useNavigate();

	const queryClient = useQueryClient();
	const { getAccessTokenRefresh, postLogin } = useAuthAPI();
	// login query
	const { mutate, data: loginMutationData } = useMutation({
		mutationKey: ['loginInfo'],
		mutationFn: () =>
			postLogin({
				userId: id,
				password: password,
			}),
		onSuccess: res => {
			setTimeout(() => {
				// queryClient.invalidateQueries(['loginInfo']);
				setIsTokenStaled(true);
			}, 1000 * 60 * 29);
			// 액세스토큰 갱신 요청 -> 리프레시 만료시 강제 로그아웃.
			const {
				data,
				headers: { authorization },
			} = res;
			// console.log(data);
			dispatch(login({ ...data, accessToken: authorization, isLogin: true }));
			notify(dispatch, `${data.nickname}님 안녕하세요`);
			navigate('/books');
		},
		onError: res => {
			console.log('login failed: ', res);
			alert('아이디 혹은 비밀번호를 다시 한 번 확인해주세요');
		},
	});

	// token renew
	const { isLoading: queryIsLoading } = useQuery({
		queryKey: ['accessToken', 'loginInfo'],
		queryFn: getAccessTokenRefresh,
		enabled: isTokenStaled && window.document.hasFocus(),
		staleTime: 1000 * 60 * 28,
		onSuccess: res => {
			console.log('token renew complete');
			setIsTokenStaled(false);
			const {
				headers: { authorization },
			} = res;
			dispatch(
				login({
					...loginMutationData,
					accessToken: authorization,
					isLogin: true,
				}),
			);
			setTimeout(() => {
				// queryClient.invalidateQueries(['loginInfo']);
				setIsTokenStaled(true);
			}, 1000 * 60 * 29);
		},
		onError: err => {
			console.error('token renew error: ', err);
			notify(
				dispatch,
				'로그인 시간이 만료되었습니다. 다시 로그인 해주시기 바랍니다.',
			);
			dispatch(logout());
			navigate('/login');
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
