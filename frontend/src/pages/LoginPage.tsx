import styled from 'styled-components';
import LinkToSignUp from '../components/Login/LinkToSignUp';
import LoginForm from '../components/Login/LoginForm';
import Logo from '../components/Login/Logo';
import Oauth from '../components/Login/Oauth';

const LoginPage = () => {
	return (
		<StyledLoginPage>
			<Logo />
			<LoginForm />
			<Oauth />
			<LinkToSignUp />
		</StyledLoginPage>
	);
};

export default LoginPage;

const StyledLoginPage = styled.div`
	padding: 70px 0;
	display: flex;
	flex-direction: column;
	align-items: center;
`;
