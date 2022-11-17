import { BrowserRouter, Route, Routes } from 'react-router-dom';
import { Suspense } from 'react';

// import LoginOnly from '../components/LoginOnly';
// import Layout from '../pages/Layout';
import LayoutTemp from '../pages/LayoutTemp';
import Loading from '../components/Loading';
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
} from '../pages';

function Router() {
	return (
		<BrowserRouter>
			<Suspense fallback={<Loading />}>
				<Routes>
					{/* <Route element={<HeroSection />} /> */}
					{/* <Route path="" element={<MainPage />} /> */}
					<Route path="/" element={<LayoutTemp />}>
						{/* <Route index element={<MainPage />} /> */}
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
							<Route path="notice" element={<NoticePage />} />
						</Route>
						<Route path="books" element={<BooksPage />} />
						<Route path="books/search" element={<BooksSearchPage />} />
						<Route path="books/:bookId" element={<BooksDetailPage />} />
						<Route path="profile/merchant" element={<MerchantPage />} />
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
