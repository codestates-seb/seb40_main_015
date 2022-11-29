import { useEffect, useState } from 'react';

function useGeoLocation() {
	const [current, setCurrent] = useState<any>({
		lat: 0,
		lon: 0,
	});
	const handleCurrentLocationMove = () => {
		let latitude = 0;
		let longitude = 0;
		var options = {
			enableHighAccuracy: true,
		};
		navigator.geolocation.getCurrentPosition(
			position => {
				latitude = position.coords.latitude; // 위도
				longitude = position.coords.longitude; // 경도
				setCurrent({ latitude, longitude });
			},
			null,
			options,
		);
	};

	useEffect(() => {
		handleCurrentLocationMove();
	}, []);

	return [current, setCurrent, handleCurrentLocationMove];
}

export default useGeoLocation;
