import styled from 'styled-components';
import BookItem from '../Books/BookItem';
import { useHistoryAPI } from '../../api/history';
import { useInfiniteQuery } from '@tanstack/react-query';
import Animation from '../Loading/Animation';
import LendBookUserInfo from './LendBookUserInfo';
import RentStatusButton from './RentStatusButton';
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
						<Wrapper key={el.rentalInfo.rentalId}>
							<BookItem
								bookId={el.bookInfo.bookId}
								title={el.bookInfo.title}
								bookImage={el.bookInfo.bookUrl}
								rentalfee={el.bookInfo.rentalFee}
								author={el.bookInfo.author}
								publisher={el.bookInfo.publisher}
								// merchantName={el.bookInfo.merchantName}
								status={el.rentalInfo.rentalState}
								rental={el.rentalInfo}
							/>
							<LendBookUserInfo
								rentalInfo={el.rentalInfo}
								merchantName={el.bookInfo.merchantName}
							/>
							<RentStatusButton
								status={el.rentalInfo.rentalState}
								merchantName={el.rentalInfo.customerName}
								rental={el.rentalInfo}
							/>
						</Wrapper>
					))}
				</>
			) : (
				<EmptyBox>
					<p>빌린 책이 없어요</p>
				</EmptyBox>
			)}

			{/* {lists?.length ? (
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
			)} */}
			{hasNextPage ? <div ref={ref}>Loading...</div> : null}
		</Box>
	);
};

const Box = styled.div`
	/* padding: 0 1rem; */
	height: 100%;
`;

const Wrapper = styled.div`
	width: 100%;
	/* max-width: 850px; */
	display: flex;
	flex-direction: column;
	margin-bottom: 3rem;

	position: relative;
	.cancel {
		color: black;
		padding: 0.6rem 0.8rem;
		background-color: inherit;
		border-radius: 0 5px 0 5px;
		/* border: 1px solid rgba(1, 1, 1, 0.1); */
		border-left: 1px solid rgba(1, 1, 1, 0.1);
		border-bottom: 1px solid rgba(1, 1, 1, 0.1);

		position: absolute;
		top: 0;
		right: 0;
		:hover {
			background-color: ${props => props.theme.colors.grey};
		}
	}
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
