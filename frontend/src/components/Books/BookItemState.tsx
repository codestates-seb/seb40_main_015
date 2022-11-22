import { AiOutlineConsoleSql } from 'react-icons/ai';
import styled from 'styled-components';
import Button from '../common/Button';
import RentStatusButton from '../History/RentStatusButton';
import { BooksProps } from './type';

const BookItemState = ({
	status = '',
	merchantName = '',
	rental,
}: BooksProps) => {
	return (
		<BookStateWrapper>
			{/* <Button>{status}</Button> */}
			{/* <BookStatus>{status}</BookStatus> */}
			{rental?.rentalId ? (
				// rental 정보가 있는 대여내역페이지에서 렌더링
				// 빌린책과 빌리준책은 현재 로그인한 유저 닉네임과 merchantName를 비교해서 분기하면 될 듯
				<RentStatusButton status={status} merchantName={merchantName} />
			) : (
				// rental 정보가 없는 전체조회, 마이페이지에서 렌더링
				<BookStatus>{status}</BookStatus>
			)}
		</BookStateWrapper>
	);
};

const BookStateWrapper = styled.div`
	display: flex;
	justify-content: center;
	align-items: center;
`;
const BookStatus = styled.div`
	border: none;
	border-radius: 5px;
	padding: 10px;
	color: white;
	font-size: ${props => props.theme.fontSizes.paragraph};
	background-color: ${props => props.theme.colors.buttonGreen};
`;
export default BookItemState;
