import { useEffect, useRef, useState } from 'react';
import styled from 'styled-components';
import Animation from '../Loading/Animation';
import dummyImage3 from '../../assets/image/dummy3.png';
import Button from '../common/Button';
import { useMypageAPI } from '../../api/mypage';
import { useInfiniteQuery, useMutation } from '@tanstack/react-query';
import { useNavigate } from 'react-router-dom';
import ButtonStatus from '../Merchant/ButtonStatus';
import notify from '../../utils/notify';
import { useDispatch } from 'react-redux';

const ReservationBookList = () => {
	const [cancelId, setCancelId] = useState(-1);
	const navigate = useNavigate();
	const { getReservationBookList, deleteReservation } = useMypageAPI();
	const dispatch = useDispatch();

	const handleBookDetailPageMove = (id: number) => {
		navigate(`/books/${id}`);
	};

	// const handleClickIcon = (
	// 	e: React.MouseEvent<HTMLDivElement, MouseEvent>,
	// 	bookId: number,
	// ) => {
	// 	e.stopPropagation();
	// 	if (window.confirm('정말 취소하시겠습니까?')) {
	// 		mutate(bookId);
	// 	} else {
	// 		return;
	// 	}
	// };

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

	//예약취소
	const { mutate: deleteMutate } = useMutation({
		mutationFn: () => deleteReservation(cancelId),
		onSuccess: () => {
			notify(dispatch, '예약이 취소되었습니다.');
		},
		retry: false,
	});

	const handleBookCancel = (title: string, reservationId: number) => {
		const isTrue = window.confirm(`'${title}' 도서의 예약을 취소하시겠습니까?`);
		if (!isTrue) return;
		setCancelId(reservationId);
		// deleteMutate();
	};

	useEffect(() => {
		if (cancelId === -1) return;
		deleteMutate();
	}, [cancelId]);

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
									}}
									// onClick={e => handleClickIcon(e, el.bookId)}
								>
									<img
										src={bookImage}
										alt="도서 이미지"
										width={50}
										height={70}
									/>
									<InfoWrapped>
										<div className="list">
											<p className="bookname">
												{title.length < 17 ? title : title.slice(0, 17) + '...'}
											</p>
											<p>{rentalFee}원</p>
										</div>
										<Button
											fontSize={'small'}
											onClick={(e: React.MouseEvent<HTMLButtonElement>) => {
												e.stopPropagation();
												handleBookCancel(
													title,
													reservationbook.reservationInfo.reservationId,
												);
											}}>
											예약 취소
										</Button>
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
	background-color: #fbfbfb;
	height: 10rem;
`;
export default ReservationBookList;
