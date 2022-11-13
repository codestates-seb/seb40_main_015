import styled from 'styled-components';
import GlobalStyle from './styles/globalStyles';
import Router from './router/Router';
import Layout from './pages/Layout';
// import Layout from './pages/Layout';

const AppDev = () => {
	return (
		<Container>
			<GlobalStyle />
			<Router />
			{/* <Layout /> */}
		</Container>
	);
};

export default AppDev;

const Container = styled.div`
	display: flex;
	justify-content: center;
	width: 100vw;
`;
