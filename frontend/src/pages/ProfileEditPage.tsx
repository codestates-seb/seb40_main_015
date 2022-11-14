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
				<p>닉네임</p>
				<input placeholder="닉네임을 작성하세요" />
				<p>내 동네</p>
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

	.Button {
		display: flex;
		align-items: center;
		flex-direction: column;
		align-items: center;
		text-align: center;
	}
`;

const ProfileBox = styled.div`
	width: 80%;
	padding: 1.2rem;
	border: 1px solid #eaeaea;
`;

export default ProfileEditPage;
