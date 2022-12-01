const convertDateForChat = (data: string) => {
	const newDate = new Date(data).toLocaleTimeString().split(' ');
	const ampm = newDate[0];
	const min = newDate[1].split(':').slice(0, 2).join(':');
	const result = `${ampm} ${min}`;

	return result;
};

export default convertDateForChat;
