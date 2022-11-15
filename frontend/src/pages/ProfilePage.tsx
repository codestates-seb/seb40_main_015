import { useState } from 'react';
import styled from 'styled-components';
import userImage from '../assets/image/user.png';
import MyList from '../components/common/MyList';
import TabLists from '../components/common/TabLists';
import Title from '../components/common/Title';
import { HiOutlinePencilAlt } from 'react-icons/hi';
import ProfileEditPage from './ProfileEditPage';
import useTabs from '../hooks/useTabs';
import { useNavigate } from 'react-router-dom';
import Button from '../components/common/Button';

function ProfilePage() {
	const [tab, curTab, handleChange] = useTabs(['찜 목록', '예약 목록']);

	const navigate = useNavigate();
	const handleEditPage = () => {
		navigate('/profile/edit');
	};

	return (
		<Layout>
			<Title text="마이페이지" />
			<ProfileBox>
				<img src={userImage} alt="dummy" width={80} height={100} />
				<UserInfoBox>
					<p>닉네임: 안지수</p>
					<p>주거래 동네: 강남</p>
					<p>빌려준 도서 수: 11</p>
					<HiOutlinePencilAlt className="edit" onClick={handleEditPage} />
				</UserInfoBox>
			</ProfileBox>
			<TabLists tabs={tab} handleChange={handleChange} />
			<MyList />
			<Button fontSize={'small'} className="logout">
				로그아웃
			</Button>
		</Layout>
	);
}

const Layout = styled.div`
	display: flex;
	align-items: center;
	flex-direction: column;
	padding: 1rem;
	min-width: 90%;

	.logout {
		margin-top: 20px;
		margin-bottom: 20px;
		background-color: #a4a4a4;
		padding: 10px 48px;
	}
`;

const ProfileBox = styled.div`
	width: 80%;
	display: flex;
	padding: 1.2rem;
	border: 1px solid #eaeaea;

	.edit {
		display: grid;
		position: relative;
		right: 0;
		background-color: #fbfbfb;
		color: ${props => props.theme.colors.buttonGreen};
	}
`;

const UserInfoBox = styled.div`
	display: flex;
	flex-direction: column;
	justify-content: space-between;
	margin-left: 2rem;
`;

export default ProfilePage;
