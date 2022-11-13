import React from 'react';
import { Outlet } from 'react-router-dom';
import styled from 'styled-components';
import NavBar from '../components/common/NavBar';
import LenderInfoPage from './LenderInfoPage';

const Layout = () => {
	return (
		<div>
			<Main>
				<LenderInfoPage />
				<NavBar />
			</Main>
		</div>
	);
};

export default Layout;

const Main = styled.div`
	min-height: 70%;
`;
