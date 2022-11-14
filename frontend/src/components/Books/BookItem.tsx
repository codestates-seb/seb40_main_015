import styled from 'styled-components';
import Button from '../common/Button';

const BookItem = () => {
	return (
		<BookContainer>
			<BookInfo>
				<BookImage>IMG</BookImage>
				<BookDetail>
					<div>책 제목</div>
					<div>저자 / 출판사</div>
					<div>거래 지역</div>
				</BookDetail>
			</BookInfo>

			<BookStateWrapper>
				<Button>대여 가능</Button>
				<Button backgroundColor={'grey'}>대여중</Button>
			</BookStateWrapper>
		</BookContainer>
	);
};

const BookContainer = styled.div`
	display: flex;
	justify-content: space-between;

	padding: 14px;

	border: 1px solid rgba(1, 1, 1, 0.1);
`;
const BookImage = styled.div`
	width: 100px;
	height: 100px;
	background-color: pink;

	margin-right: 20px;
`;
const BookInfo = styled.div`
	display: flex;
`;
const BookStateWrapper = styled.div`
	display: flex;
	justify-content: center;
	align-items: center;
`;
const BookDetail = styled.div`
	display: flex;
	flex-direction: column;
	justify-content: space-between;
	padding: 5px 0;
	div {
		margin-bottom: 10px;
	}
`;

export default BookItem;
