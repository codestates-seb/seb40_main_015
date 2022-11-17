import React, { useEffect, useState, useCallback } from 'react';
import styled from 'styled-components';
import { TbCurrentLocation } from 'react-icons/tb';
import { HiSearch } from 'react-icons/hi';

declare global {
	interface Window {
		kakao: any;
	}
}
const { kakao } = window;

const data = [
	{
		merchantCount: 3,
		sector: 1,
		representativeLocation: {
			lat: '37.39277543968578',
			lon: '126.93629486796846',
		},
	},
	{
		merchantCount: 1,
		sector: 2,
		representativeLocation: {
			lat: '37.39271348204709',
			lon: '126.93503012423825',
		},
	},
	{
		merchantCount: 3,
		sector: 3,
		representativeLocation: {
			lat: '37.39356427430288',
			lon: '126.9354449705146',
		},
	},
	{
		merchantCount: 4,
		sector: 4,
		representativeLocation: {
			lat: '37.39198970222246',
			lon: '126.93620970826674',
		},
	},
	{
		merchantCount: 9,
		sector: 5,
		representativeLocation: {
			lat: '37.49511841048171',
			lon: '126.92184207141253',
		},
	},
	{
		merchantCount: 7,
		sector: 6,
		representativeLocation: {
			lat: '37.49292835574315',
			lon: '126.91828010572407',
		},
	},
	{
		merchantCount: 0,
		sector: 7,
		representativeLocation: {
			lat: '37.49189717824911',
			lon: '126.92301238656695',
		},
	},
	{
		merchantCount: 4,
		sector: 8,
		representativeLocation: {
			lat: '37.49049786390108',
			lon: '126.92156645934426',
		},
	},
	{
		merchantCount: 5,
		sector: 9,
		representativeLocation: {
			lat: '37.492893955476916',
			lon: '126.92068646170932',
		},
	},
];

