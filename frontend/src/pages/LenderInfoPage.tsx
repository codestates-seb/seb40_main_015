import styled from 'styled-components';
import dummyImage from '../assets/image/dummy.png';
import BookList from '../components/common/BookList';

const LenderInfoPage = () => {
	return (
		<Layout>
			<Title>상인 정보</Title>
			<ProfileBox>
				<img src={dummyImage} alt="dummy" width={80} height={100} />
				<UserInfoBox>
					<p>닉네임: 홍길동</p>
					<p>주거래 동네: 강남</p>
					<p>빌려주는 도서 수: 3</p>
					<p>평점(평균): 별별별별별</p>
				</UserInfoBox>
			</ProfileBox>
			<h2>책목록</h2>
			<BookInfoBox>
				<BookList />
			</BookInfoBox>
		</Layout>
	);
};

export default LenderInfoPage;

const Layout = styled.div`
	display: flex;
	align-items: center;
	flex-direction: column;
	padding: 1rem;
	h2 {
		font-size: 2rem;
		margin-bottom: 1rem;
	}
`;

const Title = styled.p`
	width: 100%;
	font-size: 2.5rem;
	text-align: center;
	padding-bottom: 1rem;
	border-bottom: 1px solid #a7a7a7;
`;

const ProfileBox = styled.div`
	width: 80%;
	display: flex;
	padding: 1.2rem;
	border: 1px solid #eaeaea;
	margin: 1rem 0;
`;

const UserInfoBox = styled.div`
	display: flex;
	flex-direction: column;
	justify-content: space-between;
	margin-left: 2rem;
`;

const BookInfoBox = styled.div`
	width: 100%;
	display: flex;
	flex-direction: column;
	align-items: center;
	justify-content: flex-start;
`;
