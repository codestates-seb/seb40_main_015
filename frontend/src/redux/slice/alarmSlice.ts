import { createSlice } from '@reduxjs/toolkit';

const alarmSlice = createSlice({
	name: 'alarm',
	initialState: { hasNewMessage: false },
	reducers: {
		setState: (state, action) => {
			state.hasNewMessage = action.payload;
		},
	},
});

export const { setState } = alarmSlice.actions;
export default alarmSlice.reducer;
