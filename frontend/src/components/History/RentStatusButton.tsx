import styled from 'styled-components';
import Button from '../common/Button';

const RentStatusButton = ({ status }: { status: string }) => {
	return (
		<StatusBox status={status}>
			{status === 'TRADING' && (
				<>
					<Button fontSize="small">취소</Button>
					<Button fontSize="small">수령 완료</Button>
				</>
			)}
			{status === 'Rented' && (
				<Button fontSize="small" backgroundColor="grey">
					대여중
				</Button>
			)}
			{status === 'ReviewComplete' && (
				<Button fontSize="small">리뷰 남기기</Button>
			)}
			{status === 'ReviewNotComplete' && (
				<Button fontSize="small" backgroundColor="grey">
					리뷰 완료
				</Button>
			)}
			{status === 'Canceled' && (
				<Button fontSize="small" backgroundColor="grey">
					취소 완료
				</Button>
			)}
		</StatusBox>
	);
};

export default RentStatusButton;

interface StatusBoxProps {
	status: string;
}
const StatusBox = styled.div<StatusBoxProps>`
	display: flex;
	flex-direction: column;
	flex-wrap: wrap;
	justify-content: ${props =>
		props.status === 'TRADING' ? 'space-evenly' : 'center'};
	width: 5.1rem;
`;
