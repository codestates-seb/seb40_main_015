import { Outlet } from 'react-router-dom';
import styled from 'styled-components';
import { useAppSelector } from '../../redux/hooks';
import LoginNeed from './LoginCheckElements';

// Login checking
function LoginOnly() {
	const isLogin = useAppSelector(state => state.loginInfo.isLogin);

	return (
		<StyledLoginOnly>
			{isLogin ? (
				<>
					<Outlet />
				</>
			) : (
				<LoginNeed />
			)}
		</StyledLoginOnly>
	);
}

const StyledLoginOnly = styled.div`
	width: 100%;
	display: grid;
`;

export default LoginOnly;
