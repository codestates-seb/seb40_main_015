import { useState } from 'react';
import styled from 'styled-components';
import dummyImage from '../../assets/image/dummy.png';
import convertDate from '../../utils/convertDate';
import RentStatusButton from './RentStatusButton';

const RentBookLists = () => {
	const [test, setTest] = useState<number[]>([1, 2, 3, 4, 5]);
	const [state, setState] = useState([
		'TRADING',
		'BEING_RENTED',
		'RETURN_UNREVIEWED',
		'RETURN_REVIEWED',
		'CANCELED',
	]);
	const from = '2022-11-15T00:17:34.045376400';
	const to = '2022-11-21T00:17:34.045376400';
	return (
		<Box>
			{test
				? test.map((item, i) => {
						return (
							<Wrapper key={item}>
								<Container>
									<FlexBox>
										<img src={dummyImage} alt="" width={90} height={105} />
										<InfoWrapped>
											<p>모던 자바스크립트</p>
											<p>상인 이름</p>
											<p>저자 / 출판사</p>
											<p>대여기간</p>
											<p>{convertDate(from, to, true)}</p>
										</InfoWrapped>
									</FlexBox>
									<RentStatusButton status={state[i]} />
								</Container>
							</Wrapper>
						);
				  })
				: null}
		</Box>
	);
};

const Box = styled.div`
	/* padding: 0 1rem; */
`;

const Wrapper = styled.div`
	width: 100%;
	max-width: 850px;
	display: flex;
	flex-direction: column;
	align-items: center;
	margin-bottom: 1rem;
`;

const Container = styled.div`
	width: 90vw;
	display: flex;
	justify-content: space-between;
	margin-bottom: 0.5rem;
	border: 1px solid #eaeaea;
	border-radius: 5px;
	padding: 1rem;
	background-color: white;
`;

const FlexBox = styled.div`
	display: flex;
`;

const InfoWrapped = styled.div`
	display: flex;
	margin-left: 0.3rem;
	flex-direction: column;
	justify-content: space-evenly;
	justify-items: stretch;
	background-color: white;
	p {
		font-size: ${props => props.theme.fontSizes.paragraph};
		margin-left: 1rem;
		background-color: white;
	}
`;

export default RentBookLists;
