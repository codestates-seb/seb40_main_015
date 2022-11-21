import { axiosBookReceipt, axiosCancleByMerchant } from '../../api/history';
import Button from '../common/Button';

interface Props {
	status: string;
	customerName: string;
}

const LendStatusButton = ({ status, customerName }: Props) => {
	const handleStatusChange = (
		status: string,
		id: string,
		e?: React.MouseEvent<HTMLButtonElement, MouseEvent>,
	) => {
		switch (status) {
			case 'TRADING':
				const action = (e?.target as HTMLButtonElement).textContent;
				if (action === '취소') {
					axiosCancleByMerchant(id);
				}
				if (action === '반납 완료') {
					axiosBookReceipt(id);
				}
				break;
		}
	};
	return (
		<>
			{status === 'TRADING' && (
				<Button
					padding="0.8rem"
					onClick={e => {
						handleStatusChange(status, customerName, e);
					}}>
					취소
				</Button>
			)}
			{status === 'BEING_RENTED' && (
				<Button
					padding="0.8rem"
					onClick={e => {
						handleStatusChange(status, customerName, e);
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
