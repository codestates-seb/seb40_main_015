import { useState } from 'react';

function useGeoLocation() {
	const [current, setCurrent] = useState<any>();
	const handleCurrentLocationMove = () => {
		let lat = 0;
		let lon = 0;
		var options = {
			enableHighAccuracy: true,
		};
		navigator.geolocation.getCurrentPosition(
			position => {
				lat = position.coords.latitude; // 위도
				lon = position.coords.longitude; // 경도
				setCurrent({ La: lon, Ma: lat });
			},
			null,
			options,
		);
	};

	return [current, setCurrent, handleCurrentLocationMove];
}

export default useGeoLocation;
