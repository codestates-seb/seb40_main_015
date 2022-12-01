import { useQuery } from '@tanstack/react-query';
import { useNavigate, useParams } from 'react-router-dom';

//conmponents
import Title from '../components/common/Title';
import BookDetail from '../components/Books/BookDetail';
import Animation from '../components/Loading/Animation';
import { Main, TitleWrapper } from '../components/Books/BookElements';

//hooks
import { useBooksAPI } from '../api/books';
import BookImage from '../components/Books/BookDetailimage';
import notify from '../utils/notify';
import { useAppDispatch, useAppSelector } from '../redux/hooks';
import styled from 'styled-components';

const BooksDetailPage = () => {
	const { isLogin, id } = useAppSelector(state => state.loginInfo);
	const { bookId } = useParams();
	const { getBookDetail, deleteBook } = useBooksAPI();

	// 책 상세정보 받아오기 쿼리
	const { data, isLoading } = useQuery({
		queryKey: ['bookDetail'],
		queryFn: () => getBookDetail(bookId, isLogin),
		onSuccess: data => {
			console.log('book detail: ', data);
		},
	});

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
				</Main>
			)}
		</>
	);
};

const BodyContainer = styled.div`
	display: flex;
	flex-direction: column;
	justify-content: center;
	align-items: center;

	@media screen and (min-width: 801px) {
		width: 800px;
		flex-direction: row;
		justify-content: space-between;
		align-items: flex-start;
	}
`;

export default BooksDetailPage;
