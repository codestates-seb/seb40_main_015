import styled from 'styled-components';
import Button from '../common/Button';

const RentStatusButton = ({ status }: { status: string }) => {
	return (
		<StatusBox status={status}>
			{status === 'TRADING' && (
				<>
					<Button>취소</Button>
					<Button>수령 완료</Button>
				</>
			)}
			{status === 'BEING_RENTED' && (
				<Button backgroundColor="grey">대여중</Button>
			)}
			{status === 'RETURN_UNREVIEWED' && <Button>리뷰 남기기</Button>}
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
