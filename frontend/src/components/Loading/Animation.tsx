import Lottie from 'lottie-react';
import animationData from '../../assets/image/lottieloading.json';
import styled from 'styled-components';

const Animation = ({ width, height }: { width?: number; height?: number }) => {
	return (
		<Background height={height}>
			<LottieContainer width={width} height={height}>
				<Lottie animationData={animationData} loop={true}></Lottie>
			</LottieContainer>
		</Background>
	);
};

interface BackgroundProps {
	height?: number;
}

const Background = styled.div<BackgroundProps>`
	width: 100%;
	height: ${props => (props.height ? '100%' : '100vh')};
	background: #ffffffb7;
	z-index: 999;
	display: flex;
	align-items: center;
	justify-content: center;
`;

interface LottieContainerProps {
	width?: number;
	height?: number;
}

const LottieContainer = styled.div<LottieContainerProps>`
	width: ${props => (props.width ? props.width : '150px')};
	height: ${props => (props.height ? props.height : '150px')};
	margin: 0 auto;
`;

export default Animation;
