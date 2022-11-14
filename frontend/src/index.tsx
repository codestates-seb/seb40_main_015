import React from 'react';
import ReactDOM from 'react-dom/client';
// import App from './App';
import { store } from './redux/store';
import { Provider } from 'react-redux';
import { ThemeProvider } from 'styled-components';
import theme from '../src/styles/theme';
import DevApp from './DevApp';

const root = ReactDOM.createRoot(
	document.getElementById('root') as HTMLElement,
);
root.render(
	<React.StrictMode>
		<Provider store={store}>
			<ThemeProvider theme={theme}>
				<DevApp />
			</ThemeProvider>
		</Provider>
	</React.StrictMode>,
);
