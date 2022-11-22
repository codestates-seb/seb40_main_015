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
import { useEffect } from 'react';

const BooksPage = () => {
	const { getAllBooksList } = useBooksAPI();

	// useEffect(() => {
	// 	fetch('http://13.124.11.174:8080/books')
	// 		.then(res => res.json())
	// 		.then(res => console.log(res))
	// 		.catch(err => console.error(err));
	// }, []);

	const { data, isLoading } = useQuery({
		queryKey: ['allBooks'],
		queryFn: getAllBooksList,
	});

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
					data?.data.content.map(el => (
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
