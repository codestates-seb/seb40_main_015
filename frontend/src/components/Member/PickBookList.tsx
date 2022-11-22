import { useState } from 'react';
import styled from 'styled-components';
import dummyImage2 from '../../assets/image/dummy2.png';
import Button from '../common/Button';

const PickBookList = () => {
	const [test, setTest] = useState<number[]>([1, 2, 3, 4, 5, 6, 7, 8, 9]);
	return (
		<>
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
										<Button fontSize="small">대여 가능</Button>
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
		display: flex;
		flex-direction: column;
	}
`;

export default PickBookList;
