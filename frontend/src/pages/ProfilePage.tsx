import styled from 'styled-components';
import { useNavigate } from 'react-router-dom';
import { HiOutlinePencilAlt } from 'react-icons/hi';
import { useDispatch } from 'react-redux';
import { useMutation, useQuery } from '@tanstack/react-query';
import { useAppSelector } from 'redux/hooks';

import {
	PickBookList,
	ReservationBookList,
	TabLists,
	Title,
	Button,
	Animation,
} from 'components';

// hooks
import { useMypageAPI } from 'api/mypage';
import useTabs from 'hooks/useTabs';
// etc
import { logout } from 'redux/slice/userSlice';
import { useEffect } from 'react';
import { FiExternalLink } from 'react-icons/fi';
import { useAuthAPI } from 'api/auth';

function ProfilePage() {
	const dispatch = useDispatch();
	const navigate = useNavigate();
	const [tab, curTab, handleChange] = useTabs(['찜 목록', '예약 목록']);
	const { id } = useAppSelector(state => state.loginInfo);
	const { deleteLogout } = useAuthAPI();

	//logout
	const { mutate: mutateLogout } = useMutation(deleteLogout);

	const handleEditPage = () => {
		navigate('/profile/edit');
	};

	const handleMerchantPage = () => {
		// eslint-disable-next-line no-template-curly-in-string
		navigate(`/profile/merchant/${id}`);
	};

	// api mypage member info
	const { getMyInfo, getPickBookList } = useMypageAPI();
	const { data, isLoading } = useQuery({
		queryKey: ['myprofile'],
		queryFn: () => getMyInfo(id),
		retry: false,
	});

	useEffect(() => {
		window.addEventListener('scroll', handleScroll);
		return () => {
			window.removeEventListener('scroll', handleScroll); //clean up
		};
	}, []);

	const handleScroll = () => {
		// console.log('scrolled');
	};

	if (isLoading) return <Animation width={50} height={50} />;

	return (
		<>
			{data && (
				<Layout>
					<Title text="마이페이지" />
					<ProfileBox>
						<img
							src={data?.avatarUrl}
							className="profileimage"
							alt="프로필 이미지가 없습니다"></img>
						<UserInfoBox>
							<p>닉네임: {data?.name} </p>
							<p>
								주거래 동네:{data?.address ?? '거래 할 동네를 설정해주세요!'}
							</p>
							<p className="linkfrom" onClick={handleMerchantPage}>
								등록한 도서 수: {data?.totalBookCount}
								<FiExternalLink
									className="click"
									onClick={handleMerchantPage}
								/>
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
					<Button
						fontSize={'small'}
						className="logout"
						onClick={() => {
							const isTrue = window.confirm('로그아웃 하시겠습니까?');
							if (!isTrue) return;
							mutateLogout();
							dispatch(logout());
							navigate('/login');
						}}>
						로그아웃
					</Button>
				</Layout>
			)}
		</>
	);
}

const ContainerNew = styled.div`
	width: 90%;
`;

const Layout = styled.div`
	display: flex;
	align-items: center;
	flex-direction: column;
	min-width: 90%;

	.logout {
		margin-top: 20px;
		margin-bottom: 20px;
		background-color: #a4a4a4;
		padding: 10px 48px;
		height: 3rem;
		/* width: 39rem; */
		&:hover {
			background-color: grey;
		}
	}
	.hidden {
		/* 무한스크롤 */
		display: none;
	}
`;

const ProfileBox = styled.div`
	width: 80%;
	display: flex;
	padding: 1.2rem;
	border: 1px solid #eaeaea;
	background-color: white;

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
		color: ${props => props.theme.colors.buttonGreen};
		cursor: pointer;
	}
	.click {
		padding-left: 6px;
		cursor: pointer;
	}
`;

export default ProfilePage;
