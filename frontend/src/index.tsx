import React from 'react';
import ReactDOM from 'react-dom/client';
// import App from './App';
import AppDev from './AppDev';

const root = ReactDOM.createRoot(
	document.getElementById('root') as HTMLElement,
);
root.render(
	<React.StrictMode>
		<AppDev />
	</React.StrictMode>,
);
