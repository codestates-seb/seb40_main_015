import React from 'react';
import { useNavigate } from 'react-router';
import styled from 'styled-components';
import useReviewList from '../../api/hooks/review/useReviewList';
import Star from '../common/Star';

interface Iprops {
	merchantId: number | string;
}

interface IList {
	bookId: number;
	bookTitle: string;
	customerAvatarUrl: string;
	customerNickname: string;
	rentalId: number;
	reviewGrade: number;
	reviewId: number;
	reviewMessage: string;
}

const ReviewList = ({ merchantId }: Iprops) => {
	const navigate = useNavigate();
	const { lists, hasNextPage, ref } = useReviewList(merchantId);

	const handleMoveReviewerPage = (reviewId: number, e: React.MouseEvent) => {
		e.stopPropagation();
		navigate(`/profile/merchant/${reviewId}`);
	};

	const handleMoveBookDetailPage = (bookId: number) => {
		navigate(`/books/${bookId}`);
	};

	return (
		<>
			{lists?.length ? (
				lists?.map((list: IList) => {
					const {
						reviewId,
						bookTitle,
						bookId,
						customerAvatarUrl,
						customerNickname,
						reviewMessage,
						reviewGrade,
					} = list;
					return (
						<Container
							key={reviewId}
							onClick={() => handleMoveBookDetailPage(bookId)}>
							<List>
								<Left>
									<UserInfo>
										<img
											src={customerAvatarUrl}
											alt="리뷰 작성자 프로필 이미지"
											onClick={e => {
												handleMoveReviewerPage(reviewId, e);
											}}
										/>
										<span>{customerNickname}</span>
									</UserInfo>
									<p>
										도서명: <span>{bookTitle}</span>
									</p>
									<p>평점: {<Star reviewGrade={reviewGrade} />}</p>
									<p>
										내용: <span>{reviewMessage}</span>
									</p>
								</Left>
								<BookImg src={customerAvatarUrl} alt="책 이미지" />
							</List>
						</Container>
					);
				})
			) : (
				<EmptyBox>등록된 리뷰가 없어요</EmptyBox>
			)}
			{hasNextPage ? <ScrollEnd ref={ref}>Loading...</ScrollEnd> : null}
		</>
	);
};

const Container = styled.div`
	padding: 0.3rem 0.5rem;
	cursor: pointer;
`;

const UserInfo = styled.div`
	display: flex;
	align-items: center;

	img {
		width: 3rem;
		height: 3rem;
		margin-right: 1rem;
		border-radius: 50px;
	}
`;

const List = styled.div`
	width: 90vw;
	height: 8rem;
	border: 1px solid ${props => props.theme.colors.grey};
	padding: 1rem;
	display: flex;
	justify-content: space-between;
	align-items: center;

	p {
		display: flex;
		align-items: center;
		font-size: 1.1rem;
	}
	span {
		font-size: 1.1rem;
		font-weight: 600;
	}
`;

const Left = styled.div`
	display: flex;
	flex-direction: column;
	justify-content: space-between;
	height: 100%;
`;

const BookImg = styled.img`
	width: 5rem;
	height: 7rem;
	border-radius: 5px;
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

export default ReviewList;
