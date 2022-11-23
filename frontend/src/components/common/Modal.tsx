import React, { PropsWithChildren } from 'react';
import styled from 'styled-components';
import Button from '../common/Button';

interface ModalDefaultType {
	onClickToggleModal: () => void;
}

function Modal({
	onClickToggleModal,
	children,
}: PropsWithChildren<ModalDefaultType>) {
	return (
		<ModalContainer>
			<DialogBox>
				{children}
				<h1>현재 위치를 주거래 지역으로 설정할까요?</h1>
				<div className="btn">
					<Button className="btn1">예</Button>
					<Button className="btn2">아니오</Button>
				</div>
			</DialogBox>
			<Backdrop
				onClick={(e: React.MouseEvent) => {
					e.preventDefault();

					if (onClickToggleModal) {
						onClickToggleModal();
					}
				}}
			/>
		</ModalContainer>
	);
}

const ModalContainer = styled.div`
	width: 100%;
	height: 100%;
	align-items: center;
	justify-content: center;
`;

const DialogBox = styled.dialog`
	width: 400px;
	height: 200px;
	display: flex;
	flex-direction: column;
	align-items: center;
	justify-content: center;
	border: none;
	border-radius: 3px;
	box-shadow: 0 0 30px rgba(30, 30, 30, 0.185);
	box-sizing: border-box;
	background-color: white;
	z-index: 1000;
	align-items: center;
	justify-content: center;
	top: 30%;

	h1 {
		font-size: 20px;
	}
	.btn {
		justify-content: space-between;
		padding-top: 20px;
	}
	.btn1 {
		margin-right: 40px;
		margin-top: 10px;
	}

	.btn2 {
		background-color: #a4a4a4;
		margin-top: 10px;
	}
`;

const Backdrop = styled.div`
	width: 100vw;
	height: 100vh;
	position: fixed;
	top: 0;
	left: 0;
	z-index: 999;
	background-color: rgba(0, 0, 0, 0.2);
`;

export default Modal;
