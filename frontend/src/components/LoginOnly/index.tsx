import { Outlet } from 'react-router-dom';

// Login checking

function LoginOnly() {
	return (
		<div>
			<h1>LoginOnly</h1>
			<Outlet />
		</div>
	);
}

export default LoginOnly;
