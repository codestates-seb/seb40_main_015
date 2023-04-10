import { useNavigate } from 'react-router';
import styled from 'styled-components';
import { convertDateForChat } from '../../utils/convertDateForChat';

interface Iprops {
	list: {
		avatarUrl?: string;
		content: string;
		dateTime: string;
		nickName?: string;
		memberId?: number;
	};
	isReceived?: boolean;
}

const Message = ({ list, isReceived = true }: Iprops) => {
	const { avatarUrl, content, dateTime, nickName, memberId } = list;
	const navigate = useNavigate();

	const handleMoveUserProfile = () => {
		navigate(`/profile/merchant/${memberId}`);
	};

	return (
		<Container isReceived={isReceived}>
			{avatarUrl && isReceived ? (
				<UserImage
					src={avatarUrl}
					alt="상대방 이미지"
					onClick={handleMoveUserProfile}
				/>
			) : (
				<EmptyImageBox />
			)}
			<Box isReceived={isReceived}>
				{nickName && isReceived ? <span>{nickName}</span> : null}
				<MessageInfoBox>
					{!isReceived && <span>{convertDateForChat(dateTime)}</span>}
					<MessageArea isReceived={isReceived}>
						<p>{content}</p>
					</MessageArea>
					{isReceived && <span>{convertDateForChat(dateTime)}</span>}
				</MessageInfoBox>
			</Box>
		</Container>
	);
};

interface ContainerProps {
	isReceived: boolean;
}

const Container = styled.div<ContainerProps>`
	display: flex;
	align-items: flex-start;
	justify-content: ${props => (props.isReceived ? 'flex-start' : 'flex-end')};
	margin-bottom: 0.2rem;
	&:last-child {
		margin-bottom: 1rem;
	}
`;

const UserImage = styled.img`
	width: 3.5rem;
	height: 3.5rem;
	border-radius: 50%;
	cursor: pointer;
`;

interface BoxProps {
	isReceived: boolean;
}

const Box = styled.div<BoxProps>`
	display: flex;
	flex-direction: column;
	align-items: ${props => (props.isReceived ? 'flex-start' : 'flex-end')};
	margin-left: ${props => (props.isReceived ? '0.5rem' : '0')};
	margin-right: ${props => (props.isReceived ? '0' : '0.5rem')};
	span {
		font-weight: bold;
		margin-right: ${props => (props.isReceived ? 0 : '0.3rem')};
		&:first-child {
			margin-bottom: 0.5rem;
			padding-top: 0.5rem;
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

interface MessageAreaProps {
	isReceived: boolean;
}

const MessageArea = styled.div<MessageAreaProps>`
	max-width: 15rem;
	background-color: ${props => (props.isReceived ? '#eaeaea' : '#26795d')};
	color: ${props => (props.isReceived ? 'black' : 'white')};
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

export default Message;
