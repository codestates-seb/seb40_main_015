import { useState, useCallback } from 'react';
import styled from 'styled-components';
import Title from '../components/common/Title';
import Button from '../components/common/Button';
import Modal from '../components/common/Modal';
import useGeoLocation from '../hooks/useGeoLocation';
import notify from '../utils/notify';
import { useNavigate } from 'react-router-dom';
import { useAppDispatch, useAppSelector } from '../redux/hooks';
import { updateUserInfo } from '../redux/slice/userInfoSlice';

import Avatar from '../api/hooks/profileedit/Avatar';
import { useFixInfo } from '../api/hooks/profileedit/useFixInfo';

function ProfileEditPage() {
	const goNotify = (message: string) => notify(dispatch, message);
	//현재 위치 수정
	const [isOpenModal, setOpenModal] = useState<boolean>(false);
	const onClickToggleModal = useCallback(() => {
		setOpenModal(!isOpenModal);
	}, [isOpenModal]);

	const userInfo = useAppSelector(state => state.persistedReducer.userInfo);

	console.log(userInfo);

	const [location, setLocation, handleCurrentLocationMove] = useGeoLocation();

	//유저 이미지 수정
	const { Address, avatarUrl } = userInfo;
	const [nickname, setNickname] = useState(userInfo.name);
	const navigate = useNavigate();
	const dispatch = useAppDispatch();

	//patch mutation
	const { mutate } = useFixInfo({
		nickname,
		location,
		address: Address,
		avatarUrl,
	});

	//닉네임
	const handleChangeNickname = (e: React.ChangeEvent<HTMLInputElement>) => {
		setNickname(e.target.value);
	};

	const handleBlurNickname = () => {
		dispatch(updateUserInfo({ key: 'nickname', value: nickname }));
	};

	console.log('결과', Address);
	return (
		<Layout>
			{/*  name -> nickname으로 바뀔 예정 */}
			{userInfo.name ? (
				<>
					<Title text="내 정보 수정하기" />
					<ProfileBox>
						<Avatar />
						<p className="minititle">닉네임</p>
						<div className="input">
							<input
								placeholder="수정할 닉네임을 작성하세요(15자 이하)"
								disabled={false}
								type="nickname"
								value={nickname}
								onChange={handleChangeNickname}
								onBlur={handleBlurNickname}
								maxLength={15}
							/>
						</div>
						<p className="minititle">내 동네 설정</p>
						<div className="input">
							{isOpenModal && <Modal onClickToggleModal={onClickToggleModal} />}
							<input
								placeholder="내 동네를 설정하세요"
								value={Address}
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
								const isconfirm =
									window.confirm('해당 정보로 수정하시겠습니까?');
								if (!isconfirm) return;
								mutate();
								navigate('/profile');
							}}
							className="Button"
							fontSize={'small'}>
							저장
						</Button>
					</ProfileBox>
				</>
			) : (
				'잘못된 요청입니다.'
			)}
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
