import { useNavigate } from 'react-router-dom';
import Button from '../common/Button';
import styled from 'styled-components';

interface Props {
	status: string;
	bookId?: string | number;
}

const BookStatus = ({ status, bookId }: Props) => {
	const navigate = useNavigate();

	const handleRentalPageMove = (e: React.SyntheticEvent) => {
		e.stopPropagation();
		navigate(`/books/${bookId}/rental`);
	};

	const handleBookingPageMove = (e: React.SyntheticEvent) => {
		e.stopPropagation();
		navigate(`/books/${bookId}/booking`);
	};
	return (
		<Container>
			{status === '대여가능' && (
				<Button className="rent" newLine={true} onClick={handleRentalPageMove}>
					대여 가능
				</Button>
			)}
			{status === '예약가능' && (
				<Button
					className="reservation"
					newLine={true}
					onClick={handleBookingPageMove}>
					예약 가능
				</Button>
			)}
			{status === '거래중' && (
				<Button className="impossible" newLine={true} disabled>
					거래중
				</Button>
			)}
		</Container>
	);
};

const Container = styled.div`
	.rent {
		border-radius: 1000px;
		background-color: #add3bc;
	}
	.reservation {
		border-radius: 1000px;
		background-color: #fff1aa;
	}
	.impossible {
		border-radius: 1000px;
		background-color: #ffc2c2;
	}
`;
export default BookStatus;
