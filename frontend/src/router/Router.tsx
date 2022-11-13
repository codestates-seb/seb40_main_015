import { BrowserRouter, Route, Routes } from 'react-router-dom';
import { Suspense } from 'react';

// import LoginOnly from '../components/LoginOnly';
// import Layout from '../pages/Layout';
import LayoutTemp from '../pages/LayoutTemp';
import Loading from '../components/Loading';
import {
	MainPage,
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
	LoginOnly,
} from '../pages';

function Router() {
	return (
		<BrowserRouter>
			<Suspense fallback={<Loading />}>
				<Routes>
					{/* <Route element={<HeroSection />} /> */}
					<Route path="/" element={<LayoutTemp />}>
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
							<Route path="profile" element={<ProfilePage />}>
								{/* <Route path="wish-list" element={<WishListPage />} /> */}
								<Route index element={<WishListPage />} />
								<Route path="booking-list" element={<BookingListPage />} />
							</Route>
							<Route path="profile/edit" element={<ProfileEditPage />} />
							<Route path="history" element={<HistoryPage />}>
								{/* <Route path='rent' element={<RentHistoryPage />} /> */}
								<Route index element={<RentHistoryPage />} />
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
							{/* merchant -> :userId ? */}
							{/* <Route path="books-list" element={<BooksListPage />} /> */}
							<Route index element={<BooksListPage />} />
							<Route path="review-list" element={<ReviewListPage />} />
						</Route>
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
