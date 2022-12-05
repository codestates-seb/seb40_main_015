import styled from 'styled-components';
import GlobalStyle from './styles/globalStyles';
import Router from './router/Router';

const DevApp = () => {
	return (
		<Container>
			<GlobalStyle />
			<Router />
		</Container>
	);
};

const Container = styled.div`
	display: flex;
	justify-content: center;
	width: 100%;
`;

export default DevApp;
