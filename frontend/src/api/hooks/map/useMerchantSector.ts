import { useQuery } from '@tanstack/react-query';
import { useState } from 'react';
import { useAppDispatch } from '../../../redux/hooks';
import notify from '../../../utils/notify';
import { getTotalMerchantQuery } from '../../map';

interface MerchantList {
	merchantCount: number;
	sector: number;
	location: { latitude: number; longitude: number };
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
	const [merchantSector, setMerchantSector] = useState<MerchantList[]>([]);
	const dispatch = useAppDispatch();
	const level = [0, 9, 7, 5, 3, 3, 3];
	console.log(merchantSector);
	const { refetch: merchantCurrentRefetch } = useQuery({
		queryKey: ['merchantSectorByCurrent', centerCoord, zoomLevel, size],
		queryFn: () => {
			if (!!searchInput) {
				return [];
			}
			return getTotalMerchantQuery(
				centerCoord.lat ? centerCoord.lat : current.lat,
				centerCoord.lon ? centerCoord.lon : current.lon,
				size.width,
				size.height,
				level[zoomLevel],
			);
		},
		onSuccess: data => {
			setMerchantSector(data);
		},
		onError: (err: any) => {
			if (err.message === '상인 없음') {
				notify(dispatch, '주변에 상인이 없어요');
			}
		},
		retry: false,
		refetchOnWindowFocus: false,
		cacheTime: 0,
	});
	return [merchantSector, setMerchantSector, merchantCurrentRefetch] as const;
};

export default useMerchantSector;
