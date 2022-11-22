import React from 'react';
import { Outlet } from 'react-router-dom';
import styled from 'styled-components';
import NavBar from '../components/common/NavBar';

const Layout = () => {
	return (
		<div>
			<Main>
				<NavBar />
			</Main>
		</div>
	);
};

export default Layout;

const Main = styled.div`
	min-height: 70%;
`;
