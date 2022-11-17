import { useSelector } from 'react-redux';
import styled from 'styled-components';
import { notificationType } from '../../redux/slice/notificationSlice';
import Toast from './Toast';

interface RootState {
	notification: notificationType;
}

const NotificationCenter = () => {
	const state = useSelector((state: RootState) => state.notification).messages;
	return (
		<StyledNotificationCenter>
			{state.map(n => (
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
