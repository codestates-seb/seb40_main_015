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
	const nickname = useAppSelector(state => state.loginInfo.nickname);
	return (
		<BookStateWrapper>
			{/* <Button>{status}</Button> */}
			{/* <BookStatus>{status}</BookStatus> */}
			{rental?.rentalId ? null : ( // ) // 	/> // 		rental={rental} // 		merchantName={merchantName} // 		status={status} // 	<RentStatusButton // merchantName === '' ? null : ( // nickName === merchantName ? null : ( // rental 정보가 있는 대여내역페이지에서는 상태 버튼이 보이지 않음.
				// rental 정보가 없는 전체조회, 마이페이지에서 상태 버튼 렌더링
				<BookStatus status={status}>
					{status === '대여가능'
						? '대여가능'
						: status === '대여중&예약가능'
						? '예약가능'
						: '예약불가'}
				</BookStatus>
			)}
		</BookStateWrapper>
	);
};
interface IBookstatus {
	status: string;
}
const BookStateWrapper = styled.div`
	/* width: 4rem; */
	display: flex;
	justify-content: center;
	/* align-items: center; */
`;
const BookStatus = styled.div<IBookstatus>`
	width: 30px;
	height: 30px;
	/* height: 100%; */
	border: none;
	border-radius: 50%;
	padding: 10px;
	/* color: white; */
	color: black;
	display: flex;
	text-align: center;
	/* font-size: ${props => props.theme.fontSizes.paragraph}; */
	font-size: 14px;
	/* background-color: ${props => props.theme.colors.buttonGreen}; */
	background-color: ${props => {
		if (props.status === '대여가능') return '#DEF5E5';
		else if (props.status === '대여중&예약가능') return '#FFFAD7';
		else return '#FF9F9F';
	}};
`;
export default BookItemState;
