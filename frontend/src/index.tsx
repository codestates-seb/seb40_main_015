import React from 'react';
import ReactDOM from 'react-dom/client';
import { store, persistor } from './redux/store';
import { Provider } from 'react-redux';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { ReactQueryDevtools } from '@tanstack/react-query-devtools';
import { ThemeProvider } from 'styled-components';
import { PersistGate } from 'redux-persist/integration/react';

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
	<React.StrictMode>
		<Provider store={store}>
			<PersistGate loading={null} persistor={persistor}>
				<QueryClientProvider client={queryClient}>
					<ThemeProvider theme={theme}>
						<DevApp />
						<NotificationCenter />
					</ThemeProvider>
					<ReactQueryDevtools initialIsOpen={false} />
				</QueryClientProvider>
			</PersistGate>
		</Provider>
		,
	</React.StrictMode>,
);
