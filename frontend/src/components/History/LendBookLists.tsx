import { useState } from 'react';
import styled from 'styled-components';
import dummyImage from '../../assets/image/dummy.png';
import convertDate from '../../utils/convertDate';
import Button from '../common/Button';
import LendStatusButton from './LendStatusButton';

const LentBookLists = () => {
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
		<>
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
								</Container>
								<BottomContainer>
									<UserInfoBox>
										<span>주민: 김주민</span>
										<span>대여기간: {convertDate(from, to)}</span>
									</UserInfoBox>
									<LendStatusButton status={state[i]} />
								</BottomContainer>
							</Wrapper>
						);
				  })
				: null}
		</>
	);
};


const Wrapper = styled.div`
	width: 100%;
	display: flex;
	flex-direction: column;
	margin-bottom: 1rem;
`;

const Container = styled.div`
	display: flex;
	justify-content: space-between;
	border: 1px solid #eaeaea;
	border-radius: 5px;
	padding: 1rem;
	margin-bottom: 0.5rem;
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
	p {
		font-size: ${props => props.theme.fontSizes.paragraph};
		margin-left: 1rem;
	}
`;

const BottomContainer = styled.div`
	width: 90vw;
	display: flex;
	justify-content: center;
	flex-direction: column;
	margin: auto;
	margin-bottom: 1rem;
`;

const UserInfoBox = styled.div`
	border: 1px solid #eaeaea;
	border-radius: 5px;
	width: 90vw;
	display: flex;
	justify-content: space-evenly;
	margin: auto;
	margin-bottom: 1rem;
	padding: 1rem 0;
	background-color: white;
	span {
		font-size: ${props => props.theme.fontSizes.paragraph};
	}
`;

export default LentBookLists;
