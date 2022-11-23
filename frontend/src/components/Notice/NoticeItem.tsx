import styled from 'styled-components';
import logo from '../../assets/image/logo1.png';

export type NoticeItemType = {
	noticeData: noticeDataType[];
};

export type noticeDataType = {
	[key: string]: string | number | boolean;
	id: number;
	type:
		| 'reservation'
		| 'return'
		| 'rental'
		| 'merchantCancellation'
		| 'residentCancellation';
	title: string;
	isViewed: boolean;
};

const NoticeItem = ({ noticeData }: NoticeItemType) => {
	return (
		<>
			{noticeData.map(el => {
				const message = noticeMessages[el.type];
				return (
					<StyledNoticeItem isViewed={el.isViewed} key={el.id}>
						<Logo src={logo} alt="로고" />
						<Notice>
							{`${message[0]} ${message[1]}하신 `}
							<span>{el.title}</span>
							{`${message[2]}`}
						</Notice>
					</StyledNoticeItem>
				);
			})}
		</>
	);
};

const noticeMessages = {
	reservation: ['💌', '예약', '의 대여가 가능합니다.'],
	return: ['⏰', '대여', '의 대여 반납이 하루 남았습니다.'],
	rental: ['📚', '등록', '대여 신청이 접수되었습니다.'],
	merchantCancellation: ['❌', '신청', '의 대여가 취소되었습니다.'],
	residentCancellation: ['❌', '등록', '의 대여가 취소되었습니다.'],
};

const StyledNoticeItem = styled.div<{ isViewed: boolean }>`
	width: 95%;
	max-width: 1000px;
	min-height: 5rem;
	background-color: ${props =>
		props.isViewed ? 'white' : props.theme.colors.unViewedNotice};
	border: ${props => props.theme.colors.grey + ' 1px solid'};
	border-radius: 5px;
	display: flex;
	align-items: center;
	padding: 0.5rem;
	margin: 0 0.5rem 1rem 0.5rem;
`;

const Logo = styled.img`
	width: 4rem;
	height: 4rem;
	margin: 0 1rem;
`;

const Notice = styled.div`
	font-size: ${props => props.theme.fontSizes.paragraph};
	span {
		font-size: inherit;
		font-weight: bold;
		color: ${props => props.theme.colors.buttonGreen};
	}
`;

export default NoticeItem;
