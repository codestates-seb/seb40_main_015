import { useState } from 'react';
import styled from 'styled-components';
import dummyImage from '../../assets/image/dummy.png';
import Button from '../common/Button';
import { useMypageAPI } from '../../api/mypage';
import { useQuery } from '@tanstack/react-query';
import Animation from '../Loading/Animation';
import ButtonStatus from './ButtonStatus';
import { useNavigate } from 'react-router-dom';

interface Item {
	bookId: string;
	title: string;
	bookImage: string;
	status: string;
}

const BookList = ({ merchantId }: { merchantId: string | undefined }) => {
	const { getMerchantBookLists } = useMypageAPI();
	const navigate = useNavigate();

	const { data, isLoading } = useQuery(
		['merchantBookList'],
		() => getMerchantBookLists(merchantId),
		{ retry: 1 },
	);

	const handleBookDetailPageMove = (id: string) => {
		navigate(`/books/${id}`);
	};

	if (isLoading) {
		return <Animation width={50} height={50} />;
	}
	return (
		<>
			{data?.content ? (
				data.content.map((item: Item, i: number) => {
					const { bookId, title, bookImage, status } = item;
					// 대여가능, 거래중, 대여중&예약불가, 대여중&예약가능
					return (
						<Container key={bookId}>
							<FlexBox
								onClick={() => {
									handleBookDetailPageMove(bookId);
								}}>
								<img src={bookImage} alt="" width={50} height={70} />
								<InfoWrapped>
									<p>{title}</p>
									<ButtonStatus status={status} bookId={bookId} />
									{/* <Button fontSize="small">대여 가능</Button> */}
								</InfoWrapped>
							</FlexBox>
						</Container>
					);
				})
			) : (
				<EmptyBox>
					<p>등록한 책이 없어요</p>
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
	height: 75vh;
	display: flex;
	justify-content: center;
	align-items: center;
	p {
		font-size: ${props => props.theme.fontSizes.subtitle};
		font-weight: 600;
	}
`;

export default BookList;
