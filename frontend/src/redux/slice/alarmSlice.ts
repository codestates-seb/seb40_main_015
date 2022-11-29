import { createSlice } from '@reduxjs/toolkit';
const alarmSlice = createSlice({
	name: 'alarm',
	initialState: { hasNewMessage: false, isListening: false },
	reducers: {
		setState: (state, action) => {
			state.hasNewMessage = action.payload;
		},
		setListening: (state, action) => {
			state.isListening = action.payload;
		},
	},
});

export const { setState, setListening } = alarmSlice.actions;

export default alarmSlice.reducer;
