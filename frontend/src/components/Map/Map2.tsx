import { Dispatch, SetStateAction, useEffect, useState } from 'react';
import styled, { css, keyframes } from 'styled-components';
import { getTotalBook, getTotalMerchant } from '../../api/map';
import { Map, MapMarker } from 'react-kakao-maps-sdk';
import BookLists from './BookLists';
import MerchantLists from './MerchantLists';
import CustomOverlay from './CustomOverlay';

interface MarkerProps {
	merchantCount: number;
	totalBookCount?: number;
	sector: number;
	location: {
		latitude: string;
		longitude: string;
	};
}

interface MerchantSectorProps {
	merchantCount: number;
	totalBookCount?: number;
	sector: number;
	location: {
		latitude: string;
		longitude: string;
	};
}

interface BookMarkerProps {
	totalBookCount: number;
	sector: number;
	representativeLocation: {
		latitude: string;
		longitude: string;
	};
}

interface Map2Props {
	current: { La: number; Ma: number };
	setCurrent: Dispatch<SetStateAction<{ La: number; Ma: number }>>;
	reset: boolean;
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
}

const Map2 = (props: Map2Props) => {
	const {
		current,
		setCurrent,
		reset,
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
	} = props;
	const [level, setLevel] = useState(5);
	const [centerCoord, setCenterCoord] = useState({
		lat: 0,
		lon: 0,
	});

	useEffect(() => {
		if (level > 5) {
			setLevel(5);
		}
		setZoomLevel(level);
	}, [level]);

	useEffect(() => {
		// centerCoord 변경될때마다 주변상인 정보 api 호출하기
		if (!searchInput) {
			console.log('현재위치 변경됨 주변 상인정보 다시 요청');
			// setMerchantSector(data);
			getTotalMerchant(
				centerCoord.lat ? centerCoord.lat : current.Ma,
				centerCoord.lon ? centerCoord.lon : current.La,
				size.width,
				size.height,
				zoomLevel < 3 ? 3 : zoomLevel,
			).then(res => {
				console.log(res);
				setMerchantSector(res);
				setBookSector([]);
			});
		} else {
			console.log('현재위치 변경됨 주변 책정보 다시 요청');
			// setBookSector(bookCount);
			getTotalBook(
				searchInput,
				centerCoord.lat ? centerCoord.lat : current.Ma,
				centerCoord.lon ? centerCoord.lon : current.La,
				size.width,
				size.height,
				zoomLevel < 3 ? 3 : zoomLevel,
			).then(res => {
				console.log(res);
				setBookSector(res);
				setMerchantSector([]);
			});
		}
	}, [centerCoord, current]);

	return (
		<Map
			center={{
				lat: current?.Ma ? current.Ma : 33.4522346675632,
				lng: current?.La ? current.La : 126.57100321134753,
			}}
			isPanto={true}
			style={{ width: '100%', height: '100%', position: 'absolute' }}
			level={level}
			onZoomChanged={map => setLevel(map.getLevel())}
			onDragEnd={map =>
				setCenterCoord({
					lat: map.getCenter().getLat(),
					lon: map.getCenter().getLng(),
				})
			}>
			<MapMarker
				position={{
					lat: current?.Ma ? current.Ma : 33.4522346675632,
					lng: current?.La ? current.La : 126.57100321134753,
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
					merchantSector={merchantSector}
					selectOverlay={selectOverlay}
					setSelectOverlay={setSelectOverlay}
				/>
			)}
			{bookSector.length && (
				<CustomOverlay
					merchantSector={bookSector}
					selectOverlay={selectOverlay}
					setSelectOverlay={setSelectOverlay}
				/>
			)}
			<Search>
				{merchantLists.length > 0 && (
					<MerchantLists merchantList={merchantLists} />
				)}
				{bookLists.length > 0 && <BookLists bookLists={bookLists} />}
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

export default Map2;
