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
	/* margin-bottom: 1rem; */
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
`;

const MessageArea = styled.div`
	max-width: 15rem;
	background-color: #eaeaea;
	border-radius: 10px;
	padding: 0.7rem;

	p {
		font-size: 1.2rem;
		line-height: 1.5rem;
	}
`;

const EmptyImageBox = styled.div`
	width: 4rem;
	height: 3.3rem;
`;

export default ReceptionMessage;
