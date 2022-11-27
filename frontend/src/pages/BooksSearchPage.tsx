import { useEffect, useState } from 'react';
import styled from 'styled-components';
import { TbCurrentLocation } from 'react-icons/tb';
import Search from '../components/Map/Search';
import {
	getBookListQuery,
	getMerchantListQuery,
	getTotalBookQuery,
	getTotalMerchantQuery,
} from '../api/map';
import useWindowSize from '../hooks/useWindowSize';
import useGeoLocation from '../hooks/useGeoLocation';
import KakaoMap from '../components/Map/KakaoMap';
import { useQuery } from '@tanstack/react-query';

interface MerchantSectorProps {
	merchantCount: number;
	totalBookCount?: number;
	sector: number;
	location: {
		latitude: number;
		longitude: number;
	};
}

interface selectOverlayProps {
	merchantCount?: number;
	bookCount?: number;
	sector: number;
	location: {
		latitude: number;
		longitude: number;
	};
}

const BooksSearchPage = () => {
	const [current, setCurrent, handleCurrentLocationMove] = useGeoLocation();
	const [centerCoord, setCenterCoord] = useGeoLocation();
	const [searchInput, setSearchInput] = useState('');
	const [selectOverlay, setSelectOverlay] = useState<selectOverlayProps>();
	const [merchantSector, setMerchantSector] = useState<MerchantSectorProps[]>(
		[],
	);
	const [merchantLists, setMerchantLists] = useState<any>([]);
	const [bookSector, setBookSector] = useState([]);
	const [bookLists, setBookLists] = useState<any>([]);
	const [zoomLevel, setZoomLevel] = useState(5);
	const size = useWindowSize(zoomLevel);

	const { data: merchantSectorQuery, refetch: merchantCurrentRefetch } =
		useQuery({
			queryKey: ['merchantSectorByCurrent'],
			queryFn: () =>
				getTotalMerchantQuery(
					centerCoord.lat ? centerCoord.lat : current.lat,
					centerCoord.lat ? centerCoord.lon : current.lon,
					size.width,
					size.height,
					zoomLevel < 3 ? 3 : zoomLevel,
				),
			onSuccess: () => {
				if (merchantSectorQuery !== undefined && !searchInput) {
					setMerchantSector(merchantSectorQuery);
				} else {
					setMerchantSector([]);
				}
			},
			refetchOnWindowFocus: false,
			// enabled: !!centerCoord.lat || !!current.lat,
		});

	const { data: merchantListQuery, refetch: merchantListRefetch } = useQuery({
		queryKey: ['merchantListMap'],
		queryFn: () => {
			const sector = selectOverlay ? selectOverlay?.sector : 0;
			return getMerchantListQuery(
				centerCoord.lat ? centerCoord.lat : current.lat,
				centerCoord.lat ? centerCoord.lon : current.lon,
				sector,
				zoomLevel < 3 ? 3 : zoomLevel,
				size.width,
				size.height,
			);
		},
		onSuccess: () => {
			if (merchantListQuery !== undefined && !searchInput) {
				setMerchantLists(merchantListQuery.content);
			} else {
				setMerchantLists([]);
			}
		},
		refetchOnWindowFocus: false,
		// enabled: !!searchInput,
	});

	const { data: bookSectorQuery, refetch: bookCurrentRefetch } = useQuery({
		queryKey: ['bookSectorByCurrent'],
		queryFn: () =>
			getTotalBookQuery(
				searchInput,
				centerCoord.lat ? centerCoord.lat : current.lat,
				centerCoord.lat ? centerCoord.lon : current.lon,
				size.width,
				size.height,
				zoomLevel < 3 ? 3 : zoomLevel,
			),
		onSuccess: () => {
			if (bookSectorQuery !== undefined && searchInput) {
				setBookSector(bookSectorQuery);
			} else {
				setBookSector([]);
			}
		},
		refetchOnWindowFocus: false,
		// enabled: !!centerCoord.lat || !!current.lat,
	});

	const { data: bookListQuery, refetch: bookListRefetch } = useQuery({
		queryKey: ['bookListMap'],
		queryFn: () => {
			const sector = selectOverlay ? selectOverlay?.sector : 0;
			return getBookListQuery(
				searchInput,
				centerCoord.lat ? centerCoord.lat : current.lat,
				centerCoord.lat ? centerCoord.lon : current.lon,
				sector,
				zoomLevel < 3 ? 3 : zoomLevel,
				size.width,
				size.height,
			);
		},
		onSuccess: () => {
			if (bookListQuery !== undefined && searchInput) {
				setBookLists(bookListQuery.content);
			} else {
				setBookLists([]);
			}
		},
		refetchOnWindowFocus: false,
		// enabled: !!searchInput,
	});

	useEffect(() => {
		if (typeof selectOverlay === 'object' && !!selectOverlay) {
			if (selectOverlay['merchantCount']) {
				merchantListRefetch();
			}
			if (selectOverlay['bookCount']) {
				bookListRefetch();
			}
		} else {
			setMerchantLists([]);
			setBookLists([]);
		}
	}, [selectOverlay]);

	console.log('current', current, 'center', centerCoord);

	return (
		<Container>
			<FlexBox>
				<Search
					searchInput={searchInput}
					setSearchInput={setSearchInput}
					current={current}
					setMerchantSector={setMerchantSector}
					setBookSector={setBookSector}
					setMerchantLists={setMerchantLists}
					setBookLists={setBookLists}
					zoomLevel={zoomLevel}
					size={size}
					merchantCurrentRefetch={merchantCurrentRefetch}
					bookCurrentRefetch={bookCurrentRefetch}
				/>
				<TbCurrentLocation
					className="location"
					size={40}
					onClick={handleCurrentLocationMove}
				/>
			</FlexBox>
			<KakaoMap
				current={current}
				setCurrent={setCurrent}
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
				centerCoord={centerCoord}
				setCenterCoord={setCenterCoord}
				merchantCurrentRefetch={merchantCurrentRefetch}
				bookCurrentRefetch={bookCurrentRefetch}
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
