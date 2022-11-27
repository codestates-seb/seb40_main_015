import { CustomOverlayMap } from 'react-kakao-maps-sdk';
import styled, { keyframes } from 'styled-components';

interface HoverProps {
	latitude: number;
	longitude: number;
}

const CustomOverlayHover = ({ hoverList }: { hoverList: HoverProps }) => {
	const { latitude, longitude } = hoverList;
	return (
		<>
			<div key={`${latitude}-${longitude}`}>
				<CustomOverlayMap
					position={{
						lat: Number(latitude) + 0.0001,
						lng: Number(longitude),
					}}>
					<StyledOverlay />
				</CustomOverlayMap>
			</div>
		</>
	);
};

const Circle = keyframes`
	0% {
  transform: scale(1);
	opacity: 0.7;
	}
  
	70% {
    transform: scale(0.4);
	opacity: 0.6;
	}

  100% {
  transform: scale(0.4);
	opacity: 0.6;
  }
	`;

const StyledOverlay = styled.div`
	width: 7rem;
	height: 7rem;
	border-radius: 50%;
	background-color: #f6bb83;
	animation: ${Circle} 1.4s infinite;
`;

export default CustomOverlayHover;
