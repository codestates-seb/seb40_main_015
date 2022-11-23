import styled from 'styled-components';
import Title from '../components/common/Title';
import NoticeItem, { noticeDataType } from '../components/Notice/NoticeItem';

function NoticePage() {
	return (
		<div>
			<Title text="알림" />
			<Main>
				<Deletion>받으신 알림은 30일 이후 자동 삭제됩니다.</Deletion>
				<NoticeItem noticeData={dummy} />
			</Main>
		</div>
	);
}

const Main = styled.div`
	width: 100%;
	display: flex;
	flex-direction: column;
	align-items: center;
`;

const Deletion = styled.div`
	margin-bottom: 1rem;
	color: ${props => props.theme.colors.buttonGreen};
`;

const dummy: noticeDataType[] = [
	{ id: 0, type: 'reservation', title: '자바의 정석', isViewed: false },
	{ id: 1, type: 'return', title: '모비딕', isViewed: false },
	{ id: 2, type: 'rental', title: '리팩터링 2판', isViewed: false },
	{
		id: 3,
		type: 'merchantCancellation',
		title: '한 권으로 읽는 컴퓨터 구조와 프로그래밍',
		isViewed: false,
	},
	{
		id: 4,
		type: 'residentCancellation',
		title: '누가 내 머리에 똥쌌어?',
		isViewed: false,
	},
	{
		id: 5,
		type: 'residentCancellation',
		title: '누가 내 머리에 똥쌌어?',
		isViewed: true,
	},
	{
		id: 6,
		type: 'residentCancellation',
		title: '누가 내 머리에 똥쌌어?',
		isViewed: true,
	},
	{
		id: 7,
		type: 'residentCancellation',
		title: '누가 내 머리에 똥쌌어?',
		isViewed: true,
	},
	{
		id: 8,
		type: 'residentCancellation',
		title: '누가 내 머리에 똥쌌어?',
		isViewed: true,
	},
	{
		id: 9,
		type: 'residentCancellation',
		title: '누가 내 머리에 똥쌌어?',
		isViewed: true,
	},
	{
		id: 10,
		type: 'residentCancellation',
		title: '누가 내 머리에 똥쌌어?',
		isViewed: true,
	},
	{
		id: 11,
		type: 'residentCancellation',
		title: '누가 내 머리에 똥쌌어?',
		isViewed: true,
	},
];

export default NoticePage;
