import styled from 'styled-components';
import dummyImage from '../assets/image/dummy.png';
import BookList from '../components/Merchant/BookList';
import TabLists from '../components/common/TabLists';
import Title from '../components/common/Title';
import useTabs from '../hooks/useTabs';
import Review from '../components/Review/Review';
import { useParams } from 'react-router-dom';
import Animation from '../components/Loading/Animation';
import useUserInfo from '../api/hooks/user/useUserInfo';
import ReviewList from '../components/Merchant/ReviewList';

function MerchantPage() {
	const [tab, curTab, handleChange] = useTabs(['책 목록', '리뷰 보기']);
	const urlParams = useParams();
	const merchantId = urlParams.merchantId!;

	const { data, isLoading } = useUserInfo(merchantId);

	return (
		<Layout>
			<Title text="상인 정보" />
			<ProfileBox>
				{isLoading ? (
					<Animation width={50} height={50} />
				) : (
					<>
						<img
							src={data ? data.avatarUrl : dummyImage}
							alt="유저이미지"
							width={80}
							height={100}
							className="profileimage"
						/>
						<UserInfoBox>
							<p>닉네임: {data?.name}</p>
							<p>주거래 동네: {data?.address}</p>
							<p>등록한 도서 수: {data?.totalBookCount}</p>
							<p>평점(평균): {data?.avgGrade}</p>
						</UserInfoBox>
					</>
				)}
			</ProfileBox>
			<TabLists tabs={tab} handleChange={handleChange} />
			{curTab === '책 목록' && <BookList merchantId={merchantId} />}
			{curTab === '리뷰 보기' && <ReviewList />}
		</Layout>
	);
}

const Layout = styled.div`
	display: flex;
	align-items: center;
	flex-direction: column;
	min-width: 90%;
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

	@media (min-width: 800px) {
		width: 800px;
	}
`;

const UserInfoBox = styled.div`
	display: flex;
	flex-direction: column;
	justify-content: space-around;
	margin-left: 2rem;
	font-size: ${props => props.theme.fontSizes.paragraph};
`;

export default MerchantPage;
