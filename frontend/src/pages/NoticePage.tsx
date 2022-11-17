import styled from 'styled-components';
import Title from '../components/common/Title';
import NoticeItem from '../components/Notice/NoticeItem';

function NoticePage() {
	return (
		<div>
			<Title text="알림" />
			<Main>
				{dummy.map(el => (
					<NoticeItem key={el.id} type={el.type} title={el.title} />
				))}
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
];

export default NoticePage;
