import { useNavigate } from 'react-router-dom';
import Button from '../common/Button';

interface Props {
	status: string;
	bookId?: string | number;
}

const ButtonStatus = ({ status, bookId }: Props) => {
	const navigate = useNavigate();

	const handleRentalPageMove = () => {
		navigate(`books/${bookId}/rental`);
	};

	const handleBookingPageMove = () => {
		navigate(`books/${bookId}/booking`);
	};
	return (
		<>
			{status === '대여가능' && (
				<Button fontSize="small" onClick={handleRentalPageMove}>
					대여 가능
				</Button>
			)}
			{status === '거래중' && (
				<Button fontSize="small" backgroundColor="grey" disabled>
					거래중
				</Button>
			)}
			{status === '대여중&예약불가' && (
				<Button fontSize="small" backgroundColor="grey" disabled>
					대여중
				</Button>
			)}
			{status === '대여중&예약가능' && (
				<Button fontSize="small" onClick={handleBookingPageMove}>
					예약가능
				</Button>
			)}
		</>
	);
};

export default ButtonStatus;
