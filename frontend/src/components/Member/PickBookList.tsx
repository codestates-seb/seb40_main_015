import styled from 'styled-components';
import { useEffect, useRef, useState } from 'react';
import { useInfiniteQuery, useQuery } from '@tanstack/react-query';
import { useNavigate } from 'react-router-dom';

//component
import { useMypageAPI } from '../../api/mypage';
import ButtonStatus from '../Merchant/ButtonStatus';
import Animation from '../Loading/Animation';

const PickBookList = () => {
	const navigate = useNavigate();
	const { getPickBookList } = useMypageAPI();

	const handleBookDetailPageMove = (id: number) => {
		navigate(`/books/${id}`);
	};

	// 찜목록 무한스크롤
	const infiniteScrollTarget = useRef<HTMLDivElement>(null);
	const { data, fetchNextPage, isLoading, hasNextPage, isFetchingNextPage } =
		useInfiniteQuery({
			queryKey: ['pickbooklist'],
			queryFn: ({ pageParam = undefined }) => getPickBookList(pageParam),
			getNextPageParam: lastPage => {
				return lastPage?.content?.slice(-1)[0]?.bookId;
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
			) : data?.pages[0].content[0]?.bookId ? (
				data?.pages.map(el =>
					el?.content.map((pickbook, i: number) => {
						const {
							bookId,
							title,
							status,
							bookImage,
							rentalFee,
							merchantName,
						} = pickbook;
						// 대여가능, 예약가능, 대여/예약 불가능
						return (
							<Container key={bookId}>
								<FlexBox
									onClick={() => {
										handleBookDetailPageMove(bookId);
									}}>
									<img src={bookImage} alt="" width={50} height={70} />
									<InfoWrapped>
										<div className="list">
											<p className="bookname">{title}</p>
											<p>{rentalFee}원</p>
											<p>{merchantName}</p>
										</div>
										<ButtonStatus status={status} bookId={bookId} />
									</InfoWrapped>
								</FlexBox>
							</Container>
						);
					}),
				)
			) : (
				<EmptyBox>
					<p>찜한 책이 없어요</p>
				</EmptyBox>
			)}
			{/* 무한스크롤 */}
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
	line-height: 20px;
	.bookname {
		/* font-size: ${props => props.theme.fontSizes.subtitle}; */
		font-size: 16px;
		font-weight: 600;
	}
	p {
		font-size: 13px;
		margin-left: 1rem;
		display: flex;
		flex-direction: column;
		padding-top: 5px;
	}
	.list {
		display: flex;
		flex-direction: column;
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
// infinite scroll
const ScrollEnd = styled.div`
	width: 100%;
	background-color: ${props => props.theme.colors.grey};
	height: 10rem;
`;
export default PickBookList;
