import { useQuery } from '@tanstack/react-query';
import { useState } from 'react';
import { useAppDispatch } from '../../../redux/hooks';
import notify from '../../../utils/notify';
import { getTotalBookQuery } from '../../map';

interface IProps {
	centerCoord: { lat: number; lon: number };
	current: { lat: number; lon: number };
	searchInput: string;
	zoomLevel: number;
	size: { width: number; height: number };
}

interface BookSector {
	bookCount: number;
	sector: number;
	location: { latitude: number; longitude: number };
}

const useBookSector = (props: IProps) => {
	const { centerCoord, current, searchInput, zoomLevel, size } = props;
	const [bookSector, setBookSector] = useState<BookSector[]>([]);
	const dispatch = useAppDispatch();
	const level = [0, 9, 9, 7, 5, 5, 5, 5, 5, 5];

	const { refetch: bookCurrentRefetch } = useQuery({
		queryKey: ['bookSectorByCurrent', centerCoord, zoomLevel, size],
		queryFn: () => {
			if (!searchInput) {
				return [];
			}
			return getTotalBookQuery(
				searchInput,
				centerCoord.lat ? centerCoord.lat : current.lat,
				centerCoord.lon ? centerCoord.lon : current.lon,
				size.width,
				size.height,
				level[zoomLevel],
			);
		},
		onSuccess: data => {
			setBookSector(data);
		},
		onError: (err: any) => {
			// console.log(err.message);
			if (err.message === '책 없음') {
				notify(dispatch, '우리 동네에는 찾으시는 책이 없어요');
			}
		},
		retry: false,
		refetchOnWindowFocus: false,
		cacheTime: 0,
	});
	return [bookSector, setBookSector, bookCurrentRefetch] as const;
};

export default useBookSector;
