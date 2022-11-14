import { Link } from 'react-router-dom';
import styled from 'styled-components';
import BookItem from '../components/Books/BookItem';
import Button from '../components/common/Button';
import Title from '../components/common/Title';

const BooksPage = () => {
	return (
		<Main>
			<TitleWrapper>
				<Title text="동네북" isLogo={true} />
			</TitleWrapper>

			<BtnWrapper>
				<LinkStyled to={'/books/create'}>
					<Button>책 등록하기</Button>
				</LinkStyled>
			</BtnWrapper>

			<Books>
				<BookItem />
				<BookItem />
				<BookItem />
				<BookItem />
				<BookItem />
				<BookItem />
				<BookItem />
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

const BtnWrapper = styled.div`
	display: flex;
	flex-direction: column;
	width: 70vh;
	padding: 10px 20px;
`;

const Books = styled.div`
	height: 70vh;
	padding: 10px;

	overflow-x: scroll;
`;

const LinkStyled = styled(Link)`
	display: flex;
	flex-direction: column;
`;

export default BooksPage;
