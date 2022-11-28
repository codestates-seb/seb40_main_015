import { useState, useCallback, useRef, useEffect } from 'react';
import styled from 'styled-components';
import Title from '../components/common/Title';
import Button from '../components/common/Button';
import Modal from '../components/common/Modal';
import useGeoLocation from '../hooks/useGeoLocation';

import axios from 'axios';
import { BASE_URL } from '../constants/constants';
import { useNavigate } from 'react-router-dom';
import Photo from '../components/BooksCreate/Photo';
import Avatar from '../api/hooks/profileEdit/Avatar';
import { useFixInfo } from '../api/hooks/profileEdit/useFixInfo';

function ProfileEditPage() {
	//현재 위치 수정
	const [isOpenModal, setOpenModal] = useState<boolean>(false);
	const onClickToggleModal = useCallback(() => {
		setOpenModal(!isOpenModal);
	}, [isOpenModal]);
	const [current, setCurrent, handleCurrentLocationMove] = useGeoLocation();

	//유저 이미지 수정
	const [File, setFile] = useState<File | undefined>();
	const [Image, setImage] = useState<string>(
		'https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png',
	);
	const navigate = useNavigate();
	const fileInput = useRef<any>(null);
	const onChange = (e: React.ChangeEvent<HTMLInputElement>) => {
		const { files } = e.target;
		const formData = new FormData();
		if (files) {
			const fileRef = files[0];
			// setFile(fileRef);
			// formData.append('img', fileRef);
			axios.post(`${BASE_URL}/upload`, formData).then(res => console.log(res));
		} else {
			//업로드 취소할 시
			setImage(
				'https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png',
			);
			return;
		}

		//화면에 프로필 사진 표시
		const reader = new FileReader();
		reader.onload = () => {
			if (reader.readyState === 2 && reader.result) {
				console.log(reader);
				setImage(`${reader.result}`);
			}
		};
		reader.readAsDataURL(files[0]);
	};

	//patch mutation
	const { mutate } = useFixInfo({
		nickname: 'asdf',
		location: {
			latitude: '37.5340',
			longitude: '126.7064',
		},
		address: '서울시 서울구 서울동',
		avatarUrl:
			'https://www.pngarts.com/files/10/Default-Profile-Picture-PNG-Download-Image.png',
	});

	const [result, setResult] = useState<any>('');
	const getAdress = (ad: any) => {
		setResult(ad);
	};
	const [id, setId] = useState('');

	console.log('결과', result);
	return (
		<Layout>
			<Title text="내 정보 수정하기" />
			<ProfileBox>
				<Avatar />
				<p className="minititle">닉네임</p>
				<div className="input">
					<input
						placeholder="수정할 닉네임을 작성하세요"
						disabled={false}
						type="nickname"
						{...setId}
					/>
				</div>
				<p className="minititle">내 동네 설정</p>
				<div className="input">
					{isOpenModal && (
						<Modal
							onClickToggleModal={onClickToggleModal}
							getAdress={getAdress}></Modal>
					)}
					<input
						placeholder="내 동네를 설정하세요"
						value={result}
						disabled={false}
						onClick={() => {
							onClickToggleModal();
							handleCurrentLocationMove();
						}}
					/>
				</div>
				<Button
					onClick={() => {
						//저장 후 바로 프로필 페이지로 가지 않게 수정
						const isconfirm = window.confirm('해당 정보로 수정하시겠습니까?');
						if (!isconfirm) return;
						mutate();
						navigate('/profile');
					}}
					className="Button"
					fontSize={'small'}>
					저장
				</Button>
			</ProfileBox>
		</Layout>
	);
}

const Layout = styled.div`
	display: flex;
	align-items: center;
	flex-direction: column;
	font-size: 1.25rem;
	padding: 1rem;
	min-width: 90%;
	text-align: center;

	.username {
		padding-top: 2rem;
		padding-bottom: 0.5rem;
	}

	input {
		width: 220px;
		margin-top: 10;
		padding: 5px;
		border-radius: 5px;
		border: 0.5px solid grey;
	}

	.minititle {
		padding-top: 2rem;
		padding-bottom: 0.5rem;
		font-size: 16px;
	}
`;

const ProfileBox = styled.div`
	display: flex;
	align-items: center;
	flex-direction: column;
	width: 60%;
	position: fixed;
	top: 22%;
	padding: 1.2rem;
	border: 1px solid #eaeaea;
	background-color: rgb(244, 243, 236);
	padding-top: 50px;
	padding-bottom: 50px;

	.image {
		box-sizing: border-box;
		width: 220px;
		height: 220px;
		border-radius: 1000px;
		border: 0.5px solid grey;
		cursor: pointer;
		:hover {
			border: 2px solid ${props => props.theme.colors.buttonGreen};
		}
	}

	.Button {
		margin-top: 2.5rem;
		width: 230px;
		font-size: 16px;
	}
	.input {
		display: flex;
		text-align: center;
		align-items: center;
	}

	.editicon {
		width: 20px;
		height: 20px;
		color: ${props => props.theme.colors.buttonGreen};
		cursor: pointer;
		padding-left: 5px;
	}
`;

export default ProfileEditPage;
