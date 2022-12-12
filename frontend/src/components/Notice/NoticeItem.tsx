import { HiOutlineX } from 'react-icons/hi';
import { useNavigate } from 'react-router-dom';
import styled from 'styled-components';
import { NOTICE_MESSAGES } from '../../api/hooks/notice/noticeMessages';
import useDeleteNotice from '../../api/hooks/notice/useDeleteNotice';
import { NoticeItemType } from '../../api/hooks/notice/useGetNotice';
import logo from '../../assets/image/logo1.png';
import { CONFIRM_MESSAGES, MESSAGES } from '../../constants/constants';

const WINDOWS = 'Windows';

const NoticeItem = ({ noticeData }: NoticeItemType) => {
	const navigate = useNavigate();
	const { mutate } = useDeleteNotice();

	const handleClickIcon = (
		e: React.MouseEvent<HTMLDivElement, MouseEvent>,
		alarmId: number,
	) => {
		e.stopPropagation();
		if (window.confirm(CONFIRM_MESSAGES.delete)) {
			mutate(alarmId, {
				onSuccess: () => {
					if (navigator.userAgent.includes(WINDOWS)) window.location.reload();
				},
			});
		} else {
			return;
		}
	};

	return (
		<Container>
			{noticeData && noticeData.length === 0 && (
				<NoData>{MESSAGES.noNotice}</NoData>
			)}
			{noticeData?.map(el => {
				const message = NOTICE_MESSAGES[el.alarmType];
				return (
					<StyledNoticeItem
						className="notice-item"
						onClick={() => navigate(message['link'])}
						isRead={el.isRead}
						key={el.alarmId}>
						<IconWrapper
							className="icon"
							onClick={e => handleClickIcon(e, el.alarmId)}>
							<HiOutlineX className="icon" />
						</IconWrapper>
						<Logo src={logo} alt="로고" />
						<Notice>
							{`${message['icon']} ${message['type']}${
								message['type'] && '하신'
							} `}
							<span>{el.bookTitle}</span>
							{`${message['message']}`}
						</Notice>
					</StyledNoticeItem>
				);
			})}
		</Container>
	);
};

const Container = styled.div`
	display: flex;
	flex-direction: column;
`;

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

	:hover {
		background-color: ${props => props.theme.colors.buttonGrey};
		cursor: pointer;
		.icon {
			color: black;
		}
	}
`;

const NoData = styled.div`
	height: 50vh;
	font-size: 1.5625rem;
	font-weight: bold;
	text-align: center;
	margin-top: 30vh;
`;

const IconWrapper = styled.div`
	height: 3rem;
	width: 3em;
	position: absolute;
	top: 0;
	right: 0;
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
