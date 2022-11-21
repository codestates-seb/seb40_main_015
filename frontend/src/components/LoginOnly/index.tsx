import { Outlet } from 'react-router-dom';
import styled from 'styled-components';
import { useAppSelector } from '../../redux/hooks';
import NoticeIcon from '../Notice/NoticeIcon';
import LoginNeed from './LoginCheckElements';

// export interface StateProps {
// 	loginInfo: {
// 		isLogin: boolean;
// 	};
// }

// Login checking
function LoginOnly() {
	const isLogin = useAppSelector(state => state.loginInfo.isLogin);

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
