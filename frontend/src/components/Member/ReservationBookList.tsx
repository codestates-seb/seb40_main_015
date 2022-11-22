import { useState } from 'react';
import styled from 'styled-components';
import dummyImage3 from '../../assets/image/dummy3.png';
import Button from '../common/Button';

const ReservationBookList = () => {
	const [test, setTest] = useState<number[]>([1, 2, 3, 4, 5, 6, 7, 8, 9]);
	return (
		<>
			{test
				? test.map(item => {
						return (
							<Container key={item}>
								<FlexBox>
									<img src={dummyImage3} alt="" width={50} height={70} />
									<InfoWrapped>
										<p>오늘부터 개발자</p>
										<p>친구네 다락방</p>
										<p>2,000</p>
										<Button fontSize="small">예약 취소</Button>
									</InfoWrapped>
								</FlexBox>
							</Container>
						);
				  })
				: null}
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

export default ReservationBookList;
