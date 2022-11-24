import { createSlice } from '@reduxjs/toolkit';

interface bookCreateInterface {
	bookInfo: {
		title: string;
		authors: string[];
		publisher: string;
	};
	rentalInfo: {
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
	},
});

export const { updateBookInfo, updateRentalFee } = bookCreateSlice.actions;

export default bookCreateSlice.reducer;
