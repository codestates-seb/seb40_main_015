import { createSlice } from '@reduxjs/toolkit';

interface bookCreateInterface {
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
		updateRentalFee: (state, action) => {
			state.rentalInfo.rentalFee = action.payload;
		},
		updateDescription: (state, action) => {
			state.rentalInfo.description = action.payload;
		},
		updateRentalInfo: (state, action) => {
			const { key } = action.payload;
			state.rentalInfo[key] = action.payload.value;
		},
	},
});

export const {
	updateBookInfo,
	updateRentalFee,
	updateDescription,
	updateRentalInfo,
} = bookCreateSlice.actions;

export default bookCreateSlice.reducer;
