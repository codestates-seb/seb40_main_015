import styled from 'styled-components';
import BookItem from '../Books/BookItem';
import { useHistoryAPI } from '../../api/history';
import { useInfiniteQuery } from '@tanstack/react-query';
import Animation from '../Loading/Animation';
import { dummyBooksRental } from '../../assets/dummy/books';
import LendBookUserInfo from './LendBookUserInfo';
import LendStatusButton from './LendStatusButton';
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
			{/* 빌린책 -빌려준책 레이아웃 일치시키는 중  */}
			{/* {dummyBooksRental.map((el: any) => (
				<>
					<BookItem
						key={el.rentalInfo.rentalId}
						bookId={el.bookInfo.bookId}
						title={el.bookInfo.title}
						bookImage={el.bookInfo.bookUrl}
						rentalfee={el.bookInfo.rentalFee}
						author={el.bookInfo.author}
						publisher={el.bookInfo.publisher}
						// merchantName={el.bookInfo.merchantName}
						status={el.rentalInfo.rentalState}
						rental={el.rentalInfo}
					/> */}
			{/* lendbook 참고해서 rentbook 전용 유저 인포 컴포넌트와 상태 버튼을 만들어야 할듯, 통합 컴포넌트를 만들거나 */}
			{/* <LendBookUserInfo
						rentalInfo={el.rentalInfo}
						merchantName={el.bookInfo.merchantName}
					/>
					<LendStatusButton
						status={el.rentalInfo.rentalState}
						customerName={el.rentalInfo.customerName}
						rental={el.rentalInfo} */}
			{/* />
				</>
			))} */}

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
