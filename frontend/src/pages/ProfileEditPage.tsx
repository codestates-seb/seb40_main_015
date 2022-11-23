import { useState, useCallback, useRef } from 'react';
import styled from 'styled-components';
import userImage from '../assets/image/user.png';
import Title from '../components/common/Title';
import Button from '../components/common/Button';
import { HiOutlinePencilAlt } from 'react-icons/hi';
import Modal from '../components/common/Modal';
import useAPI from '../hooks/useAPI';
import axios from 'axios';

function ProfileEditPage() {
	//현재 위치 수정
	const [isOpenModal, setOpenModal] = useState<boolean>(false);
	const onClickToggleModal = useCallback(() => {
		setOpenModal(!isOpenModal);
	}, [isOpenModal]);

	//유저 이미지 수정
	const [File, setFile] = useState<File | undefined>();
	const [Image, setImage] = useState<string>(
		'https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png',
	);
	const fileInput = useRef<any>(null);
	const onChange = (e: React.ChangeEvent<HTMLInputElement>) => {
		console.log('');
		const files = e.currentTarget.files as FileList;
		if (files) {
			setFile(files[0]);
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

	return (
		<Layout>
			<Title text="내 정보 수정하기" />
			<ProfileBox>
				<img
					className="image"
					src={Image}
					alt="dummy"
					onClick={() => {
						fileInput.current.click();
					}}
				/>
				<input
					type="file"
					style={{ display: 'none' }}
					accept="image/jpg,impge/png,image/jpeg"
					name="profile_img"
					onChange={onChange}
					ref={fileInput}
				/>
				<p className="minititle">닉네임</p>
				<div className="input">
					<input placeholder="닉네임을 입력하세요" disabled={false} />
				</div>
				<p className="minititle">내 동네 설정</p>
				<div className="input">
					{isOpenModal && (
						<Modal onClickToggleModal={onClickToggleModal}></Modal>
					)}
					<input
						placeholder="내 동네를 설정하세요"
						disabled={false}
						onClick={onClickToggleModal}
					/>
				</div>
				<Button className="Button" fontSize={'small'}>
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
		width: 250px;
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
