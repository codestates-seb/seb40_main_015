import React, { PropsWithChildren, useEffect, useState } from 'react';
import styled from 'styled-components';
import Button from '../common/Button';
import useGeoLocation2 from '../../hooks/useGeoLocation2';
import { useNavigate } from 'react-router-dom';
import Geocode from 'react-geocode';

interface ModalDefaultType {
	onClickToggleModal: () => void;
	getAdress: (ad: string) => void;
}

function Modal({
	onClickToggleModal,
	getAdress,
	children,
}: PropsWithChildren<ModalDefaultType>) {
	const location = useGeoLocation2();
	const navigate = useNavigate();

	const { lat, lng }: any = location.coordinates;

	const [ad, setAd] = useState('');

	useEffect(() => {
		if (location.loaded === true) getAddressFromLatLng();
	}, [location]);

	const GEOCODER_KEY = 'AIzaSyDERRfqgHFEembIWc79vWHaxP9QUJifh1Q';
	// const key: any = process.env.GEOCODER_KEY;
	Geocode.setLanguage('ko');
	Geocode.setApiKey(GEOCODER_KEY);
	Geocode.enableDebug();

	const getAddressFromLatLng = () => {
		Geocode.fromLatLng(lat, lng).then(
			response => {
				const address = response.results[0].formatted_address;
				setAd(address);
			},
			error => {
				console.log(error);
			},
		);
	};

	return (
		<ModalContainer>
			<DialogBox>
				{children}
				<h1>현재 위치를 주거래 지역으로 설정할까요?</h1>
				<div className="currentplace">
					{location.loaded ? ad : '- 현재 위치를 확인 중입니다 -'}
				</div>
				<div className="btn">
					<Button
						className="btn1"
						onClick={(e: React.MouseEvent) => {
							getAdress(ad);
							e.preventDefault();
							if (onClickToggleModal) {
								onClickToggleModal();
							}
						}}>
						예
					</Button>
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
	padding-top: 20px;

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
		align-items: center;
		text-align: center;
		padding-top: 14px;
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
