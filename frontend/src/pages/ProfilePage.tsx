import styled from 'styled-components';
import { useNavigate } from 'react-router-dom';
import { HiOutlinePencilAlt } from 'react-icons/hi';
import { useDispatch } from 'react-redux';
import { useQuery } from '@tanstack/react-query';
import { useAppSelector } from '../redux/hooks';

// component
import PickBookList from '../components/Member/PickBookList';
import ReservationBookList from '../components/Member/ReservationBookList';
import TabLists from '../components/common/TabLists';
import Title from '../components/common/Title';
import Button from '../components/common/Button';
import Animation from '../components/Loading/Animation';

// hooks
import { useMypageAPI } from '../api/mypage';
import useTabs from '../hooks/useTabs';
// etc
import { logout } from '../redux/slice/userSlice';
import { useState } from 'react';
function ProfilePage() {
	const dispatch = useDispatch();
	const navigate = useNavigate();
	const [tab, curTab, handleChange] = useTabs(['찜 목록', '예약 목록']);
	const { id } = useAppSelector(state => state.loginInfo);
	// const [Image, setImage] = useState<string>(
	// 	'https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png',
	// );

	const handleEditPage = () => {
		navigate('/profile/edit');
	};

	interface Member {
		memberId: number;
		name: string;
		location: {
			lat: string | number;
			lon: string | number;
		} | null;
		address: string | null;
		totalBookCount: number;
		avatarUrl: string | null;
	}

	// api mypage member info
	const { getMyInfo, getPickBookList } = useMypageAPI();

	const { data, isLoading } = useQuery({
		queryKey: ['myprofile'],
		queryFn: () => getMyInfo(id),
		retry: false,
	});
	console.log('data: ', data);

	if (isLoading) return <Animation width={50} height={50} />;

	return (
		<Layout>
			<Title text="마이페이지" />
			<ProfileBox>
				<img
					src={data?.avatarUrl}
					className="profileimage"
					alt="프로필 이미지가 없습니다"></img>
				<UserInfoBox>
					<p>닉네임: {data?.name}</p>
					<p>주거래 동네:{data?.address ?? '거래 할 동네를 설정해주세요!'}</p>
					<p className="linkfrom">
						<a href="http://localhost:3000/profile/merchant/`${id}`">
							등록한 도서 수: {data?.totalBookCount}
						</a>
					</p>
					<div className="editprofile">
						<p className="edit1" onClick={handleEditPage}>
							수정하기
						</p>
						<HiOutlinePencilAlt className="edit" onClick={handleEditPage} />
					</div>
				</UserInfoBox>
			</ProfileBox>

			<TabLists tabs={tab} handleChange={handleChange} />
			{curTab === '찜 목록' && <PickBookList />}
			{curTab === '예약 목록' && <ReservationBookList />}
			{/* <MyList /> */}
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
		&:hover {
			background-color: grey;
		}
	}
`;

const ProfileBox = styled.div`
	width: 80%;
	display: flex;
	padding: 1.2rem;
	border: 1px solid #eaeaea;
	.profileimage {
		box-sizing: border-box;
		width: 100px;
		height: 100px;
		border-radius: 1000px;
		border: 0.5px solid grey;
	}
	.edit1 {
		cursor: pointer;
	}
	.edit {
		display: grid;
		position: relative;
		right: 0;
		background-color: #fbfbfb;
		color: ${props => props.theme.colors.buttonGreen};
		padding-left: 5px;
		cursor: pointer;
	}
	@media (min-width: 800px) {
		width: 800px;
	}
`;

const UserInfoBox = styled.div`
	display: flex;
	flex-direction: column;
	justify-content: space-between;
	margin-left: 2rem;

	.editprofile {
		display: flex;
		color: ${props => props.theme.colors.buttonGreen};
	}

	.linkfrom {
		&:hover {
			color: ${props => props.theme.colors.buttonGreen};
		}
	}
`;

export default ProfilePage;
