import styled from 'styled-components';
import { useAppSelector } from '../redux/hooks';
import { useParams } from 'react-router-dom';

//conmponents
import Title from '../components/common/Title';
import BookDetail from '../components/Books/BookDetail';
import Animation from '../components/Loading/Animation';
import { Main, TitleWrapper } from '../components/Books/BookElements';
import BookImage from '../components/Books/BookDetailimage';

//hooks
import { useGetBookDetail } from '../api/hooks/books/useGetBookDetail';

const BooksDetailPage = () => {
	const { isLogin } = useAppSelector(state => state.loginInfo);
	const { bookId } = useParams();

	const { bookDetailData, isLoading, refetchBookDetail } = useGetBookDetail(
		isLogin,
		bookId,
	);

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
				bookDetailData && (
					<BodyContainer>
						<BookImage
							book={bookDetailData?.book}
							merchant={bookDetailData?.merchant}
						/>
						<BookDetail
							book={bookDetailData?.book}
							merchant={bookDetailData?.merchant}
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
