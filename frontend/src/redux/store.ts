import { configureStore, combineReducers } from '@reduxjs/toolkit';
import storage from 'redux-persist/lib/storage';
import { persistStore } from 'redux-persist';
import persistReducer from 'redux-persist/lib/persistReducer';

// slices
import notificationReducer from './slice/notificationSlice';
import loginInfoReducer from './slice/userSlice';

const persistConfig = {
	key: 'root',
	version: 1,
	storage,
	whitelist: ['notification'],
};

const loginPersistConfig = {
	key: 'login',
	storage,
};

const rootReducer = combineReducers({
	notification: notificationReducer,
	loginInfo: persistReducer(loginPersistConfig, loginInfoReducer),
});

const persistedReducer = persistReducer(persistConfig, rootReducer);

export const store = configureStore({
	reducer: persistedReducer,
	// reducer: {
	// 	persistedReducer: persistReducer(persistConfig, notificationReducer),
	// 	loginInfo: persistReducer(loginPersistConfig, loginInfoReducer),
	// },

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
