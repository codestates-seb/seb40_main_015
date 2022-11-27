import { Dispatch, SetStateAction, useEffect, useState } from 'react';
import styled, { css, keyframes } from 'styled-components';
import { Map, MapMarker } from 'react-kakao-maps-sdk';
import BookLists from './BookLists';
import MerchantLists from './MerchantLists';
import CustomOverlay from './CustomOverlay';
import CustomOverlayHover from './CustomOverlayHover';

interface MarkerProps {
	merchantCount: number;
	totalBookCount?: number;
	sector: number;
	location: {
		latitude: number;
		longitude: number;
	};
}

interface MerchantSectorProps {
	merchantCount: number;
	totalBookCount?: number;
	sector: number;
	location: {
		latitude: number;
		longitude: number;
	};
}

interface KakaoMapProps {
	current: { lat: number; lon: number };
	setCurrent: Dispatch<SetStateAction<{ lat: number; lon: number }>>;
	selectOverlay: any;
	setSelectOverlay: Dispatch<SetStateAction<any>>;
	merchantSector: MerchantSectorProps[];
	setMerchantSector: Dispatch<SetStateAction<MerchantSectorProps[]>>;
	merchantLists: MarkerProps[];
	setMerchantLists: Dispatch<SetStateAction<any>>;
	bookSector: MerchantSectorProps[];
	setBookSector: Dispatch<SetStateAction<any>>;
	bookLists: any;
	zoomLevel: number;
	setZoomLevel: Dispatch<SetStateAction<number>>;
	size: any;
	searchInput: string;
	centerCoord: {
		lat: number;
		lon: number;
	};
	setCenterCoord: Dispatch<
		SetStateAction<{
			lat: number;
			lon: number;
		}>
	>;
	merchantCurrentRefetch: any;
	bookCurrentRefetch: any;
}

const KakaoMap = (props: KakaoMapProps) => {
	const {
		current,
		setCurrent,
		selectOverlay,
		setSelectOverlay,
		merchantSector,
		setMerchantSector,
		merchantLists,
		setMerchantLists,
		bookSector,
		setBookSector,
		bookLists,
		zoomLevel,
		setZoomLevel,
		size,
		searchInput,
		centerCoord,
		setCenterCoord,
		merchantCurrentRefetch,
		bookCurrentRefetch,
	} = props;

	const [hoverList, setHoverLists] = useState({ latitude: 0, longitude: 0 });

	useEffect(() => {
		if (zoomLevel > 5) {
			setZoomLevel(5);
		}
	}, [zoomLevel]);

	// 	// centerCoord 변경될때마다 주변상인 정보 api 호출하기
	useEffect(() => {
		if (centerCoord.lat && centerCoord.lon) {
			if (!searchInput) {
				merchantCurrentRefetch();
			} else {
				bookCurrentRefetch();
			}
		}
	}, [centerCoord, zoomLevel, size]);

	// 	// current 변경될때마다 주변상인 정보 api 호출하기
	useEffect(() => {
		if (current.lat && current.lon) {
			if (!searchInput) {
				merchantCurrentRefetch();
			} else {
				bookCurrentRefetch();
			}
		}
	}, [current, zoomLevel, size]);

	return (
		<Map
			center={{
				lat: current?.lat ? current.lat : 33.4522346675632,
				lng: current?.lon ? current.lon : 126.57100321134753,
			}}
			isPanto={true}
			style={{ width: '100%', height: '100%', position: 'absolute' }}
			level={zoomLevel}
			onZoomChanged={map => setZoomLevel(map.getLevel())}
			onDragEnd={map =>
				setCenterCoord({
					lat: map.getCenter().getLat(),
					lon: map.getCenter().getLng(),
				})
			}>
			<MapMarker
				position={{
					lat: current?.lat ? current.lat : 33.4522346675632,
					lng: current?.lon ? current.lon : 126.57100321134753,
				}}
				image={{
					src: 'https://velog.velcdn.com/images/fejigu/post/ffa9fea3-b632-4d69-aac0-dc807ff55ea7/image.png', // 마커이미지의 주소입니다
					size: {
						width: 51,
						height: 55,
					}, // 마커이미지의 크기입니다
					options: {
						offset: {
							x: 16,
							y: 34,
						}, // 마커이미지의 옵션입니다. 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정합니다.
					},
				}}
			/>
			{merchantSector.length && (
				<CustomOverlay
					sector={merchantSector}
					selectOverlay={selectOverlay}
					setSelectOverlay={setSelectOverlay}
				/>
			)}
			{bookSector.length && (
				<CustomOverlay
					sector={bookSector}
					selectOverlay={selectOverlay}
					setSelectOverlay={setSelectOverlay}
				/>
			)}
			{hoverList && <CustomOverlayHover hoverList={hoverList} />}
			<Search>
				{merchantLists.length > 0 && (
					<MerchantLists
						merchantList={merchantLists}
						setHoverLists={setHoverLists}
					/>
				)}
				{bookLists.length > 0 && (
					<BookLists bookLists={bookLists} setHoverLists={setHoverLists} />
				)}
			</Search>
		</Map>
	);
};

const slideUp = keyframes`
	from {
    transform: translateY(300px);
	}
	to {
    transform: translateY(0px);
	}
	`;

const slideDown = keyframes`
		from {
      transform: translateY(0px);
		}
		to {
      transform: translateY(300px);
		}
    `;

interface SearchProps {
	mount?: boolean;
}

const Search = styled.div<SearchProps>`
	width: 100%;
	max-height: 220px;
	position: absolute;
	bottom: 60px;
	border-radius: 30px 30px 0px 0px;
	box-shadow: 0px -5px 10px -5px grey;
	overflow-y: hidden;
	z-index: 555;
	opacity: 0.9;
	animation: ${props =>
		props.mount
			? css`
					${slideUp} ease-in-out 0.6s
			  `
			: css`
					${slideDown} ease-in-out 0.6s
			  `};
`;

export default KakaoMap;
