import styled from 'styled-components';
import Title from '../components/common/Title';
import NoticeItem from '../components/Notice/NoticeItem';

function NoticePage() {
	return (
		<div>
			<Title text="알림" />
			<Main>
				<NoticeItem noticeData={dummy} />
			</Main>
		</div>
	);
}

const Main = styled.div`
	display: flex;
	flex-direction: column;
	align-items: center;
`;

const dummy = [
	{ id: 0, type: 'reservation', title: '자바의 정석' },
	{ id: 1, type: 'return', title: '모비딕' },
	{ id: 2, type: 'rental', title: '리팩터링 2판' },
	{
		id: 3,
		type: 'merchantCancellation',
		title: '한 권으로 읽는 컴퓨터 구조와 프로그래밍',
	},
	{ id: 4, type: 'residentCancellation', title: '누가 내 머리에 똥쌌어?' },
];

export default NoticePage;
