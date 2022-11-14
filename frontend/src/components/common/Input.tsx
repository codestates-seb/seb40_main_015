import styled from 'styled-components';

type InputProps = {
	label: string;
	state: string;
	setState: Function;
	type?: string;
};

const Input = (props: InputProps) => {
	const { label, state, setState, type } = props;

	const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
		setState(e.target.value);
	};

	return (
		<StyledDiv>
			<StyledLabel>{label}</StyledLabel>
			<StyledInput type={type} value={state} onChange={handleChange} />
		</StyledDiv>
	);
};

const StyledDiv = styled.div`
	width: 100%;
	margin-top: 10px;
	display: flex;
	flex-direction: column;
`;

const StyledLabel = styled.label`
	font-size: ${props => props.theme.fontSizes.subtitle};
	font-weight: bold;
	margin-bottom: 8px;
`;

const StyledInput = styled.input`
	padding: 5px 15px;
	font-size: ${props => props.theme.fontSizes.paragraph};
`;

export default Input;
