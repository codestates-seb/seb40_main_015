export interface BooksProps {
	key?: number;
	bookId?: string;
	title?: string;
	publisher?: string;
	author?: string;
	status?: string;
	rentalfee?: number;
	bookImage?: string;
	imageUrl?: string;
	merchantName?: string;
	rental?: RentalProps;
}

export interface RentalProps {
	rentalId: string;
	customerName: string;
	rentalState: string;
	rentalStartedAt: string;
	rentalDeadline: string;
	rentalReturnedAt: string;
	rentalCanceledAt: string;
}

// 주의:
// bookImage vs imageUrl
// rentalfee type: number vs string
