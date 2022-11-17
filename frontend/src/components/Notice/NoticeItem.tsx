import styled from 'styled-components';
import logo from '../../assets/image/logo1.png';

type NoticeItemProps = {
	type: string;
	title: string;
};

const NoticeItem = ({ title, type }: NoticeItemProps) => {
	return (
		<StyledNoticeItem>
			<Logo src={logo} alt="로고" />
			{type === 'reservation' && (
				<Notice>
					💌 예약하신 <span>{title}</span>의 대여가 가능합니다.
				</Notice>
			)}
			{type === 'return' && (
				<Notice>
					⏰ 대여하신 <span>{title}</span>의 대여 반납이 하루 남았습니다.
				</Notice>
			)}
			{type === 'rental' && (
				<Notice>
					📚 등록하신 <span>{title}</span>의 대여 신청이 접수되었습니다.
				</Notice>
			)}
		</StyledNoticeItem>
	);
};

const StyledNoticeItem = styled.div`
	width: 100%;
	max-width: 95vw;
	min-height: 5rem;
	background-color: white;
	border: ${props => props.theme.colors.grey + ' 1px solid'};
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
