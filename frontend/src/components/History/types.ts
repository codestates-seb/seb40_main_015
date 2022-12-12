export interface BookInfo {
	author: string;
	bookId: number;
	bookState: string;
	bookUrl: string;
	content: string;
	location: { latitude: number; longitude: number };
	merchantId: number;
	publisher: string;
	rentalFee: number;
	title: string;
}

export interface RentalInfo {
	customerName: string;
	rentalCanceledAt: string;
	rentalDeadline: string;
	rentalId: number;
	rentalReturnedAt: string;
	rentalStartedAt: string;
	rentalState: string;
}
