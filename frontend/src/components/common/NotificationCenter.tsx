import { SiAtandt } from 'react-icons/si';
import { useSelector } from 'react-redux';
import styled from 'styled-components';
import { useAppSelector } from '../../redux/hooks';
import {
	notificationType,
	payloadType,
} from '../../redux/slice/notificationSlice';
import Toast from './Toast';

interface RootState {
	notification: notificationType;
}
// interface RootState {
// 	persistedReducer: { notification: notificationType };
// }

const NotificationCenter = () => {
	const state = useAppSelector(state => state.persistedReducer.messages);
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
