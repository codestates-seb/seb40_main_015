import { useEffect, useState } from 'react';
import styled from 'styled-components';
import { TbCurrentLocation } from 'react-icons/tb';
import Search from '../components/Map/Search';
import Map from '../components/Map/Map';
import { getBookList, getMerchantList, getTotalBook } from '../api/map';
import useWindowSize from '../hooks/useWindowSize';
import useGeoLocation from '../hooks/useGeoLocation';
import Map2 from '../components/Map/Map2';

interface MerchantSectorProps {
	merchantCount: number;
	totalBookCount?: number;
	sector: number;
	location: {
		latitude: string;
		longitude: string;
	};
}

const BooksSearchPage = () => {
	const [current, setCurrent, handleCurrentLocationMove] = useGeoLocation();
	const [searchInput, setSearchInput] = useState('');
	const [reset, setReset] = useState(false);
	const [selectOverlay, setSelectOverlay] = useState(null);
	const [merchantSector, setMerchantSector] = useState<MerchantSectorProps[]>(
		[],
	);
	const [merchantLists, setMerchantLists] = useState<any>([]);
	const [bookSector, setBookSector] = useState([]);
	const [bookLists, setBookLists] = useState<any>([]);
	const [zoomLevel, setZoomLevel] = useState(5);
	const size = useWindowSize(zoomLevel);
	console.log(zoomLevel);
	// console.log(selectOverlay);
	useEffect(() => {
		if (typeof selectOverlay === 'object' && !!selectOverlay) {
			const {
				sector,
				location,
			}: {
				sector: number;
				location: { latitude: number; longitude: number };
			} = selectOverlay;
			const latitude = location.latitude;
			const longitude = location.longitude;
			if (selectOverlay['merchantCount']) {
				getMerchantList(
					latitude,
					longitude,
					sector,
					zoomLevel < 3 ? 3 : zoomLevel,
					size.width,
					size.height,
				).then(res => {
					if (res) {
						console.log(res);
						setMerchantLists(res.content);
						// setMerchantLists(dummyMerchantList.content);
					}
				});
			}
			if (selectOverlay['bookCount']) {
				getBookList(
					searchInput,
					latitude,
					longitude,
					sector,
					zoomLevel < 3 ? 3 : zoomLevel,
					size.width,
					size.height,
				).then(res => {
					if (res) {
						console.log(res);
						setBookLists(res.content);
						// setBookLists(bookListsDummy);
					}
				});
			}
		} else {
			setMerchantLists([]);
			setBookLists([]);
		}
	}, [selectOverlay]);

	console.log(!searchInput);

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
					zoomLevel={zoomLevel}
					size={size}
				/>
				<TbCurrentLocation
					className="location"
					size={40}
					onClick={handleCurrentLocationMove}
				/>
			</FlexBox>
			<Map2
				current={current}
				setCurrent={setCurrent}
				reset={reset}
				setSelectOverlay={setSelectOverlay}
				selectOverlay={selectOverlay}
				merchantSector={merchantSector}
				setMerchantSector={setMerchantSector}
				merchantLists={merchantLists}
				setMerchantLists={setMerchantLists}
				setBookSector={setBookSector}
				bookSector={bookSector}
				bookLists={bookLists}
				zoomLevel={zoomLevel}
				setZoomLevel={setZoomLevel}
				size={size}
				searchInput={searchInput}
			/>
		</Container>
	);
};

const Container = styled.div`
	width: 100%;
	height: 100%;
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
