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

	}
  
	80% {
    transform: scale(0.4);

	}

  100% {
  transform: scale(0.4);
	
  }
	`;

const StyledOverlay = styled.div`
	width: 8rem;
	height: 8rem;
	border-radius: 50%;
	background-color: rgba(246, 187, 131, 0.7);
	animation: ${Circle} 1.6s infinite;
`;

export default CustomOverlayHover;
