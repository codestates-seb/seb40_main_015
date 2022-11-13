import { BrowserRouter, Route, Routes } from 'react-router-dom';
import { Suspense } from 'react';

import LoginOnly from '../components/LoginOnly';
// import Layout from '../pages/Layout';
import LayoutTemp from '../pages/LayoutTemp';
import Loading from '../components/Loading';
import {
	BookingListPage,
	BooksBookingPage,
	BooksCreatePage,
	BooksDetailPage,
	BooksListPage,
	BooksPage,
	BooksRentalPage,
	BooksSearchPage,
	ChatsPage,
	HistoryPage,
	LendHistoryPage,
	LoginPage,
	MerchantPage,
	NoticePage,
	ProfileEditPage,
	ProfilePage,
	RentHistoryPage,
	ReviewCreatePage,
	ReviewListPage,
	SignupPage,
	WishListPage,
} from '../pages';

function Router() {
	return (
		<BrowserRouter>
			<Suspense fallback={<Loading />}>
				<Routes>
					{/* <Route element={<HeroSection />} /> */}
					{/* <Route path="" element={} /> */}

					<Route path="/" element={<LayoutTemp />}>
						{/* login check page  */}

						<Route element={<LoginOnly />}>
							<Route path="books/create" element={<BooksCreatePage />} />
							<Route path="books/rental" element={<BooksRentalPage />} />
							<Route path="books/booking" element={<BooksBookingPage />} />
							<Route path="profile" element={<ProfilePage />}>
								<Route path="wish-list" element={<WishListPage />} />
								<Route path="booking-list" element={<BookingListPage />} />
							</Route>
							<Route path="profile/edit" element={<ProfileEditPage />} />
							<Route path="history" element={<HistoryPage />}>
								<Route path="rent" element={<RentHistoryPage />} />
								<Route path="lend" element={<LendHistoryPage />} />
							</Route>
							<Route path="review/create" element={<ReviewCreatePage />} />
							<Route path="chats" element={<ChatsPage />} />
							<Route path="notice" element={<NoticePage />} />
						</Route>

						<Route path="books" element={<BooksPage />} />
						<Route path="books/search" element={<BooksSearchPage />} />
						<Route path="books/:bookId" element={<BooksDetailPage />} />
						<Route path="profile/merchant" element={<MerchantPage />}>
							<Route path="books-list" element={<BooksListPage />} />
							<Route path="review-list" element={<ReviewListPage />} />
						</Route>
					</Route>

					<Route path="/login" element={<LoginPage />} />
					<Route path="/signup" element={<SignupPage />} />
				</Routes>
			</Suspense>
		</BrowserRouter>
	);
}

export default Router;
