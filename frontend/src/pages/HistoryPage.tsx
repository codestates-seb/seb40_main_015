import { Link } from 'react-router-dom';
import { Outlet } from 'react-router-dom';

function HistoryPage() {
	return (
		<>
			<h1>HistoryPage</h1>
			<ul>
				<li>
					<Link to={'rent'}>
						<span>RentHistoryPage</span>
					</Link>
				</li>
				<li>
					<Link to={'lend'}>
						<span>LendHistoryPage</span>
					</Link>
				</li>
			</ul>
			<span>현재 탭: </span>
			<Outlet />
		</>
	);
}

export default HistoryPage;
