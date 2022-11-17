import Lottie from 'lottie-react';
import animationData from '../../assets/image/lottieloading.json';
import styled from 'styled-components';

const Animation = () => {
	return (
		<Background>
			<LottieContainer>
				<Lottie animationData={animationData} loop={true}></Lottie>
			</LottieContainer>
		</Background>
	);
};

const Background = styled.div`
	position: absolute;
	width: 100vw;
	height: 100vh;
	top: 0;
	left: 0;
	background: #ffffffb7;
	z-index: 999;
	display: flex;
	flex-direction: column;
	align-items: center;
	justify-content: center;
`;

const LottieContainer = styled.div`
	width: 150px;
	height: 150px;
	margin: 0 auto;
	position: absolute;
	z-index: 1000;
`;

export default Animation;
