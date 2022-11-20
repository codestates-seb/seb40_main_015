import { useSelector } from 'react-redux';
import { Outlet } from 'react-router-dom';
import styled from 'styled-components';
import NoticeIcon from '../Notice/NoticeIcon';
import LoginNeed from './LoginCheckElements';

interface StateProps {
	loginInfo: {
		isLogin: boolean;
	};
}

// Login checking
function LoginOnly() {
	const isLogin = useSelector((state: StateProps) => state.loginInfo.isLogin);

	return (
		<StyledLoginOnly>
			{isLogin ? (
				<>
					<NoticeIcon />
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
