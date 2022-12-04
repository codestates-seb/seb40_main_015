import styled from 'styled-components';
import { useQuery } from '@tanstack/react-query';
import { useParams } from 'react-router-dom';
import { useAppSelector } from '../redux/hooks';

//conmponents
import Title from '../components/common/Title';
import BookDetail from '../components/Books/BookDetail';
import Animation from '../components/Loading/Animation';
import { Main, TitleWrapper } from '../components/Books/BookElements';
import BookImage from '../components/Books/BookDetailimage';

//hooks
import { useBooksAPI } from '../api/books';

const BooksDetailPage = () => {
	const { isLogin, id } = useAppSelector(state => state.loginInfo);
	const { bookId } = useParams();
	const { getBookDetail, deleteBook } = useBooksAPI();

	// 책 상세정보 받아오기 쿼리
	const {
		data,
		isLoading,
		refetch: refetchBookDetail,
	} = useQuery({
		queryKey: [bookId, 'bookDetail'],
		queryFn: () => getBookDetail(bookId, isLogin),
	});

	console.log(data);

	return (
		<Main>
			<TitleWrapper>
				<Title text="상세 조회" />
			</TitleWrapper>
			{isLoading ? (
				<LoadingSpinnerWrapper>
					<Animation width={20} height={20} />
				</LoadingSpinnerWrapper>
			) : (
				data && (
					<BodyContainer>
						<BookImage book={data?.book} merchant={data?.merchant} />
						<BookDetail
							book={data?.book}
							merchant={data?.merchant}
							refetchBookDetail={refetchBookDetail}
						/>
					</BodyContainer>
				)
			)}
		</Main>
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

const LoadingSpinnerWrapper = styled.div`
	height: 70vh;
`;

export default BooksDetailPage;
