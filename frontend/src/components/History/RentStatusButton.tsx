import { useNavigate } from 'react-router-dom';
import styled from 'styled-components';
import Button from '../common/Button';
import { useBookReceipt } from './hooks/useBookReceipt';
import { useCancelByCustomer } from './hooks/useCancelByCustomer';

interface Props {
	status: string;
	merchantName: string;
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
const RentStatusButton = ({ status, merchantName, rental }: Props) => {
	const navigate = useNavigate();

	const { mutate: cancel } = useCancelByCustomer(rental.rentalId);
	const { mutate: receipt } = useBookReceipt(rental.rentalId);

	const handleStatusChange = (
		status: string,
		id: string,
		e?: React.SyntheticEvent,
	) => {
		e?.stopPropagation();
		switch (status) {
			case 'TRADING':
				const action = (e?.target as HTMLButtonElement).textContent;
				if (action === '취소') {
					cancel();
				}
				if (action === '수령 완료') {
					receipt();
				}
				break;
			case 'RETURN_UNREVIEWED':
				navigate('/review/create');
				break;
		}
	};
	return (
		<StatusBox status={status}>
			{status === 'TRADING' && (
				<>
					<Button onClick={e => handleStatusChange(status, merchantName, e)}>
						취소
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
		</StatusBox>
	);
};

interface StatusBoxProps {
	status: string;
}
const StatusBox = styled.div<StatusBoxProps>`
	height: 100%;
	display: flex;
	flex-direction: column;
	flex-wrap: wrap;
	justify-content: ${props =>
		props.status === 'TRADING' ? 'space-evenly' : 'center'};
	width: 7rem;
	word-break: keep-all;
	:hover {
		background-color: ${props => props.theme.colors.grey};
	}
`;

export default RentStatusButton;
