import React, { useEffect, useState, useCallback } from 'react';
import styled from 'styled-components';
import { TbCurrentLocation } from 'react-icons/tb';
import Search from '../components/Map/Search';
import Map from '../components/Map/Map';
import { getMerchantList } from '../api/test';
const BooksSearchPage = () => {
	const [current, setCurrent] = useState<any>();
	const [searchInput, setSearchInput] = useState('');
	const [reset, setReset] = useState(false);
	const [selectOverlay, setSelectOverlay] = useState(null);

	const handleCurrentLocationMove = () => {
		let lat = 0;
		let lon = 0;
		var options = {
			enableHighAccuracy: true,
		};
		navigator.geolocation.getCurrentPosition(
			position => {
				lat = position.coords.latitude; // 위도
				lon = position.coords.longitude; // 경도
				setCurrent({ La: lon, Ma: lat });
			},
			null,
			options,
		);
	};

	console.log(selectOverlay);

	useEffect(() => {
		if (selectOverlay) {
			const {
				sector,
				representativeLocation,
			}: {
				sector: number;
				representativeLocation: { lat: string; lon: string };
			} = selectOverlay;
			const latitude = representativeLocation.lat;
			const longitude = representativeLocation.lon;
			getMerchantList(latitude, longitude, sector);
		}
	}, [selectOverlay]);
	return (
		<Container>
			<FlexBox>
				<Search
					searchInput={searchInput}
					setSearchInput={setSearchInput}
					setReset={setReset}
				/>
				<TbCurrentLocation
					className="location"
					size={40}
					onClick={handleCurrentLocationMove}
				/>
			</FlexBox>
			<Map
				current={current}
				setCurrent={setCurrent}
				reset={reset}
				setSelectOverlay={setSelectOverlay}
			/>
		</Container>
	);
};

const Container = styled.div`
	width: 100%;
	height: 93.5vh;
	position: absolute;
	overflow-x: hidden;
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
