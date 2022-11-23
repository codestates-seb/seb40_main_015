import styled from 'styled-components';
import { Link } from 'react-router-dom';
import { useQuery } from '@tanstack/react-query';

//components
import BookItem from '../components/Books/BookItem';
import Button from '../components/common/Button';
import Title from '../components/common/Title';
import Animation from '../components/Loading/Animation';
import { dummyBooks } from '../assets/dummy/books';

//hooks
import { useBooksAPI } from '../api/books';

const BooksPage = () => {
	const { getAllBooksList } = useBooksAPI();

	const { data, isLoading } = useQuery({
		queryKey: ['allBooks'],
		queryFn: getAllBooksList,
	});

	console.log('dataL ', data);

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

			<BooksList>
				{isLoading ? (
					<Animation />
				) : (
					data?.content.map(el => (
						<BookItem
							key={+el.bookId}
							bookId={el.bookId}
							title={el.title}
							bookImage={el.bookImage}
							status={el.status}
							rentalfee={el.rentalFee}
							merchantName={el.merchantName}
						/>
					))
				)}
			</BooksList>
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
	padding: 10px 20px;
`;

const BooksList = styled.div`
	height: 70vh;
	padding: 10px;

	overflow-x: scroll;

	::-webkit-scrollbar {
		display: none;
	}
`;

const LinkStyled = styled(Link)`
	display: flex;
	flex-direction: column;
`;

export default BooksPage;
