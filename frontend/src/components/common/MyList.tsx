import { useState } from 'react';
import styled from 'styled-components';
import { dummyBookWish } from '../../assets/dummy/books';
import dummyImage from '../../assets/image/dummy.png';
import BookItem from '../Books/BookItem';
import Button from './Button';

// 통합본
// const MyList = () => {
// 	return (
// 		<>
// 			{dummyBookWish?.map(el => {
// 				return (
// 					<ContainerNew key={+el.bookId}>
// 						<BookItem
// 							// key={+el.bookId}
// 							bookId={el.bookId}
// 							title={el.title}
// 							bookImage={el.imageUrl}
// 							rentalfee={+el.rentalFee}
// 							status={el.status}
// 						/>
// 					</ContainerNew>
// 				);
// 			})}
// 		</>
// 	);
// };

// 통합 이전
const MyList = () => {
	const [test, setTest] = useState<number[]>([1, 2, 3, 4, 5, 6]);
	return (
		<>
			{test
				? test.map(item => {
						return (
							<Container key={item}>
								<FlexBox>
									<img src={dummyImage} alt="" width={50} height={70} />
									<InfoWrapped>
										<p>도서 이름 : 모던 자바스크립트</p>
										<p>상인 이름 : 상헌북스</p>
										<p>대여료 : 2,000</p>
									</InfoWrapped>
								</FlexBox>
								<Button fontSize="small">대여 가능</Button>
							</Container>
						);
				  })
				: null}
		</>
	);
};

export default MyList;

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
`;

const InfoWrapped = styled.div`
	display: flex;
	margin-left: 0.3rem;
	flex-direction: column;
	justify-content: center;
	justify-items: stretch;

	p {
		font-size: 1.2rem;
		margin-left: 1rem;
	}
`;
