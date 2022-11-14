import styled from 'styled-components';
import BookList from '../components/common/BookList';

function BooksListPage() {
	return (
		<BookInfoBox>
			<BookList />
		</BookInfoBox>
	);
}

const BookInfoBox = styled.div`
	width: 100%;
	display: flex;
	flex-direction: column;
	align-items: center;
	justify-content: flex-start;
`;

export default BooksListPage;
