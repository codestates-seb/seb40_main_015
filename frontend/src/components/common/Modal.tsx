import React, { PropsWithChildren, useEffect, useState } from 'react';
import styled from 'styled-components';
import Button from '../common/Button';
import useGeoLocation2 from '../../hooks/useGeoLocation2';
import Geocode from 'react-geocode';
import { useAppDispatch } from '../../redux/hooks';
import { updateUserInfo } from '../../redux/slice/userInfoSlice';

interface ModalDefaultType {
	onClickToggleModal: () => void;
}

function Modal({
	onClickToggleModal,
	children,
}: PropsWithChildren<ModalDefaultType>) {
	const location = useGeoLocation2();
	const dispatch = useAppDispatch();

	const { latitude, longitude }: any = location.coordinates;

	const [ad, setAd] = useState('');

	useEffect(() => {
		if (location.loaded === true) getAddressFromLatLng();
	}, [location]);

	const GEOCODER_KEY: any = process.env.REACT_APP_GEOCODER_KEY;
	Geocode.setLanguage('ko');
	Geocode.setApiKey(GEOCODER_KEY);
	Geocode.enableDebug();

	const getAddressFromLatLng = () => {
		Geocode.fromLatLng(latitude, longitude).then(
			response => {
				const address = response.results[4].formatted_address;
				setAd(address.slice(5));
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
						fontSize={'small'}
						className="btn1"
						onClick={() => {
							dispatch(updateUserInfo({ key: 'address', value: ad }));

							onClickToggleModal();
						}}>
						예
					</Button>
					<Button
						fontSize={'small'}
						className="btn2"
						onClick={() => {
							if (onClickToggleModal) {
								onClickToggleModal();
							}
						}}>
						아니오
					</Button>
				</div>
			</DialogBox>
			<Backdrop
				onClick={() => {
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
	position: fixed;
	top: 10%;
`;

const DialogBox = styled.dialog`
	width: 25rem;
	height: 12.5rem;
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
		font-size: 1.25rem;
		padding-bottom: 15px;
	}
	.btn {
		justify-content: space-between;
		padding-top: 20px;
	}
	.btn1 {
		margin-right: 2.5rem;
		margin-top: 0px;
		margin-bottom: 6px;
	}

	.btn2 {
		background-color: #a4a4a4;
		margin-top: 0px;
		margin-bottom: 6px;
	}

	.currentplace {
		padding-bottom: 3px;
		width: 20rem;
		height: 2rem;
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
