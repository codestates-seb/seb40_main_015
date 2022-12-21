import styled from 'styled-components';
import { useNavigate } from 'react-router';
import { useAppSelector } from '../redux/hooks';
import { useEffect } from 'react';
import Header from '../components/common/PcVersion/Header/Header';
import LinkToSign from '../components/common/LinkToSign';
import LoginForm from '../components/Login/LoginForm';
import Logo from '../components/Login/Logo';

const LoginPage = () => {
	const { isLogin } = useAppSelector(state => state.loginInfo);
	const navigate = useNavigate();

	useEffect(() => {
		isLogin && navigate(-1);
	}, []);

	return (
		<>
			{isLogin || (
				<StyledLoginPage>
					<Header />
					<Contents>
						<Logo />
						<LoginForm />
						<LinkToSign
							message="회원이 아니신가요?"
							link="/signup"
							linkText="회원가입"
						/>
					</Contents>
				</StyledLoginPage>
			)}
		</>
	);
};

const StyledLoginPage = styled.div`
	width: 100%;
	height: 100vh;
	background-color: #fbfbfb;
	display: flex;
	flex-direction: column;
	align-items: center;
	justify-content: center;
`;

const Contents = styled.div`
	width: 60%;
	max-width: 600px;
	display: flex;
	flex-direction: column;
	align-items: center;
	@media screen and (min-width: 800px) {
		margin-top: 10rem;
	}
`;

export default LoginPage;
