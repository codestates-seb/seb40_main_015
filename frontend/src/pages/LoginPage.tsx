import styled from 'styled-components';
import LinkToSign from '../components/common/LinkToSign';
import LoginForm from '../components/Login/LoginForm';
import Logo from '../components/Login/Logo';
import Oauth from '../components/common/Oauth';

const LoginPage = () => {
	return (
		<StyledLoginPage>
			<Logo />
			<LoginForm />
			<Oauth />
			<LinkToSign
				message="회원이 아니신가요?"
				link="/signup"
				linkText="회원가입"
			/>
		</StyledLoginPage>
	);
};

export default LoginPage;

const StyledLoginPage = styled.div`
	padding: 70px 0;
	display: flex;
	flex-direction: column;
	align-items: center;
	justify-content: center;
`;
