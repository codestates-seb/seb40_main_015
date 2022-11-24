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
} from '../components/Books/BookElements';

//hooks
import { useBooksAPI } from '../api/books';

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

			<BookDetail book={data?.book} merchant={data?.merchant} />

			<LinkStyled to={`rental`}>
				<Button>책 대여하기</Button>
			</LinkStyled>
			<LinkStyled to={`booking`}>
				<Button>책 예약하기</Button>
				<Button backgroundColor={'grey'}>예약 불가</Button>
			</LinkStyled>
			<Button backgroundColor={'grey'}>예약 불가</Button>
		</Main>
	);
};

export default BooksDetailPage;
