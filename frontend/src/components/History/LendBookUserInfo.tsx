import convertDate from '../../utils/convertDate';
import styled from 'styled-components';
import { RentalProps } from '../Books/type';

interface ILendBookUserInfo {
	rentalInfo: RentalProps;
}

// export const LendBookUserInfo = ({
// 	customerName,
// 	rentalState,
// 	rentalStartedAt,
// 	rentalDeadline,
// 	rentalReturnedAt,
// 	rentalCanceledAt,
// }: RentalProps) => {
export const LendBookUserInfo = ({ rentalInfo }: ILendBookUserInfo) => {
	return (
		<UserInfoBox>
			<span>주민: {rentalInfo.customerName}</span>
			{(rentalInfo.rentalState === 'TRADING' ||
				rentalInfo.rentalState === 'BEING_RENTED') && (
				<p>
					대여기간:{' '}
					{convertDate(
						rentalInfo.rentalStartedAt,
						rentalInfo.rentalDeadline,
						true,
					)}
				</p>
			)}
			{(rentalInfo.rentalState === 'RETURN_UNREVIEWED' ||
				rentalInfo.rentalState === 'RETURN_REVIEWED') && (
				<p>
					대여기간:{' '}
					{convertDate(
						rentalInfo.rentalStartedAt,
						rentalInfo.rentalReturnedAt,
						true,
					)}
				</p>
			)}
			{rentalInfo.rentalState === 'CANCELED' && (
				<p>
					대여기간:{' '}
					{convertDate(
						rentalInfo.rentalStartedAt,
						rentalInfo.rentalCanceledAt,
						true,
					)}
				</p>
			)}
		</UserInfoBox>
	);
};

const UserInfoBox = styled.div`
	border: 1px solid #eaeaea;
	border-radius: 5px;
	display: flex;
	justify-content: space-evenly;
	/* margin-bottom: 1rem; */
	padding: 1rem 0;
	background-color: white;
	span {
		font-size: ${props => props.theme.fontSizes.paragraph};
	}
`;
export default LendBookUserInfo;
