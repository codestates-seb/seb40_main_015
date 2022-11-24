import { createSlice } from '@reduxjs/toolkit';

interface bookCreateInterface {
	title: string;
	authors: string;
	publisher: string;
}

const initialState: bookCreateInterface = {
	title: '',
	authors: '',
	publisher: '',
};

const bookCreateSlice = createSlice({
	name: 'bookCreate',
	initialState,
	reducers: {
		updateBookInfo: (state, action) => {
			const { title, authors, publisher } = action.payload;
			state.title = title;
			state.authors = authors[0];
			state.publisher = publisher;
		},
	},
});

export const { updateBookInfo } = bookCreateSlice.actions;

export default bookCreateSlice.reducer;