const BooksSearchPage = () => {
	const [current, setCurrent] = useState<any>();
	const [centerCoord, setCenterCoord] = useState();

	//여러개의 마커 표시하기
	const ShowMultipleMarkers = (map: any, data: any) => {
		let imageSrc =
			'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png';
		for (let i = 0; i < data.length; i++) {
			let imageSize = new kakao.maps.Size(24, 35);
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

			//커스텀 오버레이
			let customOverlay = new kakao.maps.CustomOverlay({
				map: map,
				clickable: true, // 커스텀 오버레이 클릭 시 지도에 이벤트를 전파하지 않도록 설정한다
				content: `<div style="width: 120px; height: 120px; display: flex; justify-content: center; align-items:center; background:#26795D; color: white; border-radius: 50%; opacity: 0.8; font-size: 2rem"; font-weight: 600;" onclick="handleSubmit()">
				${data[i].merchantCount}
				</div>`,
				position: new kakao.maps.LatLng(
					data[i].representativeLocation.lat,
					data[i].representativeLocation.lon,
				),
				// 커스텀 오버레이를 표시할 좌표
				xAnchor: 0.5, // 컨텐츠의 x 위치
				yAnchor: 0.7, // 컨텐츠의 y 위치
				onClick: { i },
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
		if (navigator.geolocation) {
			// GeoLocation을 이용해서 접속 위치를 얻어옵니다
			navigator.geolocation.getCurrentPosition(function (position) {
				let lat = position.coords.latitude; // 위도
				let lon = position.coords.longitude; // 경도

				let locPosition = new kakao.maps.LatLng(lat, lon), // 마커가 표시될 위치를 geolocation으로 얻어온 좌표로 생성합니다
					message = '<div style="padding:5px;">현재 위치입니다</div>'; // 인포윈도우에 표시될 내용입니다
				setCurrent(locPosition);

				// 마커와 인포윈도우를 표시합니다
				displayMarker(locPosition, message);
			});
		} else {
			// HTML5의 GeoLocation을 사용할 수 없을때 마커 표시 위치와 인포윈도우 내용을 설정합니다

			let locPosition = new kakao.maps.LatLng(37.498095, 127.02761),
				message = 'geolocation을 사용할수 없어요..';

			displayMarker(locPosition, message);
		}

		// // 지도에 마커와 인포윈도우를 표시하는 함수입니다
		function displayMarker(locPosition: any, message?: any) {
			// 마커를 생성합니다
			let marker = new kakao.maps.Marker({
				map: map,
				position: locPosition,
			});

			if (message) {
				let iwContent = message, // 인포윈도우에 표시할 내용입니다
					iwRemoveable = true;

				// 인포윈도우를 생성합니다
				let infowindow = new kakao.maps.InfoWindow({
					content: iwContent,
					removable: iwRemoveable,
				});

				// 인포윈도우를 마커위에 표시합니다
				infowindow.open(map, marker);
			}

			// 지도 중심좌표를 접속위치로 변경합니다
			map.setCenter(locPosition);
		}
		return () => {};
	}, []);

	// // 지도에 마커와 인포윈도우를 표시하는 함수입니다
	function displayMarker(locPosition: any, message?: any) {
		let mapContainer = document.getElementById('map'), // 지도를 표시할 div
			mapOption = {
				center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
				level: 2, // 지도의 확대 레벨
				// 두번 클릭시 확대 기능을 막습니다
				disableDoubleClickZoom: true,
			};

		let map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다
		// 마커를 생성합니다
		let marker = new kakao.maps.Marker({
			map: map,
			position: locPosition,
		});

		//확대 축소 기능을 막습니다
		function setZoomable(zoomable: any) {
			map.setZoomable(zoomable);
		}
		setZoomable(false);

		if (message) {
			let iwContent = message, // 인포윈도우에 표시할 내용입니다
				iwRemoveable = true;

			// 인포윈도우를 생성합니다
			let infowindow = new kakao.maps.InfoWindow({
				content: iwContent,
				removable: iwRemoveable,
			});

			// 인포윈도우를 마커위에 표시합니다
			infowindow.open(map, marker);
		}

		// 지도 중심좌표를 접속위치로 변경합니다
		map.setCenter(locPosition);
	}

	//버튼 클릭 시 현재 위치로 돌아갑니다(추후에 로딩 시간에 애니메이션 추가하기)
	const handleCurrentLocationMove = () => {
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
		if (current) {
			let locPosition = new kakao.maps.LatLng(current.Ma, current.La), // 마커가 표시될 위치를 geolocation으로 얻어온 좌표로 생성합니다
				message = '<div style="padding:5px;">현재 위치입니다</div>'; // 인포윈도우에 표시될 내용입니다
			// 마커와 인포윈도우를 표시합니다
			displayMarker(locPosition, message);
			navigator.geolocation.getCurrentPosition(function (position) {
				let lat = position.coords.latitude; // 위도
				let lon = position.coords.longitude; // 경도

				let locPosition = new kakao.maps.LatLng(lat, lon), // 마커가 표시될 위치를 geolocation으로 얻어온 좌표로 생성합니다
					message = '<div style="padding:5px;">현재 위치입니다</div>'; // 인포윈도우에 표시될 내용입니다
				if (locPosition.La !== current.La && locPosition.Ma !== current.Ma) {
					console.log(locPosition, current);
					console.log('다름');
					setCurrent(locPosition);
					// 마커와 인포윈도우를 표시합니다
					displayMarker(locPosition, message);
				}
			});
		} else {
			navigator.geolocation.getCurrentPosition(function (position) {
				let lat = position.coords.latitude; // 위도
				let lon = position.coords.longitude; // 경도

				let locPosition = new kakao.maps.LatLng(lat, lon), // 마커가 표시될 위치를 geolocation으로 얻어온 좌표로 생성합니다
					message = '<div style="padding:5px;">현재 위치입니다</div>'; // 인포윈도우에 표시될 내용입니다
				setCurrent(locPosition);
				// 마커와 인포윈도우를 표시합니다
				displayMarker(locPosition, message);
			});
		}
	};

	// 현재위치 변경시 주변 상인정보 다시 받아오기
	useEffect(() => {
		// centerCoord 변경될때마다 주변상인 정보 api 호출하기
		console.log('현재위치 변경됨 주변 상인정보 다시 요청');
	}, [centerCoord]);

	return (
		<Box>
			<FlexBox>
				<SearchBox>
					<HiSearch size={30} color="#a7a7a7" />
					<SearchInput placeholder="책 검색" />
				</SearchBox>
				<TbCurrentLocation
					className="location"
					size={40}
					onClick={() => {
						// onClickToggleModal();
						handleCurrentLocationMove();
					}}
				/>
				<div></div>
			</FlexBox>
			<Container id="map" />
		</Box>
	);
};

const Box = styled.div`
	width: 100vw;
	height: 93.5vh;
	position: absolute;
`;

const Container = styled.div`
	width: 100vw;
	height: 93.5vh;
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

const SearchBox = styled.div`
	width: 30rem;
	height: 3rem;
	display: flex;
	align-items: center;
	border: 1px solid white;
	border-radius: 5px;
	background-color: white;
	width: 90vw;
	padding: 5px;
	box-shadow: 2px 2px 2px 2px rgba(108, 122, 137, 0.2);
`;

const SearchInput = styled.input`
	border: none;
	width: 100%;
	outline: none;
	padding-left: 10px;
	font-size: ${props => props.theme.fontSizes.paragraph};
	// height: 5rem;
`;

export default BooksSearchPage;
