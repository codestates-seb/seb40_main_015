import { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { RootState } from '../redux/store';
import { change } from '../redux/slice/geoLocationSlice';
import getLocation from '../utils/getLocation';
import { useAppSelector } from '../redux/hooks';

function useGeoLocation(type?: string) {
	const dispatch = useDispatch();
	const location = useSelector((state: RootState) => {
		return state.persistedReducer.getLocation;
	});
	const { location: persistLocation } = useAppSelector(
		state => state.loginInfo,
	);

	const [current, setCurrent] = useState<any>({
		lat: persistLocation?.latitude ? persistLocation.latitude : location.lat,
		lon: persistLocation?.longitude ? persistLocation.longitude : location.lon,
	});
	const [loading, setLoading] = useState(false);

	const handleCurrentLocationMove = async () => {
		setLoading(true);
		let options = {
			enableHighAccuracy: true,
			maximumAge: 0,
		};

		let position: any = await getLocation(options);
		let lat = position.coords.latitude; // 위도
		let lon = position.coords.longitude; // 경도
		let result = { lat, lon };
		dispatch(change(result));
		setCurrent(result);
		setLoading(false);
	};

	const setCurrentLocation = (lat: number, lon: number) => {
		dispatch(change({ lat, lon }));
	};

	useEffect(() => {
		handleCurrentLocationMove();
	}, []);

	if (type === 'center') {
		return [location, setCurrentLocation];
	} else {
		return [current, setCurrent, handleCurrentLocationMove, loading];
	}
}

export default useGeoLocation;
