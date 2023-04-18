import { Dispatch, SetStateAction } from 'react';
import { HiStar } from 'react-icons/hi';
import styled from 'styled-components';

interface IProps {
	hovered: number | null;
	setHovered: Dispatch<SetStateAction<number | null>>;
	clicked: number;
	setClicked: Dispatch<SetStateAction<number>>;
}

const RatingSelect = ({ hovered, setHovered, clicked, setClicked }: IProps) => {
	return (
		<Container>
			{[...Array(5)].map((_, i) => {
				const ratingValue = i + 1;
				return (
					<StyledStar
						key={i}
						onMouseEnter={() => setHovered(ratingValue)}
						onMouseLeave={() => setHovered(null)}
						onClick={() => setClicked(ratingValue)}
						view={ratingValue <= (hovered || clicked) ? 1 : 0}
					/>
				);
			})}
		</Container>
	);
};

const Container = styled.div`
	display: flex;
	align-items: center;
`;

interface StarProps {
	view: number;
}

const StyledStar = styled(HiStar)<StarProps>`
	width: 1.5rem;
	height: 1.5rem;
	padding-right: 0.1rem;
	color: ${props => (props.view ? '#ff9700' : '#cccccc')};
	&:hover {
		color: #ff9700;
	}
`;

export default RatingSelect;
