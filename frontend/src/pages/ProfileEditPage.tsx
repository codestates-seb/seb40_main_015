import { useState, useCallback } from 'react';
import styled from 'styled-components';
import userImage from '../assets/image/user.png';
import Title from '../components/common/Title';
import Button from '../components/common/Button';
import { HiOutlinePencilAlt } from 'react-icons/hi';
import Modal from '../components/common/Modal';

function ProfileEditPage() {
	const [isOpenModal, setOpenModal] = useState<boolean>(false);
	const onClickToggleModal = useCallback(() => {
		setOpenModal(!isOpenModal);
	}, [isOpenModal]);

	return (
		<Layout>
			<Title text="내 정보 수정하기" />
			<ProfileBox>
				<img src={userImage} alt="dummy" width={260} height={300} />
				<p className="minititle">닉네임</p>
				<div className="input">
					<input placeholder="닉네임을 입력하세요" />
					<HiOutlinePencilAlt className="editicon" />
				</div>
				<p className="minititle">내 동네 설정</p>
				<div className="input">
					<input placeholder="내 동네를 설정하세요" disabled={false} />
					{isOpenModal && (
						<Modal onClickToggleModal={onClickToggleModal}></Modal>
					)}
					<HiOutlinePencilAlt
						className="editicon"
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
		border-radius: 10px;
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
	width: 80%;
	position: fixed;
	top: 22%;
	padding: 1.2rem;
	border: 1px solid #eaeaea;
	background-color: rgb(239, 240, 241);
	padding-top: 50px;
	padding-bottom: 50px;
	border-radius: 5%;

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
