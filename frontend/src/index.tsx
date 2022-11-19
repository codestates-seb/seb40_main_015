import React from 'react';
import ReactDOM from 'react-dom/client';
import { store } from './redux/store';
import { Provider } from 'react-redux';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { ThemeProvider } from 'styled-components';

//components
// import App from './App';
import theme from '../src/styles/theme';
import DevApp from './DevApp';
import NotificationCenter from './components/common/NotificationCenter';

const queryClient = new QueryClient();

const root = ReactDOM.createRoot(
	document.getElementById('root') as HTMLElement,
);
root.render(
	// <React.StrictMode>
	<Provider store={store}>
		<QueryClientProvider client={queryClient}>
			<ThemeProvider theme={theme}>
				<DevApp />
				<NotificationCenter />
			</ThemeProvider>
		</QueryClientProvider>
	</Provider>,
	// </React.StrictMode>,
);
