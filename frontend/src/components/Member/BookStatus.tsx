import { useNavigate } from 'react-router-dom';
import Button from '../common/Button';
import styled from 'styled-components';

interface Props {
	status: string;
	bookId?: string | number;
}

const BookStatus = ({ status, bookId }: Props) => {
	const navigate = useNavigate();

	const handleRentalPageMove = () => {
		navigate(`books/${bookId}/rental`);
	};

	const handleBookingPageMove = () => {
		navigate(`books/${bookId}/booking`);
	};
	return (
		<Container>
			{status === '대여가능' && (
				<Button fontSize="small" backgroundColor="green">
					대여 가능
				</Button>
			)}
			{status === '예약가능' && (
				<Button fontSize="small" backgroundColor="yellow" disabled>
					예약 가능
				</Button>
			)}
			{status === '대여&예약 불가능' && (
				<Button fontSize="small" backgroundColor="red" disabled>
					대여&예약 불가능
				</Button>
			)}
		</Container>
	);
};

const Container = styled.div`
	Button {
		width: 10px;
		height: 10px;
	}
`;

export default BookStatus;
