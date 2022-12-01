import { useInfiniteQuery } from '@tanstack/react-query';
import { useEffect, useState } from 'react';
import { useInView } from 'react-intersection-observer';
import { getMerchantListQuery } from '../../map';

interface IProps {
	centerCoord: { lat: number; lon: number };
	current: { lat: number; lon: number };
	selectOverlay: any;
	searchInput: string;
	zoomLevel: number;
	size: { width: number; height: number };
}

const useMerchantList = (props: IProps) => {
	const { centerCoord, current, selectOverlay, searchInput, zoomLevel, size } =
		props;
	const [merchantLists, setMerchantLists] = useState<any>([]);
	const [ref, inView] = useInView();

	const {
		fetchNextPage,
		hasNextPage,
		refetch: merchantListRefetch,
		remove: merchantListRemove,
	} = useInfiniteQuery({
		queryKey: ['merchantListMap'],
		queryFn: ({ pageParam = undefined }) => {
			const sector = selectOverlay ? selectOverlay?.sector : 0;
			if (!!searchInput) {
				return [];
			}
			let reqZoomLevel = 7 - zoomLevel;
			return getMerchantListQuery(
				centerCoord.lat ? centerCoord.lat : current.lat,
				centerCoord.lon ? centerCoord.lon : current.lon,
				sector,
				reqZoomLevel,
				size.width,
				size.height,
				pageParam,
			);
		},
		getNextPageParam: lastPage => {
			return lastPage.last
				? undefined
				: lastPage?.content[lastPage.content.length - 1].merchantId;
		},
		onSuccess: data => {
			if (selectOverlay?.merchantCount) {
				setMerchantLists(data?.pages.flatMap(page => page.content));
			}
		},
		refetchOnWindowFocus: false,
		cacheTime: 0,
	});

	useEffect(() => {
		if (inView && hasNextPage) fetchNextPage();
	}, [inView]);
	return [
		merchantLists,
		setMerchantLists,
		ref,
		merchantListRefetch,
		merchantListRemove,
	];
};

export default useMerchantList;
