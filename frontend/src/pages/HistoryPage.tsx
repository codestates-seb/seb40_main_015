import { Link } from 'react-router-dom';
import { Outlet } from 'react-router-dom';
import React from 'react';
import styled from 'styled-components';

const HistoryPage = () => {
	return (
		<Layout>
			<Title>대여 목록</Title>
			<div
				style={{
					display: 'flex',
					width: '100%',
					justifyContent: 'space-evenly',
					marginTop: '1rem',
				}}>
				<Tab>빌린 책</Tab>
				<Tab>빌려준 책</Tab>
			</div>
		</Layout>
	);
};

const Layout = styled.div`
	display: flex;
	align-items: center;
	flex-direction: column;
	padding: 1rem;
	h2 {
		font-size: 2rem;
		margin-bottom: 1rem;
	}
`;

const Title = styled.p`
	width: 100%;
	font-size: 2.5rem;
	text-align: center;
	padding-bottom: 1rem;
	border-bottom: 1px solid #a7a7a7;
`;

const Tab = styled.button`
	padding: 0.8rem 3rem;
	background-color: ${props => props.theme.colors.main};
	border: none;
	border-radius: 5px;
	box-shadow: nonoe;
	/* color: white; */
	cursor: pointer;
	/* display: flex;
	flex-direction: row;
	flex-wrap: nowrap; */
`;

export default HistoryPage;
