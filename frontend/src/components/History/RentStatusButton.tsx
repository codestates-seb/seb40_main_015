import { useNavigate } from 'react-router-dom';
import Button from '../common/Button';
import { useBookReceipt } from '../../api/hooks/history/useBookReceipt';
import { useCancelByCustomer } from '../../api/hooks/history/useCancelByCustomer';

interface Props {
	status: string;
	merchantName: string;
	bookId: number;
	title: string;
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

const RentStatusButton = ({
	status,
	merchantName,
	rental,
	bookId,
	title,
}: Props) => {
	const navigate = useNavigate();

	const { mutate: cancel } = useCancelByCustomer(rental.rentalId);
	const { mutate: receipt } = useBookReceipt(rental.rentalId);

	const handleStatusChange = (
		status: string,
		id: string,
		e?: React.SyntheticEvent,
	) => {
		// e?.stopPropagation();
		switch (status) {
			case 'TRADING':
				const action = (e?.target as HTMLButtonElement).textContent;
				if (action === '취소하기') {
					const istrue = window.confirm(`${id}님과의 거래를 취소하시겠습니까?`);
					istrue && cancel();
				}
				if (action === '수령 완료') {
					const istrue = window.confirm(
						`${id}님으로부터 대여 신청한 도서를 받으셨나요?`,
					);
					istrue && receipt();
				}
				break;
			case 'RETURN_UNREVIEWED':
				const istrue = window.confirm(`${id}님에게 리뷰를 작성하시겠습니까?`);
				istrue &&
					navigate({
						pathname: '/review/create',
						search: `?rentalId=${rental.rentalId}&id=${id}&bookId=${bookId}&title=${title}`,
					});
				break;
		}
	};
	return (
		<>
			{status === 'TRADING' && (
				<>
					<Button
						className="cancel"
						onClick={e => handleStatusChange(status, merchantName, e)}>
						취소하기
					</Button>
					<Button onClick={e => handleStatusChange(status, merchantName, e)}>
						수령 완료
					</Button>
				</>
			)}
			{status === 'BEING_RENTED' && (
				<Button backgroundColor="grey">대여중</Button>
			)}
			{status === 'RETURN_UNREVIEWED' && (
				<Button onClick={e => handleStatusChange(status, merchantName, e)}>
					리뷰 남기기
				</Button>
			)}
			{status === 'RETURN_REVIEWED' && (
				<Button backgroundColor="grey">리뷰 완료</Button>
			)}
			{status === 'CANCELED' && (
				<Button backgroundColor="grey">취소 완료</Button>
			)}
		</>
	);
};

export default RentStatusButton;
