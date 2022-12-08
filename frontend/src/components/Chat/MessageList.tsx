import React from 'react';
import styled from 'styled-components';
import { useAppSelector } from '../../redux/hooks';
import { convertDateForChat2 } from '../../utils/convertDateForChat';
import ReceptionMessage from './ReceptionMessage';
import SendingMessage from './SendingMessage';

interface List {
	avatarUrl: string;
	content: string;
	dateTime: string;
	memberId: number;
	nickName: string;
}

interface IProps {
	messageList: List[];
}

const MessageList = (props: IProps) => {
	const { messageList } = props;
	const { nickname } = useAppSelector(state => state.loginInfo);
	let prevNickname = '';
	let prevDate = '';

	return (
		<>
			{messageList.length ? (
				messageList.map((list: List) => {
					const { dateTime, content, memberId } = list;
					const newList = { content, dateTime, memberId };
					const currentDate = convertDateForChat2(dateTime);
					if (currentDate !== prevDate) {
						prevDate = currentDate;
						if (list?.nickName === nickname) {
							if (prevNickname === list.nickName) {
								return (
									<React.Fragment key={currentDate}>
										<DateDisplay>{currentDate}</DateDisplay>
										<SendingMessage list={newList} />
									</React.Fragment>
								);
							} else {
								prevNickname = list.nickName;
								return (
									<React.Fragment key={currentDate}>
										<DateDisplay>{currentDate}</DateDisplay>
										<SendingMessage list={list} />
									</React.Fragment>
								);
							}
						} else {
							if (prevNickname === list.nickName) {
								return (
									<React.Fragment key={currentDate}>
										<DateDisplay>{currentDate}</DateDisplay>
										<ReceptionMessage list={newList} />
									</React.Fragment>
								);
							} else {
								prevNickname = list.nickName;
								return (
									<React.Fragment key={currentDate}>
										<DateDisplay>{currentDate}</DateDisplay>
										<ReceptionMessage list={list} />
									</React.Fragment>
								);
							}
						}
					} else {
						if (list?.nickName === nickname) {
							if (prevNickname === list.nickName) {
								return <SendingMessage key={dateTime} list={newList} />;
							} else {
								prevNickname = list.nickName;
								return <SendingMessage key={dateTime} list={list} />;
							}
						} else {
							if (prevNickname === list.nickName) {
								return <ReceptionMessage key={dateTime} list={newList} />;
							} else {
								prevNickname = list.nickName;
								return <ReceptionMessage key={dateTime} list={list} />;
							}
						}
					}
				})
			) : (
				<Empty>
					<p>대화를 시작해보세요</p>
				</Empty>
			)}
		</>
	);
};

const DateDisplay = styled.p`
	text-align: center;
	font-size: 1.5rem;
	margin: 1rem 0;
`;

const Empty = styled.div`
	width: 100%;
	height: 75vh;
	display: flex;
	justify-content: center;
	align-items: center;
	p {
		font-size: ${props => props.theme.fontSizes.subtitle};
		font-weight: 600;
		color: #a7a7a7;
	}
`;

export default MessageList;
