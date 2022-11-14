import styled from 'styled-components';
import BookItem from '../components/Books/BookItem';
import Button from '../components/common/Button';
import HeaderApp from '../components/Header/HeaderApp';

const BooksPage = () => {
	return (
		<Main>
			<HeaderApp />
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

const BtnWrapper = styled.div`
	display: flex;
	width: 100vh;
	/* padding: 10px 20px; */
`;

const Books = styled.div``;

export default BooksPage;
