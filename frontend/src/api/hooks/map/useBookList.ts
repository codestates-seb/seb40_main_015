import { useInfiniteQuery } from '@tanstack/react-query';
import { useEffect, useState } from 'react';
import { useInView } from 'react-intersection-observer';
import { getBookListQuery } from '../../map';

interface IProps {
	centerCoord: { lat: number; lon: number };
	current: { lat: number; lon: number };
	selectOverlay: any;
	searchInput: string;
	zoomLevel: number;
	size: { width: number; height: number };
}

const useBookList = (props: IProps) => {
	const { centerCoord, current, selectOverlay, searchInput, zoomLevel, size } =
		props;
	const [bookLists, setBookLists] = useState<any>([]);
	const [ref, inView] = useInView();

	const {
		fetchNextPage,
		hasNextPage,
		refetch: bookListRefetch,
		remove: bookListRemove,
	} = useInfiniteQuery({
		queryKey: ['bookListMap'],
		queryFn: ({ pageParam = undefined }) => {
			const sector = selectOverlay ? selectOverlay?.sector : 0;
			if (!searchInput) {
				return [];
			}
			let reqZoomLevel = 8 - zoomLevel;
			return getBookListQuery(
				searchInput,
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
			return lastPage?.last
				? undefined
				: lastPage.numberOfElements
				? lastPage.content[lastPage.content.length - 1].bookId
				: undefined;
		},
		onSuccess: data => {
			if (selectOverlay?.bookCount && searchInput) {
				setBookLists(data?.pages.flatMap(page => page.content));
			} else {
				setBookLists([]);
			}
		},
		refetchOnWindowFocus: false,
		cacheTime: 0,
	});

	useEffect(() => {
		if (inView && hasNextPage) fetchNextPage();
	}, [inView]);

	return [bookLists, setBookLists, ref, bookListRefetch, bookListRemove];
};

export default useBookList;
