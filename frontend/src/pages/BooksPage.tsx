import styled from 'styled-components';
import BookItem from '../components/Books/BookItem';
import LongButton from '../components/Buttons/LongButton';
import Button from '../components/common/Button';

const BooksPage = () => {
	return (
		<Main>
			<BtnWrapper>
				<LongButton />
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
	width: 100vh;
	padding: 10px 20px;
`;

const Books = styled.div``;

export default BooksPage;
