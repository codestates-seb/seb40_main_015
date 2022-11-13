import { Outlet } from 'react-router-dom';

function LoginOnly() {
	return (
		<div>
			<h1>LoginOnly</h1>
			<Outlet />
		</div>
	);
}

export default LoginOnly;
