import { useEffect, useState } from 'react';

function useGeoLocation() {
	const [current, setCurrent] = useState<any>({
		La: 37.39277543968578,
		Ma: 126.93629486796846,
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
				setCurrent({ La: lon, Ma: lat });
			},
			null,
			options,
		);
	};

	useEffect(() => {
		let lat = 0;
		let lon = 0;
		let options = {
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
	}, []);

	return [current, setCurrent, handleCurrentLocationMove];
}

export default useGeoLocation;
