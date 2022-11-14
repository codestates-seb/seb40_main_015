import styled from 'styled-components';
import BookItem from '../components/Books/BookItem';
import Button from '../components/common/Button';
import Title from '../components/common/Title';
import HeaderApp from '../components/Header/HeaderApp';
import logo from '../assets/image/동네북 로고 1.png';

const BooksPage = () => {
	return (
		<Main>
			<TitleWrapper>
				<Title text="동네북" isLogo={true} />
			</TitleWrapper>
			<BtnWrapper>
				{/* <LongButton /> */}
				<Button>책 등록하기</Button>
			</BtnWrapper>

			<Books>
				<BookItem />
			</Books>
		</Main>
	);
};

const Main = styled.div`
	display: flex;
	flex-direction: column;
`;

const TitleWrapper = styled.div``;

const Logo = styled.img`
	width: 50px;
	height: 50px;
`;
const BtnWrapper = styled.div`
	display: flex;
	width: 100vh;
	/* padding: 10px 20px; */
`;

const Books = styled.div``;

export default BooksPage;
