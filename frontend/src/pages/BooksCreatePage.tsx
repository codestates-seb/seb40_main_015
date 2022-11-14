import styled from 'styled-components';
import Title from '../components/common/Title';

function BooksCreatePage() {
	return (
		<Main>
			<TitleWrapper>
				<Title text="동네북" />
			</TitleWrapper>

			<BodyContainer>
				<BookImg></BookImg>
				<BookInfo>
					<span>도서 제목</span>
					<span>저자 / 출판사</span>
					<Wishicon />
				</BookInfo>
				<BookRentalFee>
					<span>대여료</span>
					<span>원</span>
				</BookRentalFee>
				<MerchantInfo></MerchantInfo>
				<BookDsc></BookDsc>
			</BodyContainer>
		</Main>
	);
}
const Main = styled.div`
	display: flex;
	flex-direction: column;
`;
const TitleWrapper = styled.div``;
const BodyContainer = styled.div``;
const BookImg = styled.div``;
const BookInfo = styled.div``;
const Wishicon = styled.div``;
const BookRentalFee = styled.div``;
const MerchantInfo = styled.div``;
const BookDsc = styled.div``;
export default BooksCreatePage;
