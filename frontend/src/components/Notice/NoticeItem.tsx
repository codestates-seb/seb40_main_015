import axios from 'axios';
import { HiOutlineX } from 'react-icons/hi';
import { Link, useNavigate } from 'react-router-dom';
import styled from 'styled-components';
import { noticeMessages } from '../../api/hooks/notice/noticeMessages';
import { NoticeItemType } from '../../api/hooks/notice/useGetNotice';
import logo from '../../assets/image/logo1.png';
import { BASE_URL } from '../../constants/constants';
import { useAppSelector } from '../../redux/hooks';

const NoticeItem = ({ noticeData }: NoticeItemType) => {
	const navigate = useNavigate();
	const { accessToken } = useAppSelector(state => state.loginInfo);

	// const handleXClick = (alarmId: number) => {
	// 	if (window.confirm('정말 삭제하시겠습니까?')) {
	// 		axios.delete(`${BASE_URL}/alarm/${alarmId}`, {
	// 			headers: {
	// 				Authorization: accessToken,
	// 			},
	// 		});
	// 	} else {
	// 		return;
	// 	}
	// 	navigate('/notice');
	// };

	const handleClick = (
		e: React.MouseEvent<HTMLDivElement, MouseEvent>,
		alarmId: number,
		link: string,
	) => {
		if ((e.target as Element).classList.value === 'icon') {
			if (window.confirm('정말 삭제하시겠습니까?')) {
				axios.delete(`${BASE_URL}/alarm/${alarmId}`, {
					headers: {
						Authorization: accessToken,
					},
				});
			} else {
				return;
			}
		} else {
			navigate(link);
		}
	};

	return (
		<>
			{noticeData?.map(el => {
				const message = noticeMessages[el.alarmType];
				return (
					<StyledNoticeItem
						className="notice-item"
						onClick={e => handleClick(e, el.alarmId, message[3])}
						isRead={el.isRead}
						key={el.alarmId}>
						<IconWrapper>
							<HiOutlineX className="icon" />
						</IconWrapper>
						<Logo src={logo} alt="로고" />
						<Notice>
							{`${message[0]} ${message[1]}하신 `}
							<span>{el.bookTitle}</span>
							{`${message[2]}`}
						</Notice>
					</StyledNoticeItem>
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

	:hover {
		background-color: ${props => props.theme.colors.buttonGrey};
	}
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
