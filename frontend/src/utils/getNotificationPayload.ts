const getNotificationPayload = (message: string) => {
	const uuid = Math.random();
	const dismissTime = 3000;
	return { uuid, dismissTime, message };
};

export default getNotificationPayload;
