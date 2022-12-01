import styled from 'styled-components';
import LinkToSign from '../components/common/LinkToSign';
import LoginForm from '../components/Login/LoginForm';
import Logo from '../components/Login/Logo';
import Oauth from '../components/common/Oauth';

const LoginPage = () => {
	return (
		<StyledLoginPage>
			<Contents>
				<Logo />
				<LoginForm />
				<StyledOauth />
				<LinkToSign
					message="회원이 아니신가요?"
					link="/signup"
					linkText="회원가입"
				/>
			</Contents>
		</StyledLoginPage>
	);
};

const StyledLoginPage = styled.div`
	width: 100%;
	height: 100vh;
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
`;

const StyledOauth = styled(Oauth)`
	width: 100%;
	max-width: 270px;
	margin: 1rem 0;
	padding: 0 1rem;
`;

export default LoginPage;
