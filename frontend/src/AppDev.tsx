import styled from 'styled-components';
import NavBar from './components/common/NavBar';
import Layout from './pages/Layout';
import LenderInfoPage from './pages/LenderInfoPage';
import GlobalStyle from './styles/globalStyles';

const AppDev = () => {
	return (
		<Container>
			<GlobalStyle />
			<Layout />
		</Container>
	);
};

export default AppDev;

const Container = styled.div`
	width: 100vw;
`;
