import { useState } from 'react';
import styled from 'styled-components';
import { useAppDispatch } from '../../redux/hooks';
import { updateRentalFee } from '../../redux/slice/bookCreateSlice';
import notify from '../../utils/notify';
import { BookInfo } from '../Books/BookElements';

const RentalFee = () => {
	const [fee, setFee] = useState<number | undefined>();
	const [isValid, setIsValid] = useState(true);
	const dispatch = useAppDispatch();

	const handleChangeFee = (e: React.ChangeEvent<HTMLInputElement>) => {
		setFee(Number(e.target.value));
	};
	const handleBlurInput = () => {
		if (fee && fee / 100 !== Math.floor(fee / 100)) {
			setIsValid(false);
			notify(dispatch, '대여료는 100원 단위여야 합니다.');
		} else {
			setIsValid(true);
			dispatch(updateRentalFee(fee));
		}
	};

	return (
		<BookInfo>
			<StyledInput
				className="book--info__fee"
				type="number"
				placeholder="대여료 (100원 단위)"
				value={fee || ''}
				onChange={handleChangeFee}
				onBlur={handleBlurInput}
				isValid={isValid}
			/>
			<span>원 </span>
		</BookInfo>
	);
};

const StyledInput = styled.input<{ isValid: boolean }>`
	color: ${props => (props.isValid ? 'black' : props.theme.colors.errorColor)};
`;

export default RentalFee;
