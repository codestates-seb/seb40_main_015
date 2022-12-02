import styled from 'styled-components';
import { Link } from 'react-router-dom';

import logo from '../../assets/image/logo3.png';
const LoginNeed = () => {
	return (
		<AuthWrapper>
			<AuthRequired>
				<AuthRequiredTitle>
					<LogoImg src={logo} />
					<span>해당 페이지는 동네북 주민만 이용할 수 있습니다.</span>
				</AuthRequiredTitle>

				<AuthRequiredBody>
					<Link to="/login">
						<button>로그인 하러 가기</button>
					</Link>
					<Link to="/signup">
						<button>회원가입 하러 가기</button>
					</Link>
				</AuthRequiredBody>
			</AuthRequired>
		</AuthWrapper>
	);
};

const AuthWrapper = styled.div`
	width: 100%;
	height: 90vh;

	display: flex;
	justify-content: center;
	align-items: center;
`;
const LogoImg = styled.img`
	width: 6rem;
	height: 6rem;
	margin: 0 0 2rem 0;
`;
const AuthRequired = styled.div`
	background-color: white;
	width: 50%;
	height: 50%;
	box-shadow: 0px 5px 20px rgba(0, 0, 0, 0.1);
	border-radius: ${props => props.theme.radius.base};

	display: flex;
	flex-direction: column;
	justify-content: space-around;
	align-items: center;
`;

const AuthRequiredTitle = styled.div`
	padding: 2rem;
	text-align: center;
	display: flex;
	flex-direction: column;
	align-items: center;

	span {
		/* font-size: ${props => props.theme.fontSizes.subtitle}; */
		font-size: 2rem;
		font-family: 'kotra';
		/* display: flex;
		align-items: center;
		justify-content: center;
		padding-left: 10px; */
	}
`;
const AuthRequiredBody = styled.div`
	display: flex;
	flex-direction: column;
	justify-content: center;
	@media screen and (min-width: 800px) {
		width: 100%;
		max-width: 500px;
		flex-direction: row;
		justify-content: space-around;
	}

	button {
		width: 14em;
		background-color: ${props => props.theme.colors.main};
		color: ${props => props.theme.colors.grey};
		border: none;
		padding: 1rem;
		margin-bottom: 2rem;
		border-radius: ${props => props.theme.radius.base};
		cursor: pointer;
	}
`;

export default LoginNeed;
