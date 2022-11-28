import { useEffect, useMemo, useState } from 'react';
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
import { useInfiniteQuery, useQuery } from '@tanstack/react-query';
import Animation from '../components/Loading/Animation';
import { useInView } from 'react-intersection-observer';
import useMerchantList from '../components/Map/hooks/useMerchantList';

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
	// const [merchantLists, setMerchantLists] = useState<any>([]);
	const [bookSector, setBookSector] = useState([]);
	const [bookLists, setBookLists] = useState<any>([]);
	const [zoomLevel, setZoomLevel] = useState(5);
	const size = useWindowSize(zoomLevel);
	const [
		merchantLists,
		setMerchantLists,
		merchantListRef,
		merchantListRefetch,
	] = useMerchantList({
		centerCoord,
		current,
		selectOverlay,
		searchInput,
		zoomLevel,
		size,
	});

	// 무한스크롤
	// const [ref, inView] = useInView();

	// const {
	// 	fetchNextPage,
	// 	hasNextPage,
	// 	refetch: merchantListRefetch,
	// } = useInfiniteQuery({
	// 	queryKey: ['merchantListMap', centerCoord],
	// 	queryFn: ({ pageParam = undefined }) => {
	// 		const sector = selectOverlay ? selectOverlay?.sector : 0;
	// 		if (!!searchInput) {
	// 			return [];
	// 		}
	// 		return getMerchantListQuery(
	// 			centerCoord.lat ? centerCoord.lat : current.lat,
	// 			centerCoord.lon ? centerCoord.lon : current.lon,
	// 			sector,
	// 			zoomLevel < 3 ? 3 : zoomLevel,
	// 			size.width,
	// 			size.height,
	// 			pageParam,
	// 		);
	// 	},
	// 	getNextPageParam: lastPage => {
	// 		console.log(lastPage);
	// 		return lastPage.last
	// 			? undefined
	// 			: lastPage.content[lastPage.content.length - 1].merchantId;
	// 	},
	// 	onSuccess: data => {
	// 		if (selectOverlay?.merchantCount) {
	// 			setMerchantLists(data?.pages.flatMap(page => page.content));
	// 		}
	// 	},
	// 	retry: false,
	// 	refetchOnWindowFocus: false,
	// 	cacheTime: 0,
	// });

	// useEffect(() => {
	// 	if (inView && hasNextPage) fetchNextPage();
	// }, [inView]);

	console.log(centerCoord);
	const { refetch: merchantCurrentRefetch } = useQuery({
		queryKey: ['merchantSectorByCurrent', centerCoord],
		queryFn: () => {
			if (!!searchInput) {
				return [];
			}
			return getTotalMerchantQuery(
				centerCoord.lat ? centerCoord.lat : current.lat,
				centerCoord.lon ? centerCoord.lon : current.lon,
				size.width,
				size.height,
				zoomLevel < 3 ? 3 : zoomLevel,
			);
		},
		onSuccess: data => {
			setMerchantSector(data);
		},
		refetchOnWindowFocus: false,
		cacheTime: 0,
	});

	// const { refetch: merchantListRefetch } = useQuery({
	// 	queryKey: ['merchantListMap', centerCoord],
	// 	queryFn: () => {
	// 		const sector = selectOverlay ? selectOverlay?.sector : 0;
	// 		if (!!searchInput) {
	// 			return [];
	// 		}
	// 		return getMerchantListQuery(
	// 			centerCoord.lat ? centerCoord.lat : current.lat,
	// 			centerCoord.lon ? centerCoord.lon : current.lon,
	// 			sector,
	// 			zoomLevel < 3 ? 3 : zoomLevel,
	// 			size.width,
	// 			size.height,
	// 		);
	// 	},
	// 	onSuccess: data => {
	// 		if (selectOverlay?.merchantCount) {
	// 			setMerchantLists(data.content);
	// 		}
	// 	},
	// 	refetchOnWindowFocus: false,
	// 	cacheTime: 0,
	// });

	const { refetch: bookCurrentRefetch } = useQuery({
		queryKey: ['bookSectorByCurrent', centerCoord],
		queryFn: () => {
			if (!searchInput) {
				return [];
			}
			return getTotalBookQuery(
				searchInput,
				centerCoord.lat ? centerCoord.lat : current.lat,
				centerCoord.lon ? centerCoord.lon : current.lon,
				size.width,
				size.height,
				zoomLevel < 3 ? 3 : zoomLevel,
			);
		},
		onSuccess: data => {
			if (data !== undefined) {
				setBookSector(data);
			} else {
				setBookSector([]);
			}
		},
		refetchOnWindowFocus: false,
		cacheTime: 0,
	});

	const { refetch: bookListRefetch } = useQuery({
		queryKey: ['bookListMap', centerCoord],
		queryFn: () => {
			const sector = selectOverlay ? selectOverlay?.sector : 0;
			if (!searchInput) {
				return [];
			}
			return getBookListQuery(
				searchInput,
				centerCoord.lat ? centerCoord.lat : current.lat,
				centerCoord.lon ? centerCoord.lon : current.lon,
				sector,
				zoomLevel < 3 ? 3 : zoomLevel,
				size.width,
				size.height,
			);
		},
		onSuccess: data => {
			if (data !== undefined && searchInput) {
				setBookLists(data.content);
			} else {
				setBookLists([]);
			}
		},
		refetchOnWindowFocus: false,
		cacheTime: 0,
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

	return (
		<Container>
			{current.lat && current.lon ? (
				<>
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
						setBookLists={setBookLists}
						zoomLevel={zoomLevel}
						setZoomLevel={setZoomLevel}
						size={size}
						searchInput={searchInput}
						centerCoord={centerCoord}
						setCenterCoord={setCenterCoord}
						merchantCurrentRefetch={merchantCurrentRefetch}
						bookCurrentRefetch={bookCurrentRefetch}
						merchantListRefetch={merchantListRefetch}
						bookListRefetch={bookListRefetch}
						merchantListRef={merchantListRef}
					/>
				</>
			) : (
				<Animation text="현재 위치 확인중..." />
			)}
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
