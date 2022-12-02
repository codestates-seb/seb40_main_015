import styled from 'styled-components';
import Header from '../components/common/PcVersion/Header/Header';
import MobileHeroSection from '../components/HeroSection/MobileHeroSection';

const MainPage = () => {
	return (
		<StyledMainPage>
			<Header />
			<Body>
				<MobileHeroSection />
			</Body>
		</StyledMainPage>
	);
};

const StyledMainPage = styled.div`
	overflow: hidden;
`;
const Body = styled.div`
	transition-duration: 0.8s;
	background-color: #016241;
	width: 100vw;
	height: 100vh;
	body {
		margin: 0;
		padding: 0;
	}
	img {
		width: 100%;
		height: 100%;
		text-align: center;
	}
`;
export default MainPage;

// const Body = styled.div
//     width: 100vw;
//     height: 100vh;

//     .phone {
//         background-color: #ffffff;
//         width: 307px;
//         height: 664px;
//         border-radius: 20px;
//         align-items: center;
//         justify-content: center;
//         display: flex;
//     }
// ;
