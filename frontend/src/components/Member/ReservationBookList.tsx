import { useState } from 'react';
import styled from 'styled-components';
import Animation from '../Loading/Animation';
import dummyImage3 from '../../assets/image/dummy3.png';
import Button from '../common/Button';
import { useMypageAPI } from '../../api/mypage';
import { useQuery } from '@tanstack/react-query';
import { useNavigate } from 'react-router-dom';
import ButtonStatus from '../Merchant/ButtonStatus';

interface ReservationBook {
	reservationId: number;
	rentalExpectedAt: string;
	bookId: number;
	title: string;
	imageUrl: string;
	rentalFee: number;
	status: string;
	merchantName: string;
}

const ReservationBookList = () => {
	const navigate = useNavigate();

	//api
	const { getReservationBookList } = useMypageAPI();
	const { data, isLoading } = useQuery({
		queryKey: ['reservationbooklist'],
		queryFn: () => getReservationBookList().then(res => res.data),
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
			{/* <EmptyBox>
				<p>예약한 책이 없어요</p>
			</EmptyBox> */}
			{data.content ? (
				data?.content.map((reservationbook: ReservationBook, i: number) => {
					const { bookId, title, imageUrl, rentalFee, status } =
						reservationbook;
					return (
						<Container key={bookId}>
							<FlexBox
								onClick={() => {
									handleBookDetailPageMove(bookId);
								}}>
								<img src={imageUrl} alt="도서 이미지" width={50} height={70} />
								<InfoWrapped>
									<div className="list">
										<p className="bookname">{title}</p>
										<p>{rentalFee}원</p>
									</div>
									<ButtonStatus status={status} bookId={bookId} />
								</InfoWrapped>
							</FlexBox>
						</Container>
					);
				})
			) : (
				<EmptyBox>
					<p>예약한 책이 없어요</p>
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
		font-size: ${props => props.theme.fontSizes.paragraph};
		margin-left: 1rem;
	}
`;

const EmptyBox = styled.div`
	width: 100%;
	height: 65vh;
	display: flex;
	justify-content: center;
	align-items: center;
	p {
		font-size: ${props => props.theme.fontSizes.subtitle};
		font-weight: 600;
	}
`;
export default ReservationBookList;
