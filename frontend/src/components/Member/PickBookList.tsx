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
//state btn
import RentalAvailable from '../common/BookState/RentalAvailable';
import ReservationAvailable from '../common/BookState/ReservationAvailable';
import Impossible from '../common/BookState/Impossible';

const PickBookList = () => {
	const [test, setTest] = useState<number[]>([1, 2, 3, 4, 5, 6, 7, 8, 9]);
	const navigate = useNavigate();

	// api mypage member info
	const { getPickBookList } = useMypageAPI();
	const { data, isLoading } = useQuery({
		queryKey: ['pickbooklist'],
		queryFn: () => getPickBookList(),
		retry: false,
	});

	const handleBookDetailPageMove = (id: string) => {
		navigate(`/books/${id}`);
	};

	return (
		<>
			{/* {dummyBookWish?.map(el => {
				return (
					<ContainerNew key={+el.bookId}>
						<BookItem
							// key={+el.bookId}
							bookId={el.bookId}
							title={el.title}
							bookImage={el.imageUrl}
							rentalfee={+el.rentalFee}
							status={el.status}
						/>
					</ContainerNew>
				);
			})} */}

			{test
				? test.map(item => {
						return (
							<Container key={item}>
								<FlexBox>
									<img src={dummyImage2} alt="" width={50} height={70} />
									<InfoWrapped>
										<p>러닝 리액트</p>
										<p>오늘의북스</p>
										<p>3,000</p>
										<p>대여 상태</p>
										{/* <RentalAvailable />
										<ReservationAvailable />
										<Impossible /> */}
									</InfoWrapped>
								</FlexBox>
							</Container>
						);
				  })
				: null}
		</>
	);
};
// const ContainerNew = styled.div`
// 	width: 90%;
// `;

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
		display: flex;
		flex-direction: column;
	}
`;

export default PickBookList;
