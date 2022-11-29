import { AiOutlineConsoleSql } from 'react-icons/ai';
import styled from 'styled-components';
import { useAppSelector } from '../../redux/hooks';
import Button from '../common/Button';
import LendStatusButton from '../History/LendStatusButton';
import RentStatusButton from '../History/RentStatusButton';
import { BooksProps } from './type';

const BookItemState = ({
	status = '',
	merchantName = '',
	rental,
}: BooksProps) => {
	const nickName = useAppSelector(state => state.loginInfo.nickName);
	return (
		<BookStateWrapper>
			{/* <Button>{status}</Button> */}
			{/* <BookStatus>{status}</BookStatus> */}
			{rental?.rentalId ? // rental 정보가 있는 대여내역페이지에서는 상태 버튼이 보이지 않음.
			// nickName === merchantName ? null : (
			// merchantName === '' ? null : (
			// 	<RentStatusButton
			// 		status={status}
			// 		merchantName={merchantName}
			// 		rental={rental}
			// 	/>
			// )
			null : (
				// rental 정보가 없는 전체조회, 마이페이지에서 상태 버튼 렌더링
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
