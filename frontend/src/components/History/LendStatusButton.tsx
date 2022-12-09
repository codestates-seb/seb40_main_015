import Button from '../common/Button';
import { useBookReturn } from '../../api/hooks/history/useBookReturn';
import { useCancelByMerchant } from '../../api/hooks/history/useCancelByMerchant';

interface Props {
	status: string;
	customerName: string;
	rental: {
		rentalId: number;
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
				const istrue = window.confirm(`${id}님과의 거래를 취소하시겠습니까?`);
				istrue && cancel();
				break;
			case 'BEING_RENTED':
				const isTrue = window.confirm(`도서를 반납처리 하시겠습니까?`);
				isTrue && returnBook();
				break;
		}
	};
	return (
		<>
			{status === 'TRADING' && (
				<Button
					onClick={() => {
						handleStatusChange(status, customerName);
					}}>
					거래 취소
				</Button>
			)}
			{status === 'BEING_RENTED' && (
				<Button
					onClick={() => {
						handleStatusChange(status, customerName);
					}}>
					반납 완료
				</Button>
			)}
			{(status === 'RETURN_UNREVIEWED' || status === 'RETURN_REVIEWED') && (
				<Button backgroundColor="grey" disabled>
					반납 완료
				</Button>
			)}
			{status === 'CANCELED' && (
				<Button backgroundColor="grey" disabled>
					취소 완료
				</Button>
			)}
		</>
	);
};

export default LendStatusButton;
