import { useEffect } from 'react';
import { useAppDispatch } from '../redux/hooks';
import {
	dequeueNotification,
	enqueueNotification,
} from '../redux/slice/notificationSlice';

const useNotify = (message: string) => {
	const dispatch = useAppDispatch();

	useEffect(() => {
		const uuid = Math.random();
		const dismissTime = 3000;
		dispatch(enqueueNotification({ uuid, message, dismissTime }));
		setTimeout(() => {
			dispatch(dequeueNotification());
		}, dismissTime);
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);
};

export default useNotify;
