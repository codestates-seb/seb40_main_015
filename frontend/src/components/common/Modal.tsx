import React, { PropsWithChildren } from 'react';
import styled from 'styled-components';
import Button from '../common/Button';
import useGeoLocation2 from '../../hooks/useGeoLocation2';
import { useNavigate } from 'react-router-dom';

interface ModalDefaultType {
	onClickToggleModal: () => void;
}

function Modal({
	onClickToggleModal,
	children,
}: PropsWithChildren<ModalDefaultType>) {
	const location = useGeoLocation2();
	const navigate = useNavigate();

	return (
		<ModalContainer>
			<DialogBox>
				{children}
				<h1>현재 위치를 주거래 지역으로 설정할까요?</h1>
				<div className="currentplace">
					{location.loaded
						? JSON.stringify(location)
						: '현재 위치를 알 수 없습니다.'}
				</div>
				<div className="btn">
					<Button className="btn1">예</Button>
					<Button
						className="btn2"
						onClick={(e: React.MouseEvent) => {
							e.preventDefault();
							if (onClickToggleModal) {
								onClickToggleModal();
							}
						}}>
						아니오
					</Button>
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
	z-index: 9999;
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

	.currentplace {
		margin-top: 15px;
		width: 320px;
		height: 30px;
		background-color: rgb(43, 103, 74);
		border-radius: 3px;
		color: white;
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
