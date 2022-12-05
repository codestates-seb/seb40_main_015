import styled from 'styled-components';
import LendStatusButton from './LendStatusButton';
import BookItem from '../Books/BookItem';
import LendBookUserInfo from './LendBookUserInfo';
import { useHistoryAPI } from '../../api/history';
import { useInfiniteQuery } from '@tanstack/react-query';
import Animation from '../Loading/Animation';
import { useEffect, useMemo } from 'react';
import { useInView } from 'react-intersection-observer';

interface ILentBookListsProps {
	filters: string;
}

const LentBookLists = ({ filters }: ILentBookListsProps) => {
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

	return (
		<Box>
			{lists?.length ? (
				lists?.map(
					(el: any) =>
						el && (
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
								<LendBookUserInfo rentalInfo={el.rentalInfo} />
								<LendStatusButton
									status={el.rentalInfo.rentalState}
									customerName={el.rentalInfo.customerName}
									rental={el.rentalInfo}
								/>
							</Wrapper>
						),
				)
			) : (
				<EmptyBox>
					<p>빌려준 책이 없어요</p>
				</EmptyBox>
			)}
			{hasNextPage ? <ScrollEnd ref={ref}>Loading...</ScrollEnd> : null}
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
	margin-bottom: 3rem;

	/* padding-bottom: 2rem; */
	/* border-bottom: 1px solid rgba(0, 0, 0, 0.2); */

	button {
		height: 3rem;
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
const ScrollEnd = styled.div`
	background-color: #fbfbfb;
`;
export default LentBookLists;
