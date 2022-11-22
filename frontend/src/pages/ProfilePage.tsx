import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import styled from 'styled-components';
import { HiOutlinePencilAlt } from 'react-icons/hi';
import { useDispatch } from 'react-redux';

// component
import MyList from '../components/common/MyList';
import TabLists from '../components/common/TabLists';
import Title from '../components/common/Title';
import Button from '../components/common/Button';
import ProfileEditPage from './ProfileEditPage';

// etc
import useTabs from '../hooks/useTabs';
import userImage from '../assets/image/user.png';
import { logout } from '../redux/slice/userSlice';
import { dummyBookWish } from '../assets/dummy/books';
import BookItem from '../components/Books/BookItem';

function ProfilePage() {
	const [tab, curTab, handleChange] = useTabs(['찜 목록', '예약 목록']);
	const navigate = useNavigate();
	const dispatch = useDispatch();
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

			{dummyBookWish?.map(el => {
				return (
					<ContainerNew key={+el.bookId}>
						<BookItem
							// key={+el.bookId}
							bookId={el.bookId}
							title={el.title}
							bookImage={el.imageUrl}
							rentalfee={+el.rentalFee}
							status={el.status}
						/>
					</ContainerNew>
				);
			})}

			<MyList />
			<Button
				fontSize={'small'}
				className="logout"
				onClick={() => {
					dispatch(logout());
					navigate('/books');
				}}>
				로그아웃
			</Button>
		</Layout>
	);
}

const ContainerNew = styled.div`
	width: 90%;
`;

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
