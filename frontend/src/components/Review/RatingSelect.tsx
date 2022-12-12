import { Dispatch, SetStateAction } from 'react';
import { HiStar } from 'react-icons/hi';
import styled from 'styled-components';

interface Iprops {
	hovered: number;
	setHovered: Dispatch<SetStateAction<number>>;
	clicked: number;
	setClicked: Dispatch<SetStateAction<number>>;
}

const RatingSelect = (props: Iprops) => {
	const stars = [1, 2, 3, 4, 5];
	const { hovered, setHovered, clicked, setClicked } = props;

	return (
		<Box>
			<>
				{stars.map(star => {
					return (
						<Star
							key={star}
							onMouseEnter={() => setHovered(star)}
							onMouseLeave={() => setHovered(0)}
							onClick={() => setClicked(star)}
							view={hovered < star && clicked < star ? 1 : 0}
						/>
					);
				})}
			</>
		</Box>
	);
};

const Box = styled.div`
	display: flex;
	align-items: center;
`;

interface StarProps {
	view: number;
}

const Star = styled(HiStar)<StarProps>`
	width: 1.5rem;
	height: 1.5rem;
	color: #cccccc;
	color: ${props => (props.view ? '#cccccc' : '#ff9700')};
	:hover {
		color: #ff9700;
	}
`;

export default RatingSelect;
