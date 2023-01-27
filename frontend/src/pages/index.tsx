import { lazy } from 'react';

export const MainPage = lazy(() => import('pages/MainPage'));
export const LoginOnly = lazy(
	() => import('components/LoginOnly/LoginOnlyLayout'),
);
export const BooksBookingPage = lazy(() => import('pages/BooksBookingPage'));
export const BooksCreatePage = lazy(() => import('pages/BooksCreatePage'));
export const BooksDetailPage = lazy(() => import('pages/BooksDetailPage'));
export const BooksPage = lazy(() => import('pages/BooksPage'));
export const BooksRentalPage = lazy(() => import('pages/BooksRentalPage'));
export const BooksSearchPage = lazy(() => import('pages/BooksSearchPage'));
export const ChatsPage = lazy(() => import('pages/ChatsPage'));
export const HistoryPage = lazy(() => import('pages/HistoryPage'));
export const LoginPage = lazy(() => import('pages/LoginPage'));
export const MerchantPage = lazy(() => import('pages/MerchantPage'));
export const NoticePage = lazy(() => import('pages/NoticePage'));
export const ProfilePage = lazy(() => import('pages/ProfilePage'));
export const ProfileEditPage = lazy(() => import('pages/ProfileEditPage'));
export const ReviewCreatePage = lazy(() => import('pages/ReviewCreatePage'));
export const SignupPage = lazy(() => import('pages/SignupPage'));
export const ChatRoomPage = lazy(() => import('pages/ChatRoomPage'));
