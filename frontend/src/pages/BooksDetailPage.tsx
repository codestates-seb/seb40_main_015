import { useQuery } from '@tanstack/react-query';
import { useParams } from 'react-router-dom';

//conmponents
import Title from '../components/common/Title';
import Button from '../components/common/Button';
import BookDetail from '../components/Books/BookDetail';
import Animation from '../components/Loading/Animation';
import {
	Main,
	TitleWrapper,
	LinkStyled,
	BodyContainer,
} from '../components/Books/BookElements';

//hooks
import { useBooksAPI } from '../api/books';
import BookImage from '../components/Books/BookDetailimage';

const BooksDetailPage = () => {
	const { bookId } = useParams();
	const { getBookDetail } = useBooksAPI();
	const { data, isLoading } = useQuery({
		queryKey: ['book'],
		queryFn: () => getBookDetail(bookId),
	});

	console.log(isLoading, data);

	if (isLoading) return <Animation />;
	return (
		<Main>
			<TitleWrapper>
				<Title text="상세 조회" />
			</TitleWrapper>

			<BodyContainer>
				<BookImage book={data?.book} merchant={data?.merchant} />
				<BookDetail book={data?.book} merchant={data?.merchant} />
			</BodyContainer>

			{data?.book?.state === '예약불가' ? (
				<Button backgroundColor={'grey'}>대여/예약 불가</Button>
			) : data?.book?.state === '대여가능' ? (
				<LinkStyled to={`rental`}>
					<Button>책 대여하기</Button>
				</LinkStyled>
			) : (
				<LinkStyled to={`booking`}>
					<Button>책 예약하기</Button>
				</LinkStyled>
			)}
		</Main>
	);
};

export default BooksDetailPage;
