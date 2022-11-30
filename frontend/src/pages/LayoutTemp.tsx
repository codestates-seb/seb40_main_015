import { Outlet } from 'react-router-dom';
import styled from 'styled-components';
import NavBar from '../components/common/NavBar';
import NavTemp from '../components/common/NavTemp';
import ScrollToTop from '../components/common/ScrollToTop';

import { useAppSelector } from '../redux/hooks';
import useGetAccessTokenRefresh from '../api/hooks/auth/authRenew';

const LayoutTemp = () => {
	const data = useAppSelector(state => state.loginInfo);
	// console.log('log: ', data);
	// useGetAccessTokenRefresh(data);

	return (
		<Main>
			<Body>
				<ScrollToTop />
				<Outlet />
			</Body>
			<NavTemp />
		</Main>
	);
};
const Main = styled.div`
	display: flex;
	height: 100%;
	width: 100%;
	justify-content: center;
`;

const Body = styled.div`
	width: 100%;
	padding-bottom: 50px;
`;

const Nav = styled.ul`
	display: flex;
	justify-content: space-around;
	border: 1px solid black;
	width: 100%;
	padding: 10px;
	li {
		margin: 0px 5px;
	}
`;

export default LayoutTemp;
