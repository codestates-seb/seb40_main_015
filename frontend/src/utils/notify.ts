import {
	dequeueNotification,
	enqueueNotification,
} from '../redux/slice/notificationSlice';
import { AppDispatch } from '../redux/store';
import getNotificationPayload from './getNotificationPayload';

const notify = (dispatch: AppDispatch, message: string) => {
	const payload = getNotificationPayload(message);
	dispatch(enqueueNotification(payload));
	setTimeout(() => {
		dispatch(dequeueNotification());
	}, payload.dismissTime);
};

export default notify;
