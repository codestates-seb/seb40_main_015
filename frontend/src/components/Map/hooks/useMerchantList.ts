import { useInfiniteQuery } from '@tanstack/react-query';
import React, { useEffect, useState } from 'react';
import { useInView } from 'react-intersection-observer';
import { getMerchantListQuery } from '../../../api/map';

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
	} = useInfiniteQuery({
		queryKey: ['merchantListMap', centerCoord],
		queryFn: ({ pageParam = undefined }) => {
			const sector = selectOverlay ? selectOverlay?.sector : 0;
			if (!!searchInput) {
				return [];
			}
			return getMerchantListQuery(
				centerCoord.lat ? centerCoord.lat : current.lat,
				centerCoord.lon ? centerCoord.lon : current.lon,
				sector,
				zoomLevel < 3 ? 3 : zoomLevel,
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
		retry: false,
		refetchOnWindowFocus: false,
		cacheTime: 0,
	});

	console.log(merchantLists);

	useEffect(() => {
		if (inView && hasNextPage) fetchNextPage();
	}, [inView]);
	return [merchantLists, setMerchantLists, ref, merchantListRefetch];
};

export default useMerchantList;
