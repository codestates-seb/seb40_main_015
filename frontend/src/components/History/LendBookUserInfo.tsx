import convertDate from '../../utils/convertDate';
import styled from 'styled-components';
import { RentalProps } from '../Books/type';

interface ILendBookUserInfo {
	rentalInfo: RentalProps;
	merchantName?: string;
}

export const LendBookUserInfo = ({
	rentalInfo,
	merchantName,
}: ILendBookUserInfo) => {
	return (
		<UserInfoBox>
			<span>
				{merchantName
					? `상인 이름: ${merchantName}`
					: `주민 이름: ${rentalInfo.customerName}`}
			</span>
			{(rentalInfo.rentalState === 'TRADING' ||
				rentalInfo.rentalState === 'BEING_RENTED') && (
				<span>
					대여 기간:{' '}
					{convertDate(
						rentalInfo.rentalStartedAt,
						rentalInfo.rentalDeadline,
						true,
					)}
				</span>
			)}
			{(rentalInfo.rentalState === 'RETURN_UNREVIEWED' ||
				rentalInfo.rentalState === 'RETURN_REVIEWED') && (
				<span>
					대여기간:{' '}
					{convertDate(
						rentalInfo.rentalStartedAt,
						rentalInfo.rentalReturnedAt,
						true,
					)}
				</span>
			)}
			{rentalInfo.rentalState === 'CANCELED' && (
				<span>
					대여기간:{' '}
					{convertDate(
						rentalInfo.rentalStartedAt,
						rentalInfo.rentalCanceledAt,
						true,
					)}
				</span>
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
