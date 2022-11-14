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
	padding: 10px 15px;
	font-size: ${props => props.theme.fontSizes.paragraph};
	border-radius: 5px;
	border: ${props => props.theme.colors.grey + ' 1px solid'};
	:focus {
		outline: none;
		border-color: ${props => props.theme.colors.buttonGreen};
	}
`;

export default Input;
