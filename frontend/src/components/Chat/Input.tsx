import React from 'react';
import { RiSendPlane2Fill } from 'react-icons/ri';
import styled from 'styled-components';

interface IProps {
	text: string;
	onChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
	onKeyDown: (e: React.KeyboardEvent<HTMLInputElement>) => void;
	onCick: () => void;
}

const Input = ({ text, onChange, onKeyDown, onCick }: IProps) => {
	return (
		<Container>
			<TextInput
				type="text"
				value={text}
				onChange={onChange}
				onKeyDown={onKeyDown}
				placeholder="메시지 보내기"
				maxLength={200}
			/>
			<SendIcon text={text} onClick={onCick} />
		</Container>
	);
};

const Container = styled.div`
	position: fixed;
	width: 100%;
	bottom: 60px;
	display: flex;
	justify-content: space-evenly;
	align-items: center;
	background-color: white;
	padding: 1rem 0;
	@media screen and (min-width: 800px) {
		width: 800px;
		bottom: 0;
		box-sizing: border-box;
		border: 1px solid #eaeaea;
	}
`;

const TextInput = styled.input`
	flex-grow: 0.9;
	height: 2.5rem;
	border: none;
	background-color: #eaeaea;
	border-radius: 50px;
	padding: 0.5rem 2rem;

	:focus {
		outline: none;
	}
`;

interface SendIconProps {
	text: string;
}

const SendIcon = styled(RiSendPlane2Fill)<SendIconProps>`
	width: 2rem;
	height: 2rem;
	color: ${props => (props.text ? props.theme.colors.main : '#a7a7a7')};
	cursor: pointer;
`;

export default Input;
