import { Link } from 'react-router-dom';
import { Outlet } from 'react-router-dom';
import React from 'react';
import styled from 'styled-components';
import RentBookLists from '../components/History/RentBookLists';
import Button from '../components/common/Button';

const HistoryPage = () => {
	return (
		<Layout>
			<Title>대여 목록</Title>
			<div
				style={{
					display: 'grid',
					width: '100%',
					justifyContent: 'space-evenly',
					margin: '1rem 0',
					gap: '10px',
					gridTemplateColumns: 'auto auto',
				}}>
				<Button fontSize="small">빌린 책</Button>
				<Button fontSize="small">빌려준 책</Button>
			</div>
			<RentBookLists />
			<Outlet />
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
	color: white;
	cursor: pointer;
	/* display: flex;
	flex-direction: row;
	flex-wrap: nowrap; */
`;

export default HistoryPage;
