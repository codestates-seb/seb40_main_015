import { createSlice } from '@reduxjs/toolkit';

export interface bookCreateInterface {
	bookInfo: {
		title: string;
		authors: string[];
		publisher: string;
	};
	rentalInfo: {
		[key: string]: number | string;
		rentalFee: number;
		description: string;
		imageUrl: string;
	};
}

const initialState: bookCreateInterface = {
	bookInfo: {
		title: '',
		authors: [''],
		publisher: '',
	},
	rentalInfo: {
		rentalFee: 0,
		description: '',
		imageUrl: '',
	},
};

const bookCreateSlice = createSlice({
	name: 'bookCreate',
	initialState,
	reducers: {
		updateBookInfo: (state, action) => {
			const { title, authors, publisher } = action.payload;
			state.bookInfo = { title, authors, publisher };
		},
		updateRentalInfo: (state, action) => {
			const { key } = action.payload;
			state.rentalInfo[key] = action.payload.value;
		},
		resetBookCreateInfo: () => initialState,
	},
});

export const { updateBookInfo, updateRentalInfo, resetBookCreateInfo } =
	bookCreateSlice.actions;

export default bookCreateSlice.reducer;
