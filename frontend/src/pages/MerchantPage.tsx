import styled from 'styled-components';
import dummyImage from '../assets/image/dummy.png';
import BookList from '../components/Merchant/BookList';
import TabLists from '../components/common/TabLists';
import Title from '../components/common/Title';
import useTabs from '../hooks/useTabs';

function MerchantPage() {
	const [tab, curTab, handleChange] = useTabs(['책 목록', '리뷰 보기']);

	return (
		<Layout>
			<Title text="상인 정보" />
			<ProfileBox>
				<img src={dummyImage} alt="dummy" width={80} height={100} />
				<UserInfoBox>
					<p>닉네임: 홍길동</p>
					<p>주거래 동네: 강남</p>
					<p>빌려주는 도서 수: 3</p>
					<p>평점(평균): 별별별별별</p>
				</UserInfoBox>
			</ProfileBox>
			<TabLists tabs={tab} handleChange={handleChange} />
			{curTab && curTab.name === '책 목록' ? <BookList /> : null}
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

export default MerchantPage;
