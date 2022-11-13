import { Link, Outlet } from 'react-router-dom';

function ProfilePage() {
	return (
		<>
			<h1>ProfilePage</h1>
			<ul>
				<li>
					<Link to={''}>
						<span>WishListPage</span>
					</Link>
				</li>
				<li>
					<Link to={'booking-list'}>
						<span>BookingListPage</span>
					</Link>
				</li>
			</ul>
			<span>현재 탭: </span>
			<Outlet />
		</>
	);
}

export default ProfilePage;
