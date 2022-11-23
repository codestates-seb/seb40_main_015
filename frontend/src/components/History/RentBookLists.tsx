import styled from 'styled-components';
import BookItem from '../Books/BookItem';
import { useHistoryAPI } from '../../api/history';
import { useInfiniteQuery } from '@tanstack/react-query';
import Animation from '../Loading/Animation';
import { useEffect, useMemo } from 'react';
import { useInView } from 'react-intersection-observer';

const RentBookLists = () => {
	const { getRentalBookLists } = useHistoryAPI();
	const [ref, inView] = useInView();

	const { data, fetchNextPage, hasNextPage, isFetchingNextPage } =
		useInfiniteQuery(
			['rentBookList'],
			({ pageParam = undefined }) =>
				getRentalBookLists(pageParam).then(res => res.data),
			{
				getNextPageParam: lastPage => {
					return lastPage.last
						? undefined
						: lastPage.content[lastPage.content.length - 1].rentalInfo.rentalId;
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

	return (
		<Box>
			{lists?.length ? (
				<>
					{lists?.map((el: any) => (
						<BookItem
							key={el.rentalInfo.rentalId}
							bookId={el.bookInfo.bookId}
							title={el.bookInfo.title}
							bookImage={el.bookInfo.bookUrl}
							rentalfee={el.bookInfo.rentalFee}
							author={el.bookInfo.author}
							publisher={el.bookInfo.publisher}
							merchantName={el.bookInfo.merchantName}
							status={el.rentalInfo.rentalState}
							rental={el.rentalInfo}
						/>
					))}
				</>
			) : (
				<EmptyBox>
					<p>빌린 책이 없어요</p>
				</EmptyBox>
			)}
			{hasNextPage ? <div ref={ref}>Loading...</div> : null}
		</Box>
	);
};

const Box = styled.div`
	/* padding: 0 1rem; */
	height: 100%;
`;

const EmptyBox = styled.div`
	width: 100%;
	height: 75vh;
	display: flex;
	justify-content: center;
	align-items: center;
	p {
		font-size: ${props => props.theme.fontSizes.subtitle};
		font-weight: 600;
	}
`;
export default RentBookLists;
