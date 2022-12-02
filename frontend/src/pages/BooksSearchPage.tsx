import { useEffect, useState } from 'react';
import styled, { css, keyframes } from 'styled-components';
import { TbCurrentLocation } from 'react-icons/tb';
import Search from '../components/Map/Search';
import useWindowSize from '../hooks/useWindowSize';
import useGeoLocation from '../hooks/useGeoLocation';
import KakaoMap from '../components/Map/KakaoMap';
import Animation from '../components/Loading/Animation';
import useMerchantList from '../api/hooks/map/useMerchantList';
import useMerchantSector from '../api/hooks/map/useMerchantSector';
import useBookList from '../api/hooks/map/useBookList';
import useBookSector from '../api/hooks/map/useBookSector';

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
	const [current, setCurrent, handleCurrentLocationMove, loading] =
		useGeoLocation();
	const [centerCoord, setCenterCoord] = useGeoLocation('center');
	const [searchInput, setSearchInput] = useState('');
	const [selectOverlay, setSelectOverlay] = useState<selectOverlayProps>();
	const [zoomLevel, setZoomLevel] = useState(5);
	const size = useWindowSize(zoomLevel);
	const [merchantSector, setMerchantSector, merchantCurrentRefetch] =
		useMerchantSector({ centerCoord, current, searchInput, zoomLevel, size });
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
	const [bookSector, setBookSector, bookCurrentRefetch] = useBookSector({
		centerCoord,
		current,
		searchInput,
		zoomLevel,
		size,
	});
	const [bookLists, setBookLists, bookListRef, bookListRefetch] = useBookList({
		centerCoord,
		current,
		selectOverlay,
		searchInput,
		zoomLevel,
		size,
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
						<IconBox onClick={handleCurrentLocationMove}>
							<LocationIcon size={40} loading={loading ? 1 : 0} />
						</IconBox>
					</FlexBox>
					<KakaoMap
						current={current}
						setSelectOverlay={setSelectOverlay}
						selectOverlay={selectOverlay}
						merchantSector={merchantSector}
						merchantLists={merchantLists}
						setMerchantLists={setMerchantLists}
						bookSector={bookSector}
						bookLists={bookLists}
						setBookLists={setBookLists}
						zoomLevel={zoomLevel}
						setZoomLevel={setZoomLevel}
						size={size}
						searchInput={searchInput}
						centerCoord={centerCoord}
						merchantCurrentRefetch={merchantCurrentRefetch}
						bookCurrentRefetch={bookCurrentRefetch}
						merchantListRefetch={merchantListRefetch}
						bookListRefetch={bookListRefetch}
						merchantListRef={merchantListRef}
						bookListRef={bookListRef}
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

const rotate = keyframes`

  100% {
  transform: rotate(360deg);
	}
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

	@media screen and (min-width: 800px) {
		width: 70%;
		top: 100px;
	}
`;

const IconBox = styled.div`
	margin-left: 1.125rem;
	border-radius: 5px;
	background-color: rgba(950, 950, 950, 0.8);
	z-index: 100;
	cursor: pointer;
`;

interface LocationIconProps {
	loading: number;
}

const LocationIcon = styled(TbCurrentLocation)<LocationIconProps>`
	color: #016241;
	padding: 5px;
	animation: ${props =>
		props.loading
			? css`
					${rotate} 1s linear infinite
			  `
			: ''};
	transform: ${props =>
		props.loading
			? css`
			50% 50%
		`
			: ''};
`;

export default BooksSearchPage;
