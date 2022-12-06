import styled from 'styled-components';
import { convertDateForChat } from '../../utils/convertDateForChat';

interface Iprops {
	list: {
		avatarUrl?: string;
		content: string;
		dateTime: string;
		nickName?: string;
	};
}

const ReceptionMessage = ({ list }: Iprops) => {
	const { avatarUrl, content, dateTime, nickName } = list;
	return (
		<Container>
			{avatarUrl ? (
				<UserImage src={avatarUrl} alt="상대방 이미지" />
			) : (
				<EmptyImageBox />
			)}
			<Box>
				{nickName ? <span>{nickName}</span> : null}
				<MessageInfoBox>
					<MessageArea>
						<p>{content}</p>
					</MessageArea>
					<span>{convertDateForChat(dateTime)}</span>
				</MessageInfoBox>
			</Box>
		</Container>
	);
};

const Container = styled.div`
	display: flex;
	align-items: flex-start;
	margin-bottom: 0.2rem;
	&:last-child {
		margin-bottom: 1rem;
	}
`;

const UserImage = styled.img`
	width: 3.5rem;
	height: 3.5rem;
	border-radius: 50%;
`;

const Box = styled.div`
	display: flex;
	flex-direction: column;
	margin-left: 0.5rem;
	span {
		font-weight: bold;
		/* margin-left: 0.3rem; */
		&:first-child {
			margin-bottom: 0.5rem;
			padding-top: 0.5rem;
			/* padding-left: 0.5rem; */
		}
	}
`;

const MessageInfoBox = styled.div`
	display: flex;
	align-items: flex-end;
	span {
		margin-left: 0.3rem;
		font-size: 0.8rem;
	}
`;

const MessageArea = styled.div`
	max-width: 15rem;
	background-color: #eaeaea;
	border-radius: 15px;
	padding: 0.7rem;

	p {
		font-size: 1.2rem;
		line-height: 1.5rem;
		word-break: break-word;
	}
`;

const EmptyImageBox = styled.div`
	width: 3.5rem;
`;

export default ReceptionMessage;
