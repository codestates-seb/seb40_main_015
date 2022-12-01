import { useEffect, useRef, useState } from 'react';
import styled from 'styled-components';
import Animation from '../Loading/Animation';
import dummyImage3 from '../../assets/image/dummy3.png';
import Button from '../common/Button';
import { useMypageAPI } from '../../api/mypage';
import { useInfiniteQuery } from '@tanstack/react-query';
import { useNavigate } from 'react-router-dom';
import ButtonStatus from '../Merchant/ButtonStatus';

const ReservationBookList = () => {
	const navigate = useNavigate();
	const { getReservationBookList } = useMypageAPI();

	const handleBookDetailPageMove = (id: number) => {
		navigate(`/books/${id}`);
	};

	// 예약목록 무한스크롤
	const infiniteScrollTarget = useRef<HTMLDivElement>(null);
	const { data, fetchNextPage, isLoading, hasNextPage, isFetchingNextPage } =
		useInfiniteQuery({
			queryKey: ['reservationbooklist'],
			queryFn: ({ pageParam = undefined }) => getReservationBookList(pageParam),
			getNextPageParam: lastPage => {
				// console.log('ff: ', lastPage.content?.slice(-1)[0]?.bookInfo.bookId);
				return lastPage.content?.slice(-1)[0]?.reservationInfo.reservationId;
			},
		});

	useEffect(() => {
		const observer = new IntersectionObserver(
			entries => {
				const [entry] = entries;
				if (!entry.isIntersecting) return;
				fetchNextPage();
			},
			{
				root: null,
				threshold: 0.4,
			},
		);
		observer.observe(infiniteScrollTarget?.current as Element);

		return () => observer.disconnect();
	}, []);

	return (
		<>
			{isLoading ? (
				<Animation width={20} height={20} />
			) : data?.pages[0].content[0]?.bookInfo.bookId ? (
				data?.pages.map(el =>
					el?.content.map((reservationbook, i: number) => {
						const { bookId, title, bookImage, rentalFee } =
							reservationbook.bookInfo;
						return (
							<Container key={bookId}>
								<FlexBox
									onClick={() => {
										handleBookDetailPageMove(bookId);
									}}>
									<img
										src={bookImage}
										alt="도서 이미지"
										width={50}
										height={70}
									/>
									<InfoWrapped>
										<div className="list">
											<p className="bookname">{title}</p>
											<p>{rentalFee}원</p>
										</div>
										<ButtonStatus status={'대여중'} bookId={bookId} />
									</InfoWrapped>
								</FlexBox>
							</Container>
						);
					}),
				)
			) : (
				<EmptyBox>
					<p>예약한 책이 없어요</p>
				</EmptyBox>
			)}

			<ScrollEnd
				ref={infiniteScrollTarget}
				className={`${hasNextPage ? '' : 'hidden'}`}>
				{/* className={`${isFetchingNextPage ? '' : 'hidden'}`}> */}
				{isFetchingNextPage ? <p>Loading more books ...</p> : ''}
			</ScrollEnd>
		</>
	);
};

const Box = styled.div`
	/* padding: 0 1rem; */
`;

const Container = styled.div`
	width: 90%;
	display: flex;
	justify-content: space-between;
	border: 1px solid #eaeaea;
	border-radius: 5px;
	padding: 1rem;
	margin-bottom: 0.5rem;
	cursor: pointer;

	&:hover {
		background-color: ${props => props.theme.colors.grey};
	}

	@media (min-width: 800px) {
		width: 800px;
	}
`;

const FlexBox = styled.div`
	display: flex;
	width: 100%;
`;

const InfoWrapped = styled.div`
	display: flex;
	width: 100%;
	margin-left: 0.3rem;
	justify-content: space-between;
	align-items: center;
	p {
		/* font-size: ${props => props.theme.fontSizes.paragraph};
		margin-left: 1rem; */
		font-size: 13px;
		margin-left: 1rem;
		display: flex;
		flex-direction: column;
		padding-top: 10px;
	}
	.bookname {
		/* font-size: ${props => props.theme.fontSizes.subtitle}; */
		font-size: 16px;
		font-weight: 600;
		padding-top: 0px;
	}
`;

const EmptyBox = styled.div`
	width: 100%;
	height: 55vh;
	display: flex;
	justify-content: center;
	align-items: center;
	p {
		font-size: ${props => props.theme.fontSizes.subtitle};
		/* font-size: 16px; */
		font-weight: 600;
	}
`;

// infinite scroll
const ScrollEnd = styled.div`
	width: 100%;
	background-color: ${props => props.theme.colors.grey};
	height: 10rem;
`;
export default ReservationBookList;
