import { useEffect, useState } from 'react';
import styled from 'styled-components';
import data from './dummy';

declare global {
	interface Window {
		kakao: any;
	}
}
const { kakao } = window;

const Map = () => {
	const [current, setCurrent] = useState<any>();
	const [centerCoord, setCenterCoord] = useState();

	//여러개의 마커 표시하기
	const ShowMultipleMarkers = (map: any, data: any) => {
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

			kakao.maps.event.addListener(marker, 'click', function () {
				alert('마커를 클릭했습니다!');
			});

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
			content.innerText = data[i].merchantCount;
			content.onclick = () => {
				console.log('클릭함');
				view = !view;
				console.log(view);
				content.style.backgroundColor = !view ? '#26795D' : '#124B38';
				if (view) {
					content.style.width = '200px';
					content.style.height = '100px';
					content.style.backgroundColor = 'white';
					content.style.opacity = '1';
					content.style.borderRadius = '0';
					content.style.fontSize = '1rem';
					content.style.color = 'black';
					content.style.display = 'flex';
					content.style.flexDirection = 'column';
					content.style.alignItems = 'space-between';
					content.style.justifyContent = 'center';
					content.style.borderRadius = '10px';
					content.style.border = '0.5px solid grey';
					content.innerText = data[i].merchantCount;
				} else {
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
					content.innerText = data[i].merchantCount;
				}
			};
			// if (view) {
			// 	// content.appendChild(overlay);
			// 	overlay.style.display = 'block';
			// } else {
			// 	overlay.style.display = 'none';
			// }

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
				// onClick: `console.log('click')`,
			});

			kakao.maps.event.addListener(customOverlay, 'click', function () {
				alert('오버레이를 클릭했습니다!');
			});
		}
	};

	//카카오맵을 띄웁니다
	useEffect(() => {
		let mapContainer = document.getElementById('map'), // 지도를 표시할 div
			mapOption = {
				center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
				level: 2, // 지도의 확대 레벨
				// 두번 클릭시 확대 기능을 막습니다
				disableDoubleClickZoom: true,
			};

		let map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다
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
			navigator.geolocation.getCurrentPosition(function (position) {
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
			});
		} else {
			navigator.geolocation.getCurrentPosition(function (position) {
				let lat = position.coords.latitude; // 위도
				let lon = position.coords.longitude; // 경도

				let locPosition = new kakao.maps.LatLng(lat, lon); // 마커가 표시될 위치를 geolocation으로 얻어온 좌표로 생성합니다
				setCurrent(locPosition);
				// 마커를 표시합니다
				displayMarker(locPosition);
			});
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
	}, []);

	//버튼 클릭 시 현재 위치로 돌아갑니다(추후에 로딩 시간에 애니메이션 추가하기)
	// const handleCurrentLocationMove = () => {
	// 	// let mapContainer = document.getElementById('map'), // 지도를 표시할 div
	// 	// 	mapOption = {
	// 	// 		center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
	// 	// 		level: 2, // 지도의 확대 레벨
	// 	// 		// 두번 클릭시 확대 기능을 막습니다
	// 	// 		disableDoubleClickZoom: true,
	// 	// 	};

	// 	// let map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다
	// 	if (data) {
	// 		ShowMultipleMarkers(map, data);
	// 	}
	// 	if (current) {
	// 		let locPosition = new kakao.maps.LatLng(current.Ma, current.La), // 마커가 표시될 위치를 geolocation으로 얻어온 좌표로 생성합니다
	// 			message = '<div style="padding:5px;">현재 위치입니다</div>'; // 인포윈도우에 표시될 내용입니다
	// 		// 마커와 인포윈도우를 표시합니다
	// 		displayMarker(locPosition);
	// 		navigator.geolocation.getCurrentPosition(function (position) {
	// 			let lat = position.coords.latitude; // 위도
	// 			let lon = position.coords.longitude; // 경도

	// 			let locPosition = new kakao.maps.LatLng(lat, lon), // 마커가 표시될 위치를 geolocation으로 얻어온 좌표로 생성합니다
	// 				message = '<div style="padding:5px;">현재 위치입니다</div>'; // 인포윈도우에 표시될 내용입니다
	// 			if (locPosition.La !== current.La && locPosition.Ma !== current.Ma) {
	// 				console.log(locPosition, current);
	// 				console.log('다름');
	// 				setCurrent(locPosition);
	// 				// 마커와 인포윈도우를 표시합니다
	// 				displayMarker(locPosition);
	// 			}
	// 		});
	// 	} else {
	// 		navigator.geolocation.getCurrentPosition(function (position) {
	// 			let lat = position.coords.latitude; // 위도
	// 			let lon = position.coords.longitude; // 경도

	// 			let locPosition = new kakao.maps.LatLng(lat, lon), // 마커가 표시될 위치를 geolocation으로 얻어온 좌표로 생성합니다
	// 				message = '<div style="padding:5px;">현재 위치입니다</div>'; // 인포윈도우에 표시될 내용입니다
	// 			setCurrent(locPosition);
	// 			// 마커와 인포윈도우를 표시합니다
	// 			displayMarker(locPosition, message);
	// 		});
	// 	}
	// };

	// // // 지도에 마커와 인포윈도우를 표시하는 함수입니다
	// function displayMarker(locPosition: any, message?: any) {
	// 	let mapContainer = document.getElementById('map'), // 지도를 표시할 div
	// 		mapOption = {
	// 			center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
	// 			level: 2, // 지도의 확대 레벨
	// 			// 두번 클릭시 확대 기능을 막습니다
	// 			disableDoubleClickZoom: true,
	// 		};

	// 	let map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다
	// 	// 마커를 생성합니다
	// 	let marker = new kakao.maps.Marker({
	// 		map: map,
	// 		position: locPosition,
	// 	});

	// 	//확대 축소 기능을 막습니다
	// 	function setZoomable(zoomable: any) {
	// 		map.setZoomable(zoomable);
	// 	}
	// 	setZoomable(false);

	// 	if (message) {
	// 		let iwContent = message, // 인포윈도우에 표시할 내용입니다
	// 			iwRemoveable = true;

	// 		// 인포윈도우를 생성합니다
	// 		let infowindow = new kakao.maps.InfoWindow({
	// 			content: iwContent,
	// 			removable: iwRemoveable,
	// 		});

	// 		// 인포윈도우를 마커위에 표시합니다
	// 		infowindow.open(map, marker);
	// 	}

	// 	// 지도 중심좌표를 접속위치로 변경합니다
	// 	map.setCenter(locPosition);
	// }

	// 현재위치 변경시 주변 상인정보 다시 받아오기
	useEffect(() => {
		// centerCoord 변경될때마다 주변상인 정보 api 호출하기
		console.log('현재위치 변경됨 주변 상인정보 다시 요청');
	}, [centerCoord]);
	return <Container id="map" />;
};

const Container = styled.div`
	width: 100vw;
	height: 100%;
`;

export default Map;
