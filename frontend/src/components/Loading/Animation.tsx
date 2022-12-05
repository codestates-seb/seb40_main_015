import Lottie from 'lottie-react';
import animationData from '../../assets/image/lottieloading.json';
import styled from 'styled-components';

const Animation = ({
	width,
	height,
	text,
}: {
	width?: number | string;
	height?: number | string;
	text?: string;
}) => {
	return (
		<Background height={height}>
			<LottieContainer width={width} height={height}>
				<Lottie animationData={animationData} loop={true}></Lottie>
				{text && <Text>{text}</Text>}
			</LottieContainer>
		</Background>
	);
};

interface BackgroundProps {
	height?: number | string;
}

const Background = styled.div<BackgroundProps>`
	width: 100%;
	height: ${props => (props.height ? '100%' : '100vh')};
	background: #ffffffb7;
	z-index: 999;
	display: flex;
	align-items: center;
	justify-content: center;
	background-color: #fbfbfb;
`;

interface LottieContainerProps {
	width?: number | string;
	height?: number | string;
}

const LottieContainer = styled.div<LottieContainerProps>`
	width: ${props => (props.width ? props.width : '150px')};
	height: ${props => (props.height ? props.height : '150px')};
	margin: 0 auto;
	display: flex;
	justify-content: center;
	align-items: center;
	flex-direction: column;
`;

const Text = styled.p`
	margin-top: 1rem;
	font-size: 1.5rem;
`;

export default Animation;
