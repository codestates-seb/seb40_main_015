import styled from 'styled-components';
import dummyImage from '../assets/image/dummy.png';
import BookList from '../components/common/BookList';

const LendHistoryPage = () => {
	return <Layout>빌려준 책 목록</Layout>;
};

export default LendHistoryPage;

const Layout = styled.div`
	display: flex;
	align-items: center;
	flex-direction: column;
	min-width: 425px;
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
