import { useState } from 'react';
import styled from 'styled-components';
import dummyImage from '../../assets/image/dummy.png';

const RentBookLists = () => {
	const [test, setTest] = useState<number[]>([1, 2]);
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
										<p>날짜</p>
									</InfoWrapped>
								</FlexBox>
								<Button>대여 가능</Button>
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
		font-size: 1.1rem;
		margin-left: 1rem;
	}
`;

const Button = styled.button`
	background-color: ${props => props.theme.colors.main};
	width: 4rem;
	height: 2rem;
	font-size: 0.8rem;
	border: none;
	border-radius: 3px;
	color: white;
	margin: auto 0;
	:hover {
		background-color: #009539;
	}
`;
