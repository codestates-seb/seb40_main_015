import styled from 'styled-components';
import { Link } from 'react-router-dom';

//components
import Button from '../common/Button';
//data
import BookImageDummy from '../../assets/image/dummy.png';

const BookItem = () => {
	return (
		<BookContainer to={'1'}>
			<BookInfo>
				<BookImage>
					<img src={BookImageDummy} />
				</BookImage>
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

const BookContainer = styled(Link)`
	display: flex;
	justify-content: space-between;

	padding: 14px;
	margin: 10px 0;

	border: 1px solid rgba(1, 1, 1, 0.1);

	cursor: pointer;
`;
const BookImage = styled.div`
	background-color: pink;
	margin-right: 20px;

	img {
		width: 100px;
		height: 100px;
	}
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
