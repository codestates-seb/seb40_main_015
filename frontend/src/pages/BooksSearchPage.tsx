import React, { useEffect, useState } from 'react';
import styled from 'styled-components';
import { TbCurrentLocation } from 'react-icons/tb';
import { HiSearch } from 'react-icons/hi';
import { redirect } from 'react-router-dom';

declare global {
	interface Window {
		kakao: any;
	}
}
const { kakao } = window;

const data = [
	{
		totalBookCount: 3,
		sector: 1,
		representativeLocation: {
			lat: '37.15163546132',
			lon: '129.2132451321',
		},
	},
	{
		totalBookCount: 241,
		sector: 2,
		representativeLocation: {
			lat: '37.15163546132',
			lon: '125.2132451321',
		},
	},
	{
		totalBookCount: 412431,
		sector: 3,
		representativeLocation: {
			lat: '57.15163546132',
			lon: '128.2132451321',
		},
	},
	{
		totalBookCount: 34636345345,
		sector: 4,
		representativeLocation: {
			lat: '67.15163546132',
			lon: '128.2132451321',
		},
	},
	{
		totalBookCount: 111,
		sector: 5,
		representativeLocation: {
			lat: '17.15163546132',
			lon: '128.2132451321',
		},
	},
	{
		totalBookCount: 544,
		sector: 6,
		representativeLocation: {
			lat: '27.15163546132',
			lon: '128.2132451321',
		},
	},
	{
		totalBookCount: 0,
		sector: 7,
		representativeLocation: {
			lat: '26.15163546132',
			lon: '128.2132451321',
		},
	},
	{
		totalBookCount: 4,
		sector: 8,
		representativeLocation: {
			lat: '17.15163546132',
			lon: '128.2132451321',
		},
	},
	{
		totalBookCount: 5254234,
		sector: 9,
		representativeLocation: {
			lat: '37.15163546132',
			lon: '168.2132451321',
		},
	},
];

const BooksSearchPage = () => {
	//카카오맵을 띄웁니다
	useEffect(() => {
		var mapContainer = document.getElementById('map'), // 지도를 표시할 div
			mapOption = {
				center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
				level: 3, // 지도의 확대 레벨
				// 두번 클릭시 확대 기능을 막습니다
				disableDoubleClickZoom: true,
			};

		var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다

		// lodash debounce 사용하기(메모)
		// 지도 드래깅 이벤트를 등록한다 (드래그 시작 : dragstart, 드래그 종료 : dragend)
		kakao.maps.event.addListener(map, 'drag', function () {
			var message =
				'지도를 드래그 하고 있습니다. ' +
				'지도의 중심 좌표는 ' +
				map.getCenter().toString() +
				' 입니다.';
		});

		//확대 축소 기능을 막습니다
		function setZoomable(zoomable: any) {
			map.setZoomable(zoomable);
		}
		setZoomable(false);

		// HTML5의 geolocation으로 사용할 수 있는지 확인합니다
		if (navigator.geolocation) {
			// GeoLocation을 이용해서 접속 위치를 얻어옵니다
			navigator.geolocation.getCurrentPosition(function (position) {
				var lat = position.coords.latitude, // 위도
					lon = position.coords.longitude; // 경도

				var locPosition = new kakao.maps.LatLng(lat, lon), // 마커가 표시될 위치를 geolocation으로 얻어온 좌표로 생성합니다
					message = '<div style="padding:5px;">현재 위치입니다</div>'; // 인포윈도우에 표시될 내용입니다

				// 마커와 인포윈도우를 표시합니다
				displayMarker(locPosition, message);
			});
		} else {
			// HTML5의 GeoLocation을 사용할 수 없을때 마커 표시 위치와 인포윈도우 내용을 설정합니다

			var locPosition = new kakao.maps.LatLng(37.498095, 127.02761),
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

	// const handleLocationChange = () => {
	// 	navigator.geolocation.getCurrentPosition(function (position) {
	// 		var lat = position.coords.latitude, // 위도
	// 			lon = position.coords.longitude; // 경도

	// 		var locPosition = new kakao.maps.LatLng(lat, lon); // 마커가 표시될 위치를 geolocation으로 얻어온 좌표로 생성합니다
	// 	});
	// };

	return (
		<Box>
			<FlexBox>
				<SearchBox>
					<HiSearch size={30} color="#a7a7a7" />
					<SearchInput placeholder="책 검색" />
				</SearchBox>
				<TbCurrentLocation className="location" size={40} />
			</FlexBox>
			<Container id="map" />
			<div
				style={{
					position: 'relative',
					// left: '74rem',
					bottom: '500px',
					zIndex: '999',
					width: '3rem',
					height: '3rem',
					display: 'flex',
					justifyContent: 'center',
					alignItems: 'center',
					backgroundColor: '#Fbfbfb',
				}}></div>
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
