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
		updateReviewInfo: (state, action) => {
			const { bookId, reviewMessage, grade } = action.payload;
			state = {bookId, reviewMessage, grade };
		}
	}
});

export const { updateReviewInfo } = ReviewCreateSlice.actions;

export default ReviewCreateSlice.reducer;
