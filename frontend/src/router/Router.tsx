import { BrowserRouter, Route, Routes } from 'react-router-dom';
import { Suspense } from 'react';

// import LoginOnly from '../components/LoginOnly';
// import Layout from '../pages/Layout';
import Layout from '../components/common/Layout';
import Animation from '../components/Loading/Animation';
import {
	MainPage,
	BooksBookingPage,
	BooksCreatePage,
	BooksDetailPage,
	BooksPage,
	BooksRentalPage,
	BooksSearchPage,
	ChatsPage,
	HistoryPage,
	LoginPage,
	MerchantPage,
	NoticePage,
	ProfileEditPage,
	ProfilePage,
	ReviewCreatePage,
	SignupPage,
	LoginOnly,
	ChatRoomPage,
} from '../pages';
import NoticeIcon from '../components/Notice/NoticeIcon';
import { useAppSelector } from '../redux/hooks';

function Router() {
	const isLogin = useAppSelector(state => state.loginInfo.isLogin);
	return (
		<BrowserRouter>
			<Suspense fallback={<Animation />}>
				{isLogin && <NoticeIcon />}
				<Routes>
					{/* <Route element={<HeroSection />} /> */}
					{/* <Route path="" element={<MainPage />} /> */}
					<Route path="/" element={<Layout />}>
						<Route index element={<MainPage />} />
						<Route element={<LoginOnly />}>
							<Route path="books/create" element={<BooksCreatePage />} />
							<Route
								path="books/:bookId/rental"
								element={<BooksRentalPage />}
							/>
							<Route
								path="books/:bookId/booking"
								element={<BooksBookingPage />}
							/>
							<Route path="profile" element={<ProfilePage />} />
							<Route path="profile/edit" element={<ProfileEditPage />} />
							<Route path="history" element={<HistoryPage />} />
							<Route path="review/create" element={<ReviewCreatePage />} />
							<Route path="chats" element={<ChatsPage />} />
							<Route path="chats/:roomId" element={<ChatRoomPage />} />
							<Route path="notice" element={<NoticePage />} />
						</Route>
						<Route path="books" element={<BooksPage />} />
						<Route path="books/search" element={<BooksSearchPage />} />
						<Route path="books/:bookId" element={<BooksDetailPage />} />
						<Route
							path="profile/merchant/:merchantId"
							element={<MerchantPage />}
						/>
						{/* merchant -> :userId ? */}
					</Route>

					<Route path="/login" element={<LoginPage />} />
					<Route path="/signup" element={<SignupPage />} />
					<Route path="*" element={<MainPage />} />
					{/* Loading -> Not Found page */}
				</Routes>
			</Suspense>
		</BrowserRouter>
	);
}

export default Router;
