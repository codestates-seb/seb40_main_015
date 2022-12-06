import { useInfiniteQuery } from '@tanstack/react-query';
import { useEffect, useMemo } from 'react';
import { useInView } from 'react-intersection-observer';
import { useHistoryAPI } from '../../history';

const useLendBookLists = (filters: string) => {
	const { getLendBookLists } = useHistoryAPI();
	const [ref, inView] = useInView();

	const { data, fetchNextPage, hasNextPage } = useInfiniteQuery(
		['lendBookList', filters],
		({ pageParam = undefined }) =>
			getLendBookLists(pageParam, filters).then(res => res.data),
		{
			getNextPageParam: lastPage => {
				return lastPage.last
					? undefined
					: lastPage?.content?.[lastPage.content.length - 1].rentalInfo
							.rentalId;
			},
			retry: false,
		},
	);
	const lists: any = useMemo(
		() => data?.pages.flatMap(page => page.content),
		[data?.pages],
	);

	useEffect(() => {
		if (inView && hasNextPage) fetchNextPage();
	}, [inView]);

	return { lists, hasNextPage, ref };
};

export default useLendBookLists;
