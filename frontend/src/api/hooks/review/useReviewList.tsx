import { useInfiniteQuery } from '@tanstack/react-query';
import { useEffect, useMemo } from 'react';
import { useInView } from 'react-intersection-observer';
import { useReviewAPI } from '../../review';

const useReviewList = (id: number | string) => {
	const { getReviewList } = useReviewAPI();
	const [ref, inView] = useInView();

	const { data, fetchNextPage, hasNextPage } = useInfiniteQuery(
		['reviewList', id],
		({ pageParam = undefined }) => getReviewList(id, pageParam),
		{
			getNextPageParam: lastPage => {
				return lastPage.last
					? undefined
					: lastPage?.content?.[lastPage.content.length - 1].reviewId;
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

export default useReviewList;
