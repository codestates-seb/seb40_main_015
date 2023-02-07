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
			{[...Array(5)].map((star, i) => {
				const ratingValue = i + 1;
				return (
					<Label key={i}>
						<StyledStar
							onMouseEnter={() => setHovered(ratingValue)}
							onMouseLeave={() => setHovered(null)}
							onClick={() => setClicked(ratingValue)}
							view={ratingValue <= (hovered || clicked) ? 1 : 0}
						/>
					</Label>
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
	color: ${props => (props.view ? '#ff9700' : '#cccccc')};
	&:hover {
		color: #ff9700;
	}
`;

const Label = styled.label`
	margin-right: 0.1rem;
`;

export default RatingSelect;
