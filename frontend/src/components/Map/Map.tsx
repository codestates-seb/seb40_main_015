import { useEffect, useState, useRef, Dispatch, SetStateAction } from 'react';
import styled, { keyframes } from 'styled-components';
import data from './dummy';

const MoveLists = keyframes`
0% {
	opacity: 0;
	transform: translateY(220px);
	bottom: -20px;
}

80% {
	opacity: 3;
	transform: none;
	bottom: 230px;
	max-height: 230px;
}

100% {
	opacity: 3;
	transform: none;
	bottom: 220px;
	max-height: 220px;
}
`;

declare global {
	interface Window {
		kakao: any;
	}
}

interface MarkerProps {
	merchantCount: number;
	sector: number;
	representativeLocation: {
		lat: string;
		lon: string;
	};
}

interface MapProps {
	current: { La: number; Ma: number };
	setCurrent: Dispatch<SetStateAction<{ La: number; Ma: number }>>;
	reset: boolean;
}

const { kakao } = window;
const Map = (props: MapProps) => {
	const { current, setCurrent, reset } = props;
	const [centerCoord, setCenterCoord] = useState();
	const [listItem, setListItem] = useState([1, 2, 3, 4, 5, 6]);
	const [A, setA] = useState<any>();
	console.log(A);

	let mapContainer = useRef(null); // 지도를 표시할 div
	let mapOption = {
		center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
		level: 2, // 지도의 확대 레벨
		// 두번 클릭시 확대 기능을 막습니다
		disableDoubleClickZoom: true,
	};

	//여러개의 마커 표시하기
	const ShowMultipleMarkers = (map: any, data: MarkerProps[]) => {
		let imageSrc =
			'https://velog.velcdn.com/images/fejigu/post/3917d7b1-130c-4bc8-88df-b665386adbdd/image.png';
		for (let i = 0; i < data.length; i++) {
			let view = false;
			let imageSize = new kakao.maps.Size(30, 35);
			let markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);
			let marker = new kakao.maps.Marker({
				map: map,
				position: new kakao.maps.LatLng(
					data[i].representativeLocation.lat,
					data[i].representativeLocation.lon,
				),
				title: data[i].merchantCount,
				image: markerImage,
			});
			if (reset) {
				marker.setMap(null);
			}

			kakao.maps.event.addListener(marker, 'click', function () {
				alert('마커를 클릭했습니다!');
			});

			// let list = document.createElement('div');
			// list.style.width = '100%';
			// list.style.height = '30%';
			// list.style.backgroundColor = 'grey';

			//커스텀 오버레이
			//커스텀 오버레이 클릭 이벤트
			let content = document.createElement('div');
			content.style.width = '100px';
			content.style.height = '100px';
			content.style.backgroundColor = '#26795D';
			content.style.opacity = '0.8';
			content.style.borderRadius = '1000px';
			content.style.fontSize = '3rem';
			content.style.color = 'white';
			content.style.display = 'flex';
			content.style.alignItems = 'center';
			content.style.justifyContent = 'center';
			content.innerText = String(data[i].merchantCount);
			content.onclick = () => {
				console.log('click');
				view = !view;
				console.log(view);
				setA(data[i]);
				//클릭시 색상 변경 일부만 안 되는 중
				content.style.backgroundColor =
					data[i].sector === A?.sector ? '#26795D' : '#124B38';
				// content.style.backgroundColor = !view ? '#124B38' : '#26795D';
			};

			let customOverlay = new kakao.maps.CustomOverlay({
				map: map,
				clickable: true, // 커스텀 오버레이 클릭 시 지도에 이벤트를 전파하지 않도록 설정한다
				content: content,
				position: new kakao.maps.LatLng(
					data[i].representativeLocation.lat,
					data[i].representativeLocation.lon,
				),
				// 커스텀 오버레이를 표시할 좌표
				xAnchor: 0.5, // 컨텐츠의 x 위치
				yAnchor: 0.7, // 컨텐츠의 y 위치
			});
			if (reset) {
				customOverlay.setMap(null);
			}
			kakao.maps.event.addListener(customOverlay, 'click', function () {
				alert('오버레이를 클릭했습니다!');
			});
		}
	};

	//카카오맵을 띄웁니다
	useEffect(() => {
		let map = new kakao.maps.Map(mapContainer.current, mapOption); // 지도를 생성합니다
		if (data) {
			ShowMultipleMarkers(map, data);
		}

		// 지도 드래깅 이벤트를 등록한다 (드래그 시작 : dragstart, 드래그 종료 : dragend)
		kakao.maps.event.addListener(map, 'dragend', function () {
			let centerCoord = map.getCenter();
			setCenterCoord(centerCoord);
			let message =
				'지도를 드래그 하고 있습니다. ' +
				'지도의 중심 좌표는 ' +
				centerCoord.toString() +
				' 입니다.';
		});

		// map.setCenter(current);
		// 확대 축소 기능을 막습니다
		function setZoomable(zoomable: any) {
			map.setZoomable(zoomable);
		}
		setZoomable(false);

		// HTML5의 geolocation으로 사용할 수 있는지 확인합니다
		if (data) {
			ShowMultipleMarkers(map, data);
		}
		if (current) {
			let locPosition = new kakao.maps.LatLng(current.Ma, current.La); // 마커가 표시될 위치를 geolocation으로 얻어온 좌표로 생성합니다
			// 마커를 표시합니다
			displayMarker(locPosition);
			let options = {
				enableHighAccuracy: false,
			};
			navigator.geolocation.getCurrentPosition(
				function (position) {
					let lat = position.coords.latitude; // 위도
					let lon = position.coords.longitude; // 경도

					let locPosition = new kakao.maps.LatLng(lat, lon); // 마커가 표시될 위치를 geolocation으로 얻어온 좌표로 생성합니다

					if (locPosition.La !== current.La && locPosition.Ma !== current.Ma) {
						console.log(locPosition, current);
						console.log('다름');
						setCurrent(locPosition);
						// 마커를 표시합니다
						displayMarker(locPosition);
					}
				},
				null,
				options,
			);
		} else {
			let options = {
				enableHighAccuracy: false,
			};
			navigator.geolocation.getCurrentPosition(
				function (position) {
					let lat = position.coords.latitude; // 위도
					let lon = position.coords.longitude; // 경도

					let locPosition = new kakao.maps.LatLng(lat, lon); // 마커가 표시될 위치를 geolocation으로 얻어온 좌표로 생성합니다
					setCurrent(locPosition);
					// 마커를 표시합니다
					displayMarker(locPosition);
				},
				null,
				options,
			);
		}
		// // 지도에 마커와 인포윈도우를 표시하는 함수입니다
		function displayMarker(locPosition: any) {
			// 마커를 생성합니다

			let icon = new kakao.maps.MarkerImage(
				'https://velog.velcdn.com/images/fejigu/post/ffa9fea3-b632-4d69-aac0-dc807ff55ea7/image.png',
				new kakao.maps.Size(51, 55),
				{
					offset: new kakao.maps.Point(16, 34),
					alt: '현재 위치 마커',
					shape: 'poly',
					coords: '1,20,1,9,5,2,10,0,21,0,27,3,30,9,30,20,17,33,14,33',
				},
			);
			let marker = new kakao.maps.Marker({
				map: map,
				position: locPosition,
				image: icon,
			});

			// 지도 중심좌표를 접속위치로 변경합니다
			map.setCenter(locPosition);
		}
	}, [current, reset]);

	useEffect(() => {
		// centerCoord 변경될때마다 주변상인 정보 api 호출하기
		console.log('현재위치 변경됨 주변 상인정보 다시 요청');
		console.log(centerCoord);
	}, [centerCoord]);

	return (
		<div style={{ width: '100vw', height: '100%' }}>
			<Container id="map" ref={mapContainer} />
			<Search>
				{listItem?.map((item, i) => (
					<List key={i}>
						<div className="metchant">상인명 : </div>
						<div className="book"> 상헌북스</div>
					</List>
				))}
			</Search>
		</div>
	);
};

const Container = styled.div`
	width: 100%;
	height: 100%;
`;

const Search = styled.div`
	border-radius: 30px 30px 0px 0px;
	box-shadow: 20px 20px 20px 20px grey;
	max-height: 220px;
	overflow-y: scroll;
	position: relative;
	bottom: 220px;
	z-index: 1000;
	opacity: 0.9;
	transform: translateY(px);
	animation: ${MoveLists} 1s;
`;

const List = styled.div`
	padding-top: 30px;
	padding-bottom: 30px;
	display: flex;
	align-items: center;
	justify-content: center;
	background-color: white;
	border-bottom: 0.5px solid rgb(196, 182, 186);
`;

export default Map;