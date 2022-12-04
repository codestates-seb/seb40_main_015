import { useNavigate } from 'react-router-dom';
import styled from 'styled-components';
import Button from '../common/Button';

interface Props {
	status: string;
	bookId?: string | number;
}

const ButtonStatus = ({ status, bookId }: Props) => {
	const navigate = useNavigate();

	const handleRentalPageMove = (e: React.SyntheticEvent) => {
		e.stopPropagation();
		navigate(`/books/${bookId}`);
	};

	const handleBookingPageMove = (e: React.SyntheticEvent) => {
		e.stopPropagation();
		navigate(`/books/${bookId}`);
	};
	return (
		<>
			{status === '대여가능' && <StatusDisplay>대여 가능</StatusDisplay>}
			{status === '거래중' && <StatusDisplay>거래중</StatusDisplay>}
			{status === '대여중&예약불가' && <StatusDisplay>대여중</StatusDisplay>}
			{status === '대여중&예약가능' && <StatusDisplay>예약가능</StatusDisplay>}
		</>
	);
};

const StatusDisplay = styled.div`
	font-size: 0.7;
	color: white;
	background-color: ${props => props.theme.colors.main};
	border-radius: 5px;
	border: none;
	padding: 5px 15px;
	width: 4rem;
	text-align: center;
`;

export default ButtonStatus;
