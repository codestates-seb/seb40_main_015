import { configureStore, combineReducers } from '@reduxjs/toolkit';
import storage from 'redux-persist/lib/storage';
import { persistStore } from 'redux-persist';
import persistReducer from 'redux-persist/lib/persistReducer';

// slices
import notificationReducer from './slice/notificationSlice';
import loginInfoReducer from './slice/userSlice';
import bookCreateReducer from './slice/bookCreateSlice';
import geoLocationSlice from './slice/geoLocationSlice';
import userInfoReducer from './slice/userInfoSlice';

const persistConfig = {
	key: 'root',
	version: 1,
	storage,
	blacklist: ['notification', 'bookCreate', 'userInfo'],
};

const loginPersistConfig = {
	key: 'login',
	storage,
};

const rootReducer = combineReducers({
	notification: notificationReducer,
	bookCreate: bookCreateReducer,
	getLocation: geoLocationSlice,
	userInfo: userInfoReducer,
});

export const store = configureStore({
	reducer: {
		persistedReducer: persistReducer(persistConfig, rootReducer),
		loginInfo: persistReducer(loginPersistConfig, loginInfoReducer),
	},

	middleware: getDefaultMiddleware =>
		getDefaultMiddleware({
			serializableCheck: false,
		}),
});

export const persistor = persistStore(store);

// Infer the `RootState` and `AppDispatch` types from the store itself
export type RootState = ReturnType<typeof store.getState>;
// Inferred type: {posts: PostsState, comments: CommentsState, users: UsersState}
export type AppDispatch = typeof store.dispatch;
