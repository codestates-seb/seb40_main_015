import styled from 'styled-components';
import { useAppSelector } from '../../redux/hooks';
import Toast from './Toast';
import { payloadType } from '../../redux/slice/notificationSlice';

const NotificationCenter = () => {
	const state = useAppSelector(
		state => state.persistedReducer.notification.messages,
	);

	return (
		<StyledNotificationCenter>
			{state.map((n: payloadType) => (
				<Toast key={n.uuid} text={n.message} dismissTime={n.dismissTime} />
			))}
		</StyledNotificationCenter>
	);
};

const StyledNotificationCenter = styled.div`
	font-size: 1rem;
	position: fixed;
	z-index: 999999;
	left: calc(50% - 180px);
	top: 5px;
`;

export default NotificationCenter;
