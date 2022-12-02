import { useEffect } from 'react';
import { useNavigate } from 'react-router';
import styled from 'styled-components';
import LinkToSign from '../components/common/LinkToSign';
import Oauth from '../components/common/Oauth';
import Header from '../components/common/PcVersion/Header/Header';
import Title from '../components/common/Title';
import Animation from '../components/Loading/Animation';
import SignUpForm from '../components/SignUp/SignUpForm';
import { useAppSelector } from '../redux/hooks';

function SignupPage() {
	const { isLogin } = useAppSelector(state => state.loginInfo);
	const navigate = useNavigate();

	useEffect(() => {
		isLogin && navigate(-1);
	}, []);

	return (
		<>
			{isLogin || (
				<StyledSignupPage>
					<Title text="회원가입" />
					<Header />
					<Main>
						<SubTitle>동네북 회원가입</SubTitle>
						<SignUpForm />
						<StyledOauth />
						<LinkToSign
							message="이미 회원이신가요?"
							link="/login"
							linkText="로그인"
						/>
					</Main>
				</StyledSignupPage>
			)}
		</>
	);
}

const StyledSignupPage = styled.div`
	width: 100%;
	min-width: 250px;
	padding: 0 20px;
	background-color: #fbfbfb;
	display: flex;
	flex-direction: column;
	align-items: center;
`;

const StyledOauth = styled(Oauth)`
	width: 70%;
	max-width: 270px;
	margin: 1rem 0;
	padding: 0 1rem;
`;

const Main = styled.div`
	width: 80%;
	max-width: 800px;
	display: flex;
	flex-direction: column;
	align-items: center;
`;

const SubTitle = styled.p`
	font-size: ${props => props.theme.fontSizes.maintitle};
	font-weight: bold;
	color: black;

	width: 100%;
	display: flex;
	justify-content: flex-start;

	@media screen and (max-width: 800px) {
		display: none;
	}
`;

export default SignupPage;
