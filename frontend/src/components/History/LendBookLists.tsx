import styled from 'styled-components';
import LendStatusButton from './LendStatusButton';
import BookItem from '../Books/BookItem';
import LendBookUserInfo from './LendBookUserInfo';
import { useHistoryAPI } from '../../api/history';
import { useInfiniteQuery } from '@tanstack/react-query';
import Animation from '../Loading/Animation';
import { useEffect, useMemo } from 'react';
import { useInView } from 'react-intersection-observer';

const LentBookLists = () => {
	const { getLendBookLists } = useHistoryAPI();
	const [ref, inView] = useInView();

	// const { data, isLoading } = useQuery(
	// 	['lendBookList'],
	// 	() => getLendBookLists().then(res => res.data),
	// 	{ retry: 1 },
	// );

	const { data, fetchNextPage, hasNextPage, isFetchingNextPage } =
		useInfiniteQuery(
			['lendBookList'],
			({ pageParam = undefined }) =>
				getLendBookLists(pageParam).then(res => res.data),
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
			{/* 통합본 추가 */}
			{lists?.length ? (
				lists?.map((el: any) => (
					<Wrapper key={el.rentalInfo.rentalId}>
						<BookItem
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
						<LendBookUserInfo rentalInfo={el.rentalInfo} />
						<LendStatusButton
							status={el.rentalInfo.rentalState}
							customerName={el.rentalInfo.customerName}
							rental={el.rentalInfo}
						/>
					</Wrapper>
				))
			) : (
				<EmptyBox>
					<p>빌려준 책이 없어요</p>
				</EmptyBox>
			)}
			{hasNextPage ? <div ref={ref}>Loading...</div> : null}
		</Box>
	);
};

const Box = styled.div`
	/* padding: 0 1rem; */
`;

const Wrapper = styled.div`
	width: 100%;
	/* max-width: 850px; */
	display: flex;
	flex-direction: column;
	margin-bottom: 2rem;
	/* 
	padding-bottom: 1rem;
	border-bottom: 1px solid black; */
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

export default LentBookLists;
