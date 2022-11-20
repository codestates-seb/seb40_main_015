import { createSlice, PayloadAction } from '@reduxjs/toolkit';

export type notificationType = {
	messages: {
		uuid: number;
		message: string;
		dismissTime?: number;
	}[];
};

export type payloadType = {
	uuid: number;
	message: string;
	dismissTime?: number;
};

const initialState: notificationType = { messages: [] };

const notificationSlice = createSlice({
	name: 'notification',
	initialState,
	reducers: {
		enqueueNotification: (state, action: PayloadAction<payloadType>) => {
			state.messages = [...state.messages, action.payload];
		},
		dequeueNotification: state => {
			state.messages = state.messages.slice(1);
		},
	},
});

export const { enqueueNotification, dequeueNotification } =
	notificationSlice.actions;

export default notificationSlice.reducer;
