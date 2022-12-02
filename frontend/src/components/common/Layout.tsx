import { Outlet } from 'react-router-dom';
import styled from 'styled-components';
import NavBar from './NavBar';
import ScrollToTop from './ScrollToTop';

import { useAppSelector } from '../../redux/hooks';
import useGetAccessTokenRefresh from '../../api/hooks/auth/authRenew';
import Header from './PcVersion/Header/Header';

const Layout = () => {
	const data = useAppSelector(state => state.loginInfo);
	// console.log('log: ', data);
	// useGetAccessTokenRefresh(data);

	return (
		<Main>
			<Header />
			<Body>
				<ScrollToTop />
				<Outlet />
			</Body>
			<NavBar />
		</Main>
	);
};
const Main = styled.div`
	display: flex;
	height: 100%;
	width: 100%;
	justify-content: center;
	background-color: #fbfbfb;
`;

const Body = styled.div`
	width: 100%;
	height: 100%;
	padding-bottom: 50px;
	@media screen and (min-width: 800px) {
		padding-bottom: 0;
	}
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

export default Layout;
