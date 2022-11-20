import { createSlice, PayloadAction } from '@reduxjs/toolkit';
import { PURGE } from 'redux-persist';
import storage from 'redux-persist/lib/storage';

interface InitProps {
	isLogin: boolean;
	id?: string;
	nickname?: string;
	userId?: string;
	accessToken?: string;
}

// const initialState: InitProps | {} = {};
const initialState: InitProps = {
	isLogin: false,
};

const loginInfoSlice = createSlice({
	name: 'loginInfo',
	initialState,
	reducers: {
		login: (_, action: PayloadAction<InitProps>) => action.payload,
		logout: () => initialState,
	},
});

export const { login, logout } = loginInfoSlice.actions;

export default loginInfoSlice.reducer;
