import styled from 'styled-components';
import { useGetNotice } from '../api/hooks/notice/useGetNotice';
import Title from '../components/common/Title';
import NoticeItem from '../components/Notice/NoticeItem';

function NoticePage() {
	const { data } = useGetNotice();

	return (
		<div>
			<Title text="알림" />
			<Main>
				<Deletion>받으신 알림은 30일 이후 자동 삭제됩니다.</Deletion>
				<NoticeItem noticeData={data?.data} />
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

export default NoticePage;
