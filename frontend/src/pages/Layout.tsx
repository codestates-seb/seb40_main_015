import React from 'react';
import styled from 'styled-components';
import NavBar from '../components/common/NavBar';
import LenderInfoPage from './LenderInfoPage';

const Layout = () => {
	return (
		<div>
			<Main>
				<LenderInfoPage />
			</Main>
			<NavBar />
		</div>
	);
};

export default Layout;

const Main = styled.div`
	min-height: 70%;
`;
