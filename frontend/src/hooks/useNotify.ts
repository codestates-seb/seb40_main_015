import { useEffect } from 'react';
import { useAppDispatch } from '../redux/hooks';
import {
	dequeueNotification,
	enqueueNotification,
} from '../redux/slice/notificationSlice';
import getNotificationPayload from '../utils/getNotificationPayload';

const useNotify = (message: string) => {
	const dispatch = useAppDispatch();

	useEffect(() => {
		const payload = getNotificationPayload(message);
		dispatch(enqueueNotification(payload));
		setTimeout(() => {
			dispatch(dequeueNotification());
		}, payload.dismissTime);
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);
};

export const useNotifyHook = () => {
	const dispatch = useAppDispatch();

	return (message: string) => {
		const payload = getNotificationPayload(message);
		dispatch(enqueueNotification(payload));
		setTimeout(() => {
			dispatch(dequeueNotification());
		}, payload.dismissTime);
	};
};

export default useNotify;
