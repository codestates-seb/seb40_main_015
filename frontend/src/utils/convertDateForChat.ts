export const convertDateForChat = (data: string) => {
	const newDate = new Date(data).toLocaleTimeString().split(' ');
	const ampm = newDate[0];
	const min = newDate[1].split(':').slice(0, 2).join(':');
	const result = `${ampm} ${min}`;

	return result;
};

export const convertDateForChat2 = (data: string) => {
	const newDate = new Date(data).toLocaleString().split('.').slice(0, 3);
	return `${newDate[0]}년 ${newDate[1]}월 ${newDate[2]}일`;
};
