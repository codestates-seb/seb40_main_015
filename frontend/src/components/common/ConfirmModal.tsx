import { Dispatch, SetStateAction, useState } from 'react';
import { text } from 'stream/consumers';
import styled from 'styled-components';
import Button from './Button';

interface Iprops {
	text: string;
	handleClick?: () => void;
	setSubmit: Dispatch<SetStateAction<boolean>>;
}

const ConfirmModal = (props: Iprops) => {
	const [open, setOpen] = useState(false);
	const { text, handleClick, setSubmit } = props;
	const handleCloseModal = () => {
		setSubmit(false);
		setOpen(true);
	};

	return (
		<Container open={open}>
			<Box>
				<>
					{text.split('\n').map((txt: string, i: number) => (
						<Question key={i}>
							{txt}
							<br />
						</Question>
					))}
				</>
				<ButtonBox>
					<Button
						onClick={() => {
							handleCloseModal();
							props?.handleClick && props.handleClick();
						}}>
						확인
					</Button>
					<Button
						onClick={() => {
							handleCloseModal();
						}}>
						취소
					</Button>
				</ButtonBox>
			</Box>
		</Container>
	);
};

interface ContainerProps {
	open: boolean;
}

const Container = styled.div<ContainerProps>`
	display: ${props => (props.open ? 'none' : 'flex')};
	justify-content: center;
	align-items: center;
	position: fixed;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
	background-color: rgba(0, 0, 0, 0.4);
	z-index: 999;
`;

const Box = styled.div`
	position: absolute;
	width: 23rem;
	max-width: 25rem;
	height: 100px;
	display: flex;
	flex-direction: column;
	justify-content: space-between;
	padding: 40px;
	text-align: center;
	background-color: rgb(255, 255, 255);
	border-radius: 10px;
	box-shadow: 0 2px 3px 0 rgba(34, 36, 38, 0.15);
`;

const ButtonBox = styled.div`
	display: flex;
	justify-content: space-around;
	width: 50%;
	margin: 0 auto;
`;

const Question = styled.p`
	margin-bottom: 0.3rem;
	font-size: 1rem;
	font-weight: 600;
`;

export default ConfirmModal;
