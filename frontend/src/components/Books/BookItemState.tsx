import styled from 'styled-components';
import { BooksProps } from './type';

const BookItemState = ({
	status = '',
	merchantName = '',
	rental,
}: BooksProps) => {
	return (
		<BookStateWrapper>
			{rental?.rentalId ? null : (
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
	border: none;
	border-radius: 50%;
	padding: 10px;
	color: black;
	display: flex;
	text-align: center;
	font-size: 14px;
	background-color: ${props => {
		if (props.status === '대여가능') return '#DEF5E5';
		else if (props.status === '대여중&예약가능') return '#FFFAD7';
		else return '#FF9F9F';
	}};
`;
export default BookItemState;
