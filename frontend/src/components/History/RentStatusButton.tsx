import { useNavigate } from 'react-router-dom';
import styled from 'styled-components';
import { useHistoryAPI } from '../../api/history';
import Button from '../common/Button';

interface Props {
	status: string;
	merchantName: string;
}
const RentStatusButton = ({ status, merchantName }: Props) => {
	const { axiosBookReceipt, axiosBookReturn, axiosCancleByCustomer } =
		useHistoryAPI();
	const navigate = useNavigate();

	const handleStatusChange = (
		status: string,
		id: string,
		e?: React.MouseEvent<HTMLButtonElement, MouseEvent>,
	) => {
		switch (status) {
			case 'TRADING':
				const action = (e?.target as HTMLButtonElement).textContent;
				if (action === '취소') {
					axiosCancleByCustomer(id);
				}
				if (action === '수령 완료') {
					axiosBookReceipt(id);
				}
				break;
			case 'BEING_RENTED':
				axiosBookReturn(id);
				break;
			case 'RETURN_UNREVIEWED':
				navigate('/review/create');
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
				<Button onClick={() => handleStatusChange(status, merchantName)}>
					반납하기
				</Button>
			)}
			{status === 'RETURN_UNREVIEWED' && (
				<Button onClick={() => handleStatusChange(status, merchantName)}>
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
	display: flex;
	flex-direction: column;
	flex-wrap: wrap;
	justify-content: ${props =>
		props.status === 'TRADING' ? 'space-evenly' : 'center'};
	width: 7rem;
	background-color: white;
	word-break: keep-all;
`;

export default RentStatusButton;
