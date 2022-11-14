import { useState } from 'react';
import styled from 'styled-components';
import userImage from '../assets/image/user.png';
import BookList from '../components/common/BookList';
import TabLists from '../components/common/TabLists';
import Title from '../components/common/Title';
import { HiOutlinePencilAlt } from 'react-icons/hi';

function ProfilePage() {
	const [tab, setTab] = useState(['찜 목록', '예약 목록']);
	return (
		<Layout>
			<Title text="마이페이지" />
			<ProfileBox>
				<img src={userImage} alt="dummy" width={80} height={100} />
				<UserInfoBox>
					<p>닉네임: 안지수</p>
					<p>주거래 동네: 강남</p>
					<p>빌려준 도서 수: 11</p>
					<HiOutlinePencilAlt />
				</UserInfoBox>
			</ProfileBox>
			<TabLists tabs={tab} />
			<BookList />
		</Layout>
	);
}

const Layout = styled.div`
	display: flex;
	align-items: center;
	flex-direction: column;
	padding: 1rem;
	min-width: 90%;
`;

const ProfileBox = styled.div`
	width: 80%;
	display: flex;
	padding: 1.2rem;
	border: 1px solid #eaeaea;
`;

const UserInfoBox = styled.div`
	display: flex;
	flex-direction: column;
	justify-content: space-between;
	margin-left: 2rem;
`;

export default ProfilePage;
