import { useEffect } from 'react';
import styled from 'styled-components';
import { useGetNotice } from 'api/hooks/notice/useGetNotice';

import { useAppDispatch } from 'redux/hooks';
import { setState } from 'redux/slice/alarmSlice';

import { Title, NoticeItem } from 'components';

function NoticePage() {
	const { data } = useGetNotice();
	const dispatch = useAppDispatch();

	useEffect(() => {
		dispatch(setState(false));
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	return (
		<StyledNoticePage>
			<Title text="알림" />
			<Main>
				<Deletion>받으신 알림은 30일 이후 자동 삭제됩니다.</Deletion>
				<NoticeItem noticeData={data?.data} />
			</Main>
		</StyledNoticePage>
	);
}

const StyledNoticePage = styled.div`
	::-webkit-scrollbar {
		display: none;
	}
`;

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
