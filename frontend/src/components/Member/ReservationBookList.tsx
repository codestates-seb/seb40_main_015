import styled from 'styled-components';
import { useEffect, useRef, useState } from 'react';
import { useNavigate } from 'react-router-dom';

import Animation from '../Loading/Animation';
import Button from '../common/Button';

import { useGetReservList } from '../../api/hooks/member/useGetReservList';
import { useDeleteReserv } from '../../api/hooks/books/useDeleteReserv';

const ReservationBookList = () => {
	const [cancelId, setCancelId] = useState(-1);
	const navigate = useNavigate();
	const infiniteScrollTarget = useRef<HTMLDivElement>(null);

	const handleBookDetailPageMove = (id: number) => {
		navigate(`/books/${id}`);
	};

	const { deleteMutate } = useDeleteReserv(cancelId);

	const {
		reservBookData,
		fetchNextPage,
		isLoading,
		hasNextPage,
		isFetchingNextPage,
	} = useGetReservList();

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

	const handleBookCancel = (title: string, reservationId: number) => {
		const isTrue = window.confirm(`'${title}' 도서의 예약을 취소하시겠습니까?`);
		if (!isTrue) return;
		setCancelId(reservationId);
		deleteMutate();
	};

	useEffect(() => {
		if (cancelId === -1) return;
		deleteMutate();
	}, [cancelId]);

	return (
		<>
			{isLoading ? (
				<Animation width={20} height={20} />
			) : reservBookData?.pages[0].content[0]?.bookInfo.bookId ? (
				reservBookData?.pages.map(el =>
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
										<ButtonWrapper>
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
										</ButtonWrapper>
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
				{isFetchingNextPage ? <p>Loading more books ...</p> : ''}
			</ScrollEnd>
		</>
	);
};

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
		font-size: 13px;
		margin-left: 1rem;
		display: flex;
		flex-direction: column;
		padding-top: 10px;
	}
	.bookname {
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
		font-weight: 600;
	}
`;
const ButtonWrapper = styled.div`
	min-width: 68px;
	height: 2.4rem;
	button {
		width: 100%;
		height: 100%;
	}

	@media screen and (min-width: 800px) {
		width: 80px;
	}
`;

// infinite scroll
const ScrollEnd = styled.div`
	width: 100%;
	background-color: #fbfbfb;
	height: 10rem;
`;
export default ReservationBookList;
