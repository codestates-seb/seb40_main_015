export default function timeForToday(value: string) {
	let today: number | Date = new Date();
	let timeValue: number | Date = new Date(value);

	const betweenTime = Math.floor(
		(today.getTime() - timeValue.getTime()) / 1000 / 60,
	);

	if (betweenTime < 1) return '방금 전';
	if (betweenTime < 60) {
		return `${betweenTime}분 전`;
	}

	const betweenTimeHour = Math.floor(betweenTime / 60);
	if (betweenTimeHour < 24) {
		return `${betweenTimeHour}시간 전`;
	}

	const betweenTimeDay = Math.floor(betweenTime / 60 / 24);
	if (betweenTimeDay < 30) {
		return `${betweenTimeDay}일 전`;
	}

	const betweenTimeMonth = Math.floor(betweenTime / 60 / 24 / 30);
	if (0 < betweenTimeMonth && betweenTimeMonth < 12) {
		return `${betweenTimeMonth}달 전`;
	}

	return `${Math.floor(betweenTimeDay / 365)}년 전`;
}
