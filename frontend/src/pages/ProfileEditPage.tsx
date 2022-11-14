import { useState } from 'react';
import styled from 'styled-components';
import userImage from '../assets/image/user.png';
import Title from '../components/common/Title';
import Button from '../components/common/Button';
function ProfileEditPage() {
	return (
		<Layout>
			<Title text="내 정보 수정하기" />
			<ProfileBox>
				<img src={userImage} alt="dummy" width={260} height={300} />
				<p className="username">닉네임</p>
				<input placeholder="닉네임을 작성하세요" />
				<p className="place">내 동네 설정</p>
				<input placeholder="주거래 지역 설정을 하세요" />
				<Button className="Button" fontSize={'small'}>
					정보 수정
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
		padding-top: 1.25rem;
	}

	.place {
		padding-top: 1.25rem;
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
		margin-top: 1.25rem;
	}
`;

export default ProfileEditPage;
