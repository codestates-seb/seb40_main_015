import { useEffect, useState } from 'react';
import styled from 'styled-components';
import { TbCurrentLocation } from 'react-icons/tb';
import Search from '../components/Map/Search';
import Map from '../components/Map/Map';
import { getBookList, getMerchantList, getTotalBook } from '../api/map';
import {
	data,
	dummyMerchantList,
	bookListsDummy,
} from '../components/Map/dummy';
import useWindowSize from '../hooks/useWindowSize';
import useGeoLocation from '../hooks/useGeoLocation';

interface MerchantSectorProps {
	merchantCount: number;
	sector: number;
	representativeLocation: {
		lat: string;
		lon: string;
	};
}

const BooksSearchPage = () => {
	const [current, setCurrent, handleCurrentLocationMove] = useGeoLocation();
	const [searchInput, setSearchInput] = useState('');
	const [reset, setReset] = useState(false);
	const [selectOverlay, setSelectOverlay] = useState(null);
	const [merchantSector, setMerchantSector] =
		useState<MerchantSectorProps[]>(data);
	const [merchantLists, setMerchantLists] = useState<any>([]);
	const [bookSector, setBookSector] = useState([]);
	const [bookLists, setBookLists] = useState<any>([]);
	const [zoomLevel, setZoomLevel] = useState(5);
	const size = useWindowSize(zoomLevel);
	console.log(size);

	useEffect(() => {
		if (typeof selectOverlay === 'object' && !!selectOverlay) {
			const {
				sector,
				representativeLocation,
			}: {
				sector: number;
				representativeLocation: { lat: string; lon: string };
			} = selectOverlay;
			const latitude = representativeLocation.lat;
			const longitude = representativeLocation.lon;
			if (selectOverlay['merchantCount']) {
				getMerchantList(latitude, longitude, sector).then(res => {
					if (res) {
						// setMerchantLists(res.content);
						setMerchantLists(dummyMerchantList.content);
					}
				});
			}
			if (selectOverlay['totalBookCount']) {
				getBookList(searchInput, latitude, longitude, sector).then(res => {
					if (res) {
						// setBookLists(res.content);
						setBookLists(bookListsDummy);
					}
				});
			}
		} else {
			setMerchantLists([]);
			setBookLists([]);
		}
	}, [selectOverlay]);

	return (
		<Container>
			<FlexBox>
				<Search
					searchInput={searchInput}
					setSearchInput={setSearchInput}
					setReset={setReset}
					current={current}
					setMerchantSector={setMerchantSector}
					setBookSector={setBookSector}
					setMerchantLists={setMerchantLists}
					setBookLists={setBookLists}
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
				merchantSector={merchantSector}
				setMerchantSector={setMerchantSector}
				merchantLists={merchantLists}
				setMerchantLists={setMerchantLists}
				setBookSector={setBookSector}
				bookSector={bookSector}
				bookLists={bookLists}
				setZoomLevel={setZoomLevel}
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
