import styled from 'styled-components';
import Header from '../components/common/PcVersion/Header/Header';
import HeroSectionDeskTop from '../components/HeroSection/HeroSectionDeskTop';

const MainPage = () => {
	return (
		<div>
			<Header />
			<Body>
				<HeroSectionDeskTop />
				{/* <div className="phone"></div> */}
			</Body>
		</div>
	);
};

const Body = styled.div`
	background-color: #016241;
	width: 100vw;
	height: 100vh;

	.phone {
		background-color: #ffffff;
		width: 307px;
		height: 664px;
		border-radius: 20px;
		align-items: center;
		justify-content: center;
		display: flex;
	}
`;

export default MainPage;
