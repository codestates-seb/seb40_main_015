import convertDate from '../../utils/convertDate';
import styled from 'styled-components';
import { RentalProps } from '../Books/type';
import { useChatAPI } from '../../api/chat';
import { useNavigate } from 'react-router';
import { useAppSelector } from '../../redux/hooks';

interface ILendBookUserInfo {
	rentalInfo: RentalProps;
	merchantName?: string;
	merchantId?: string | number;
	bookId?: number;
}

export const LendBookUserInfo = ({
	rentalInfo,
	merchantName,
	merchantId,
	bookId,
}: ILendBookUserInfo) => {
	const { id } = useAppSelector(state => state.loginInfo);
	const { axiosCreateRoom } = useChatAPI();
	const navigate = useNavigate();

	const handleChatClick = () => {
		axiosCreateRoom(merchantId!, id, bookId!).then(res => {
			navigate(`/chats/${res}`);
		});
	};

	return (
		<UserInfoBox>
			<span>
				{merchantName
					? `상인 이름: ${merchantName}`
					: `주민 이름: ${rentalInfo.customerName}`}
			</span>
			<span>
				대여 기간:{' '}
				{(rentalInfo.rentalState === 'TRADING' ||
					rentalInfo.rentalState === 'BEING_RENTED') &&
					convertDate(
						rentalInfo.rentalStartedAt,
						rentalInfo.rentalDeadline,
						true,
					)}
				{(rentalInfo.rentalState === 'RETURN_UNREVIEWED' ||
					rentalInfo.rentalState === 'RETURN_REVIEWED') &&
					convertDate(
						rentalInfo.rentalStartedAt,
						rentalInfo.rentalReturnedAt,
						true,
					)}
				{rentalInfo.rentalState === 'CANCELED' &&
					convertDate(
						rentalInfo.rentalStartedAt,
						rentalInfo.rentalCanceledAt,
						true,
					)}
			</span>
			{merchantName && <ChatButton onClick={handleChatClick}>채팅</ChatButton>}
		</UserInfoBox>
	);
};

const UserInfoBox = styled.div`
	border: 1px solid #eaeaea;
	border-radius: 5px;
	display: flex;
	justify-content: space-evenly;
	align-items: center;
	padding: 1rem 0;
	background-color: white;
	span {
		font-size: ${props => props.theme.fontSizes.paragraph};
	}
`;

const ChatButton = styled.button`
	background-color: #ffc700;
	border: none;
	padding: 0.3rem 1rem;
	border-radius: 5px;
	cursor: pointer;
	:hover {
		background-color: #e8b601;
	}
`;
export default LendBookUserInfo;
