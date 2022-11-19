import { createSlice, PayloadAction } from '@reduxjs/toolkit';

interface InitProps {
	id: string;
	nickname: string;
	userId: string;
	accessToken: string;
}

const initialState: InitProps | {} = {};

const loginInfoSlice = createSlice({
	name: 'loginInfo',
	initialState,
	reducers: {
		login: (state, action: PayloadAction<InitProps>) => action.payload,
		logout: () => {},
	},
});

export const { login, logout } = loginInfoSlice.actions;

export default loginInfoSlice.reducer;
