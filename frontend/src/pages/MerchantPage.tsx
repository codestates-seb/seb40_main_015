import { Link } from 'react-router-dom';
import { Outlet } from 'react-router-dom';

function MerchantPage() {
	return (
		<>
			<h1>MerchantPage</h1>
			<ul>
				<li>
					<Link to={''}>
						<span>BooksListPage</span>
					</Link>
				</li>
				<li>
					<Link to={'review-list'}>
						<span>ReviewListPage</span>
					</Link>
				</li>
			</ul>
			<span>현재 탭: </span>
			<Outlet />
		</>
	);
}

export default MerchantPage;
