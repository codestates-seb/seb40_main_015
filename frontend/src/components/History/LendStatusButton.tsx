import Button from '../common/Button';
import { useBookReturn } from './hooks/useBookReturn';
import { useCancelByMerchant } from './hooks/useCancelByMerchant';

interface Props {
	status: string;
	customerName: string;
	rental: {
		rentalId: string;
		customerName: string;
		rentalState: string;
		rentalStartedAt: string;
		rentalDeadline: string;
		rentalReturnedAt: string;
		rentalCanceledAt: string;
	};
}

const LendStatusButton = ({ status, customerName, rental }: Props) => {
	const { mutate: returnBook } = useBookReturn(rental.rentalId);
	const { mutate: cancel } = useCancelByMerchant(rental.rentalId);
	const handleStatusChange = (
		status: string,
		id: string,
		e?: React.MouseEvent<HTMLButtonElement, MouseEvent>,
	) => {
		switch (status) {
			case 'TRADING':
				cancel();
				break;
			case 'BEING_RENTED':
				returnBook();
				break;
		}
	};
	return (
		<>
			{status === 'TRADING' && (
				<Button
					padding="0.8rem"
					onClick={() => {
						handleStatusChange(status, customerName);
					}}>
					취소
				</Button>
			)}
			{status === 'BEING_RENTED' && (
				<Button
					padding="0.8rem"
					onClick={() => {
						handleStatusChange(status, customerName);
					}}>
					반납 완료
				</Button>
			)}
			{(status === 'RETURN_UNREVIEWED' || status === 'RETURN_REVIEWED') && (
				<Button padding="0.8rem" backgroundColor="grey" disabled>
					반납완료
				</Button>
			)}
			{status === 'CANCELED' && (
				<Button padding="0.8rem" backgroundColor="grey" disabled>
					취소완료
				</Button>
			)}
		</>
	);
};

export default LendStatusButton;
