import { createSlice } from '@reduxjs/toolkit';

export interface ReviewCreateInterface {
  bookId: string,
  reviewMessage: string,
  grade: string | number
}

const initialState: ReviewCreateInterface = {
  bookId: "",
  reviewMessage: "",
  grade: ""
};

const ReviewCreateSlice = createSlice({
	name: 'ReviewCreate',
	initialState,
	reducers: {
<<<<<<< HEAD
		updateReviewInfo: (state, action) => {
			const { bookId, reviewMessage, grade } = action.payload;
			state = {bookId, reviewMessage, grade };
		}
	}
});

export const { updateReviewInfo } = ReviewCreateSlice.actions;
=======
		updateBookInfo: (state, action) => {
			const { title, authors, publisher } = action.payload;
			// state.bookInfo = { title, authors, publisher };
		},
		updateRentalInfo: (state, action) => {
			const { key } = action.payload;
			// state.rentalInfo[key] = action.payload.value;
		},
	},
});

export const { updateBookInfo, updateRentalInfo } = ReviewCreateSlice.actions;
>>>>>>> dev

export default ReviewCreateSlice.reducer;
