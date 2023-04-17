import type { Preview } from '@storybook/react';
import { ThemeProvider } from 'styled-components';
import React from 'react';
import theme from '../src/styles/theme';
import { store } from '../src/redux/store';
import { Provider } from 'react-redux';
import { BrowserRouter } from 'react-router-dom';
const preview: Preview = {
	parameters: {
		actions: { argTypesRegex: '^on[A-Z].*' },
		controls: {
			matchers: {
				color: /(background|color)$/i,
				date: /Date$/,
			},
		},
	},
	decorators: [
		Story => (
			<BrowserRouter>
				<Provider store={store}>
					<ThemeProvider theme={theme}>
						<Story />
					</ThemeProvider>
				</Provider>
			</BrowserRouter>
		),
	],
};

export default preview;
