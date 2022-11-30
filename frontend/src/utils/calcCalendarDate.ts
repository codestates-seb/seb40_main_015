import convertDate from './convertDate';

//type
interface IcalcCalendarDate {
	month: number[];
	day: number[];
	rentalPeriod: string;
}

export const calcCalendarDate = (start: string): IcalcCalendarDate => {
	const date = {
		rentalStatedAt: start,
		rentalDeadline: new Date(
			+start.slice(0, 4),
			+start.slice(5, 7) - 1,
			+start.slice(8, 10) + 9, //월말이 30일냐 31이냐에 따라 다름
		).toISOString(),
	};

	const rentalPeriod = convertDate(date.rentalStatedAt, date.rentalDeadline);

	const month = rentalPeriod
		.split('~')
		.map(el => el.trim().slice(5))
		.map(el => +el.split('.')[0]);

	const day = rentalPeriod
		.split('~')
		.map(el => el.trim().slice(5))
		.map(el => +el.split('.')[1]);

	return { month, day, rentalPeriod };
};
