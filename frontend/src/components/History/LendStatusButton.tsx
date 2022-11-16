import Button from '../common/Button';

const LendStatusButton = ({ status }: { status: string }) => {
	return (
		<>
			{status === 'TRADING' && <Button padding="0.8rem">거래중 & 취소</Button>}
			{status === 'BEING_RENTED' && <Button padding="0.8rem">반납완료</Button>}
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
