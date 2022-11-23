import styled from 'styled-components';
import Animation from '../Loading/Animation';
import { useState } from 'react';
import { useMypageAPI } from '../../api/mypage';
import { useQuery } from '@tanstack/react-query';
import { useNavigate } from 'react-router-dom';
import { dummyBookWish } from '../../assets/dummy/books';
import dummyImage2 from '../../assets/image/dummy2.png';
import BookItem from '../Books/BookItem';
import Button from '../common/Button';
import ButtonStatus from '../Merchant/ButtonStatus';
// import BookStatus from './BookStatus';

interface PickBook {
	bookId: number;
	title: string;
	status: string;
	bookImage: string;
	rentalFee: number;
	merchantName: string;
}

const PickBookList = () => {
	const navigate = useNavigate();

	// api mypage member info
	const { getPickBookList } = useMypageAPI();
	const { data, isLoading } = useQuery({
		queryKey: ['pickbooklist'],
		queryFn: () => getPickBookList().then(res => res.data),
		retry: false,
	});

	const handleBookDetailPageMove = (id: number) => {
		navigate(`/books/${id}`);
	};

	if (isLoading) {
		return <Animation width={50} height={50} />;
	}
	console.log(data?.content);
	return (
		<>
			{data?.content ? (
				data.content.map((pickbook: PickBook, i: number) => {
					const { bookId, title, status, bookImage, rentalFee, merchantName } =
						pickbook;
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
				})
			) : (
				<EmptyBox>
					<p>찜한 책이 없어요</p>
				</EmptyBox>
			)}
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
		font-size: ${props => props.theme.fontSizes.subtitle};
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
	height: 75vh;
	display: flex;
	justify-content: center;
	align-items: center;
	p {
		font-size: ${props => props.theme.fontSizes.subtitle};
		font-weight: 600;
	}
`;

export default PickBookList;
