import styled from 'styled-components';
import { useAppSelector } from 'redux/hooks';
import { useParams } from 'react-router-dom';

import {
	Title,
	BookDetail,
	BookImage,
	Animation,
	Main,
	TitleWrapper,
} from 'components';

//hooks
import { useGetBookDetail } from 'api/hooks/books/useGetBookDetail';

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
