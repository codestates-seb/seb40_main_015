import { useQuery, useMutation } from '@tanstack/react-query';
import { useNavigate, useParams } from 'react-router-dom';

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
	const { isLogin, id } = useAppSelector(state => state.loginInfo);
	const { bookId } = useParams();
	const navigate = useNavigate();
	const { getBookDetail, deleteBook } = useBooksAPI();

	// 책 상세정보 받아오기 쿼리
	const { data, isLoading } = useQuery({
		queryKey: ['book'],
		queryFn: () => getBookDetail(bookId),
		onSuccess: () => {
			console.log('book detail: ', data);
		},
	});
	// 삭제하기 delete 요청 쿼리
	const { mutate: mutateDelete } = useMutation({
		mutationFn: () => deleteBook(data?.book?.bookId),
	});

	// event handler
	const HandleDelete = () => {
		const result = window.confirm('대여 종료하시겠습니까?');
		result && mutateDelete();
		result && navigate('/books');
		result && notify(dispatch, '삭제가 완료되었습니다.');
	};

	if (isLoading)
		return (
			<Main>
				<Animation width={20} height={20} />
			</Main>
		);
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

					{id === data.merchant?.merchantId &&
					data?.book?.state !== '거래중단' ? (
						<Button onClick={HandleDelete}>대여 종료</Button>
					) : data?.book?.state === '대여가능' ? (
						<LinkStyled to={isLogin ? `rental` : ''}>
							<Button
								onClick={() =>
									isLogin || notify(dispatch, '로그인이 필요합니다')
								}>
								책 대여하기
							</Button>
						</LinkStyled>
					) : data?.book?.state === '대여중&예약가능' ? (
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
					) : (
						<Button backgroundColor={'grey'}>대여/예약 불가</Button>
					)}
				</Main>
			)}
		</>
	);
};

export default BooksDetailPage;
