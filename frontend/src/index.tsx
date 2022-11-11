import React from 'react';
import ReactDOM from 'react-dom/client';
// import App from './App';
import AppDev from './AppDev';
import { store } from './redux/store';
import { Provider } from 'react-redux';

const root = ReactDOM.createRoot(
	document.getElementById('root') as HTMLElement,
);
root.render(
	<React.StrictMode>
		<Provider store={store}>
			<AppDev />
		</Provider>
	</React.StrictMode>,
);
