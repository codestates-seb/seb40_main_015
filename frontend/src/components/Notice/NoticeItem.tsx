import { HiOutlineX } from 'react-icons/hi';
import { Link } from 'react-router-dom';
import styled from 'styled-components';
import { noticeMessages } from '../../api/hooks/notice/noticeMessages';
import { NoticeItemType } from '../../api/hooks/notice/useGetNotice';
import logo from '../../assets/image/logo1.png';

const NoticeItem = ({ noticeData }: NoticeItemType) => {
	const handleXClick = (alarmId: number) => {
		//알람삭제기능
	};

	return (
		<>
			{noticeData?.map(el => {
				const message = noticeMessages[el.alarmType];
				return (
					<Link to={message[3]} key={el.alarmId}>
						<StyledNoticeItem isRead={el.isRead}>
							<IconWrapper onClick={() => handleXClick(el.alarmId)}>
								<HiOutlineX className="icon" />
							</IconWrapper>
							<Logo src={logo} alt="로고" />
							<Notice>
								{`${message[0]} ${message[1]}하신 `}
								<span>{el.bookTitle}</span>
								{`${message[2]}`}
							</Notice>
						</StyledNoticeItem>
					</Link>
				);
			})}
		</>
	);
};

const StyledNoticeItem = styled.div<{ isRead: boolean }>`
	width: 90vw;
	max-width: 800px;
	min-height: 5rem;
	background-color: ${props =>
		props.isRead ? 'white' : props.theme.colors.unViewedNotice};
	border: ${props => props.theme.colors.grey + ' 1px solid'};
	border-radius: 5px;
	display: flex;
	align-items: center;
	padding: 0.5rem 1.5rem 0.5rem 0.5rem;
	margin-bottom: 1rem;
	position: relative;
`;

const IconWrapper = styled.div`
	height: 1.5rem;
	width: 1.5rem;
	position: absolute;
	top: 8px;
	right: 8px;
	display: flex;
	justify-content: center;
	align-items: center;

	.icon {
		color: ${props => props.theme.colors.buttonGrey};
		font-size: 1.3rem;
	}
	:hover {
		.icon {
			color: ${props => props.theme.colors.errorColor};
			font-size: 1.5rem;
		}
	}
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
