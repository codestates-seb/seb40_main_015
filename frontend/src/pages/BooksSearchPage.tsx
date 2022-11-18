import React, { useEffect, useState, useCallback } from 'react';
import styled from 'styled-components';
import { TbCurrentLocation } from 'react-icons/tb';
import Search from '../components/Map/Search';
import Map from '../components/Map/Map';

const BooksSearchPage = () => {
	const [current, setCurrent] = useState<any>();
	const [centerCoord, setCenterCoord] = useState();

	return (
		<Box>
			<FlexBox>
				<Search />
				<TbCurrentLocation
					className="location"
					size={40}
					onClick={() => {
						// onClickToggleModal();
						// handleCurrentLocationMove();
					}}
				/>
			</FlexBox>
			<Map />
		</Box>
	);
};

const Box = styled.div`
	width: 100vw;
	height: 93.5vh;
	position: absolute;
`;

const Container = styled.div`
	width: 100vw;
	height: 93.5vh;
`;

const FlexBox = styled.div`
	position: fixed;
	top: 20px;
	margin: 0 auto;
	left: 0;
	right: 0;
	display: flex;
	justify-content: center;
	align-items: center;
	z-index: 90;
	padding: 0 20px;

	.location {
		background-color: white;
		z-index: 100;
		margin-left: 1.25rem;
		height: 3rem;
		color: #016241;
		cursor: pointer;
		padding: 5px;
		border-radius: 5px;
	}
`;

export default BooksSearchPage;
