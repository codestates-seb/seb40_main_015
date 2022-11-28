import { useEffect, useState } from 'react';

function useGeoLocation() {
	const [current, setCurrent] = useState<any>({
		lat: 37.3827456,
		lon: 126.9264032,
	});
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
				setCurrent({ lat, lon });
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
