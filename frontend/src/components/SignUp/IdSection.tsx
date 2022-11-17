import styled from 'styled-components';
import Input from '../common/Input';

type IdSectionDataProps = {
	idSectionData: {
		label: string;
		state: string;
		setState: Function;
	}[];
};

const IdSection = ({ idSectionData }: IdSectionDataProps) => {
	return (
		<StyledIdSection>
			{idSectionData.map(el => (
				<IdWrapper>
					<Input label={el.label} state={el.state} setState={el.setState} />
					<div className="overlapCheck">중복확인</div>
				</IdWrapper>
			))}
		</StyledIdSection>
	);
};

const StyledIdSection = styled.div`
	display: grid;
`;

const IdWrapper = styled.div`
	min-width: 117.5%;
	display: grid;
	grid-template-columns: 22rem 1px;
	.overlapCheck {
		width: 4.1rem;
		height: 1rem;
		background-color: transparent;
		color: ${props => props.theme.colors.buttonGreen};
		font-weight: bold;
		position: relative;
		top: 3.4rem;
		right: 4.1rem;
		cursor: pointer;
	}
`;

export default IdSection;
