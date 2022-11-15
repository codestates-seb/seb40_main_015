import { useState } from 'react';
import styled from 'styled-components';
import dummyImage from '../../assets/image/dummy.png';
import Button from '../common/Button';

const LentBookLists = () => {
	const [test, setTest] = useState<number[]>([1, 2, 3, 4]);
	const [state, setState] = useState([
		'ON_TRADING',
		'BEING_RENTED_&_RESERVE_AVAILABLE',
		'BEING_RENTED_&_RESERVE_UNAVAILABLE',
	]);
	return (
		<>
			{test
				? test.map(item => {
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
								<div
									style={{
										display: 'flex',
										flexDirection: 'column',
										justifyContent: 'space-between',
										flexWrap: 'wrap',
										width: '4.5rem',
									}}>
									<Button fontSize="small">대여 가능</Button>
									<Button fontSize="small">대여 가능대여 가능</Button>
								</div>
							</Container>
						);
				  })
				: null}
		</>
	);
};

export default LentBookLists;

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
