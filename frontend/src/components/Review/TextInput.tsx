import React from 'react';
import styled from 'styled-components';

interface IProps {
	placeholder?: string;
	maxLength?: number;
	value: string;
	onChange: (e: React.ChangeEvent<HTMLTextAreaElement>) => void;
}

const TextInput = ({ ...props }: IProps) => {
	const { placeholder = '', maxLength = 50, value, onChange } = props;
	return (
		<Textarea
			placeholder={placeholder}
			maxLength={maxLength}
			value={value}
			onChange={onChange}
		/>
	);
};

const Textarea = styled.textarea`
	height: 8rem;
	padding: 1rem;
	margin-top: 1.5rem;
	resize: none;
	font-size: 0.7rem;
	border: 1px solid ${props => props.theme.colors.grey};
`;

export default TextInput;
