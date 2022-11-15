import { useState } from 'react';
import styled from 'styled-components';
import dummyImage from '../../assets/image/dummy.png';
import RentStatusButton from './RentStatusButton';

const RentBookLists = () => {
	const [test, setTest] = useState<number[]>([1, 2, 3, 4, 5]);
	const [state, setState] = useState([
		'TRADING',
		'Rented',
		'ReviewComplete',
		'ReviewNotComplete',
		'Canceled',
	]);
	const status = 'TRADING';
	return (
		<>
			{test
				? test.map((item, i) => {
						return (
							<Container key={item}>
								<FlexBox>
									<img src={dummyImage} alt="" width={90} height={105} />
									<InfoWrapped>
										<p>모던 자바스크립트</p>
										<p>상인 이름</p>
										<p>저자 / 출판사</p>
										<p>대여기간</p>
										<p>2022.11.09 수 ~ 2022.11.16 수</p>
									</InfoWrapped>
								</FlexBox>
								<RentStatusButton status={state[i]} />
							</Container>
						);
				  })
				: null}
		</>
	);
};

export default RentBookLists;

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
		font-size: 0.9rem;
		margin-left: 1rem;
	}
`;
