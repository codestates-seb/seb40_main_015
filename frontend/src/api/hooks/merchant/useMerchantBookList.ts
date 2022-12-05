import { useInfiniteQuery } from '@tanstack/react-query';
import { useEffect, useMemo } from 'react';
import { useInView } from 'react-intersection-observer';
import { useMypageAPI } from '../../mypage';

interface Lists {
	bookId: number;
	title: string;
	status: string;
	bookImage: string;
}

const useMerchantBookList = (merchantId: string) => {
	const { getMerchantBookLists } = useMypageAPI();
	const [ref, inView] = useInView();

	const { data, fetchNextPage, hasNextPage } = useInfiniteQuery(
		['merchantBookList'],
		({ pageParam = undefined }) => getMerchantBookLists(merchantId, pageParam),
		{
			getNextPageParam: lastPage => {
				return lastPage.last
					? undefined
					: lastPage.content[lastPage.content.length - 1].bookId;
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

	return { lists, fetchNextPage, hasNextPage, ref };
};

export default useMerchantBookList;
