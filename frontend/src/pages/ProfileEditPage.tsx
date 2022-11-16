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
				<p className="username">닉네임</p>
				<input placeholder="닉네임을 입력하세요" />
				<HiOutlinePencilAlt className="editicon" />
				<p className="place">내 동네 설정</p>
				<input placeholder="내 동네를 설정하세요" disabled={true} />
				{isOpenModal && <Modal onClickToggleModal={onClickToggleModal}></Modal>}
				<HiOutlinePencilAlt onClick={onClickToggleModal} />
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
		width: 250px;
		margin-top: 10;
		padding: 5px;
		border-radius: 10px;
		border: 0.5px solid grey;
	}

	.place {
		padding-top: 1.25rem;
		padding-bottom: 0.5rem;
	}

	.editicon {
	}
`;

const ProfileBox = styled.div`
	display: flex;
	align-items: center;
	flex-direction: column;
	width: 80%;
	padding: 1.2rem;
	border: 1px solid #eaeaea;

	.Button {
		margin-top: 2rem;
	}
	.input {
	}
`;

export default ProfileEditPage;
