import styled from 'styled-components';
import dummyImage from '../assets/image/dummy.png';
import BookList from '../components/Merchant/BookList';
import TabLists from '../components/common/TabLists';
import Title from '../components/common/Title';
import useTabs from '../hooks/useTabs';
import Review from '../components/Merchant/Review';
import { useMypageAPI } from '../api/mypage';
import { useParams } from 'react-router-dom';
import { useQuery } from '@tanstack/react-query';
import { MemberInfo } from '../queryType/members';
import Animation from '../components/Loading/Animation';

function MerchantPage() {
	const [tab, curTab, handleChange] = useTabs(['책 목록', '리뷰 보기']);
	const { getMemberInfo } = useMypageAPI();
	const urlParams = useParams();
	const merchantId = urlParams.merchantId;

	const { data, isLoading } = useQuery<MemberInfo>(
		['merchant'],
		() => getMemberInfo(merchantId),
		{ retry: false },
	);

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
						/>
						<UserInfoBox>
							<p>닉네임: {data?.name}</p>
							<p>주거래 동네: {data?.address}</p>
							<p>빌려주는 도서 수: {data?.totalBookCount}</p>
							<p>평점(평균): {data?.avgGrade}</p>
						</UserInfoBox>
					</>
				)}
			</ProfileBox>
			<TabLists tabs={tab} handleChange={handleChange} />
			{curTab === '책 목록' && <BookList merchantId={merchantId} />}
			{curTab === '리뷰 보기' && <Review />}
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
