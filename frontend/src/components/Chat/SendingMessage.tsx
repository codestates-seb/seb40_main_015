import { useNavigate } from 'react-router';
import styled from 'styled-components';
import { convertDateForChat } from '../../utils/convertDateForChat';

interface IProps {
	list: {
		avatarUrl?: string;
		content: string;
		dateTime: string;
		nickName?: string;
		memberId?: number;
	};
}

const SendingMessage = ({ list }: IProps) => {
	const { avatarUrl, content, dateTime, nickName, memberId } = list;

	const navigate = useNavigate();

	const handleMoveUserProfile = () => {
		navigate(`/profile/merchant/${memberId}`);
	};

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
				<UserImage
					src={avatarUrl}
					alt="나의 이미지"
					onClick={handleMoveUserProfile}
				/>
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
	justify-content: flex-end;
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
	cursor: pointer;
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
	/* padding-right: 0.5rem; */
`;

const MessageInfoBox = styled.div`
	display: flex;
	align-items: flex-end;
`;

const DateBox = styled.span`
	font-weight: bold;
	margin-right: 0.3rem;
	font-size: 0.8rem;
`;

const MessageArea = styled.div`
	max-width: 15rem;
	background-color: #26795d;
	border-radius: 15px;
	padding: 0.7rem;

	p {
		font-size: 1.2rem;
		line-height: 1.5rem;
		color: white;
		word-break: break-word;
	}
`;

const EmptyImageBox = styled.div`
	width: 3.5rem;
`;

export default SendingMessage;
