import styled from 'styled-components';
import convertDateForChat from '../../utils/convertDateForChat';

interface Iprops {
	list: {
		avatarUrl?: string;
		content: string;
		dateTime: string;
		nickName?: string;
	};
}

const SendingMessage = ({ list }: Iprops) => {
	const { avatarUrl, content, dateTime, nickName } = list;
	return (
		<Container avatarUrl={avatarUrl}>
			<Box>
				{nickName ? <NickNameBox>{nickName}</NickNameBox> : null}
				<MessageInfoBox>
					<DateBox>{convertDateForChat(dateTime)}</DateBox>
					<MessageArea>
						<p>{content}</p>
					</MessageArea>
				</MessageInfoBox>
			</Box>
			{avatarUrl ? (
				<UserImage src={avatarUrl} alt="상대방 이미지" />
			) : (
				<EmptyImageBox />
			)}
		</Container>
	);
};

interface ContainerProps {
	avatarUrl: string | undefined;
}

const Container = styled.div<ContainerProps>`
	display: flex;
	/* margin-bottom: ${props => (props.avatarUrl ? '1rem' : 0)}; */
	justify-content: flex-end;
	align-items: flex-end;
	&:last-child {
		margin-bottom: 1rem;
	}
`;

const UserImage = styled.img`
	width: 4rem;
	height: 4rem;
	border-radius: 50%;
`;

const Box = styled.div`
	display: flex;
	flex-direction: column;
	align-items: flex-end;
	margin-right: 0.5rem;
`;

const NickNameBox = styled.span`
	font-weight: bold;
	font-size: 1.1rem;
	margin-right: 0.3rem;
	margin-bottom: 0.5rem;
	padding-top: 0.5rem;
	padding-right: 0.5rem;
`;

const MessageInfoBox = styled.div`
	display: flex;
	align-items: flex-end;
`;

const DateBox = styled.span`
	font-weight: bold;
	margin-right: 0.3rem;
`;

const MessageArea = styled.div`
	max-width: 15rem;
	background-color: #26795d;
	border-radius: 10px;
	padding: 0.7rem;

	p {
		font-size: 1.2rem;
		line-height: 1.5rem;
		color: white;
	}
`;

const EmptyImageBox = styled.div`
	width: 4rem;
	height: 3.3rem;
`;

export default SendingMessage;
