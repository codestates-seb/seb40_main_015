export interface BooksProps {
	key?: number;
	bookId?: string | number;
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

// book detail
//type
export interface IBookDetail {
	bookId: number;
	content: string;
	publisher: string;
	rentalEnd: string | null;
	rentalFee?: number;
	rentalStart: string | null;
	state?: string;
	title: string;
	bookImgUrl: string;
}
export interface BookMerchant {
	grade: number;
	merchantId: number;
	name: string;
	avatarUrl: string;
}
export interface BookDetailProps {
	book: IBookDetail | undefined;
	merchant: BookMerchant | undefined;
}
