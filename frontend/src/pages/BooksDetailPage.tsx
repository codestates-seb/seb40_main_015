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
import notify from '../utils/notify';
import { useAppDispatch, useAppSelector } from '../redux/hooks';

const BooksDetailPage = () => {
	const dispatch = useAppDispatch();
	const { isLogin } = useAppSelector(state => state.loginInfo);
	const { bookId } = useParams();
	const { getBookDetail } = useBooksAPI();
	const { data, isLoading, isFetching } = useQuery({
		queryKey: ['book'],
		queryFn: () => getBookDetail(bookId),
		onSuccess: () => {
			console.log(data);
		},
	});

	// console.log(data);
	if (isLoading) return <Animation />;
	// if (isSuccess) {
	return (
		<>
			{data && (
				<Main>
					<TitleWrapper>
						<Title text="상세 조회" />
					</TitleWrapper>

					<BodyContainer>
						<BookImage book={data?.book} merchant={data?.merchant} />
						<BookDetail book={data?.book} merchant={data?.merchant} />
					</BodyContainer>

					{/* 글 주인한테는 버튼이 어떻게 보여야할까? */}
					{data?.book?.state === '예약불가' ? (
						<Button backgroundColor={'grey'}>대여/예약 불가</Button>
					) : data?.book?.state === '대여가능' ? (
						<LinkStyled to={isLogin ? `rental` : ''}>
							<Button
								onClick={() =>
									isLogin || notify(dispatch, '로그인이 필요합니다')
								}>
								책 대여하기
							</Button>
						</LinkStyled>
					) : (
						<LinkStyled
							to={isLogin ? `booking` : ''}
							state={{
								rentalStart: data?.book.rentalStart,
								rentalEnd: data?.book.rentalEnd,
							}}>
							<Button
								onClick={() =>
									isLogin || notify(dispatch, '로그인이 필요합니다')
								}>
								책 예약하기
							</Button>
						</LinkStyled>
					)}
					{/* <LinkStyled to={`rental`}>
				<Button>책 대여하기</Button>
			</LinkStyled>
			<LinkStyled
				to={`booking`}
				state={{
					rentalStart: data?.book.rentalStart,
					rentalEnd: data?.book.rentalEnd,
				}}>
				<Button>책 예약하기</Button>
			</LinkStyled> */}
				</Main>
			)}
		</>
	);
};

export default BooksDetailPage;
