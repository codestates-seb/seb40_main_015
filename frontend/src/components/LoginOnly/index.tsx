import { Outlet } from 'react-router-dom';
import styled from 'styled-components';
import NoticeIcon from '../Notice/NoticeIcon';

// Login checking

function LoginOnly() {
	return (
		<StyledLoginOnly>
			<NoticeIcon />
			<Outlet />
		</StyledLoginOnly>
	);
}

const StyledLoginOnly = styled.div`
	width: 100%;
	display: grid;
`;

export default LoginOnly;
