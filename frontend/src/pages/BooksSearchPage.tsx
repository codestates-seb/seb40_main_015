import React, { useEffect, useState } from 'react';
import styled from 'styled-components';

declare global {
	interface Window {
		kakao: any;
	}
}
const { kakao } = window;

const BooksSearchPage = () => {
	//지도를 띄우는 코드
	useEffect(() => {
		const container = document.getElementById('map');
		const options = {
			center: new window.kakao.maps.LatLng(
				37.496770701733176,
				127.0247033637749,
			),
			level: 3,
		};

		const map = new window.kakao.maps.Map(container, options);
		// setMap(kakaoMap);
		return () => {};
	}, []);

	return <Container id="map" />;
};

const Container = styled.div`
	width: 100vw;
	height: 93vh;
`;

export default BooksSearchPage;
