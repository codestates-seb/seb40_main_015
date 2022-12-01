import { useQuery } from '@tanstack/react-query';
import { useState } from 'react';
import { useAppDispatch } from '../../../redux/hooks';
import notify from '../../../utils/notify';
import { getTotalMerchantQuery } from '../../map';

interface MerchantSectorProps {
	merchantCount: number;
	totalBookCount?: number;
	sector: number;
	location: {
		latitude: number;
		longitude: number;
	};
}

interface IProps {
	centerCoord: { lat: number; lon: number };
	current: { lat: number; lon: number };
	searchInput: string;
	zoomLevel: number;
	size: { width: number; height: number };
}

const useMerchantSector = (props: IProps) => {
	const { centerCoord, current, searchInput, zoomLevel, size } = props;
	const [merchantSector, setMerchantSector] = useState<any>([]);
	const dispatch = useAppDispatch();

	const { refetch: merchantCurrentRefetch } = useQuery({
		queryKey: ['merchantSectorByCurrent', centerCoord],
		queryFn: () => {
			if (!!searchInput) {
				return [];
			}
			let reqZoomLevel = 8 - zoomLevel;
			return getTotalMerchantQuery(
				centerCoord.lat ? centerCoord.lat : current.lat,
				centerCoord.lon ? centerCoord.lon : current.lon,
				size.width,
				size.height,
				reqZoomLevel,
			);
		},
		onSuccess: data => {
			setMerchantSector(data);
		},
		onError: (err: any) => {
			// console.log(err.message);
			if (err.message === '상인 없음') {
				notify(dispatch, '주변에 상인이 없어요');
			}
		},
		retry: false,
		refetchOnWindowFocus: false,
		cacheTime: 0,
	});
	return [merchantSector, setMerchantSector, merchantCurrentRefetch];
};

export default useMerchantSector;
