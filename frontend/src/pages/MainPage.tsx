import styled from 'styled-components';

import { HeroSectionDeskTop, MobileHeroSection } from 'components';

const MainPage = () => {
	return (
		<div>
			<Body>
				<HeroSectionDeskTop />
				<MobileHeroSection />
			</Body>
		</div>
	);
};

const Body = styled.div`
	transition-duration: 0.8s;
	background-color: #016241;
	body {
		margin: 0;
		padding: 0;
	}

	.heroimage {
		transition-duration: 0.8s;
		body {
			margin: 0;
			padding: 0;
		}
		img {
			width: 100%;
			height: 375px;
			height: 100%;
			text-align: center;
		}
	}
`;
export default MainPage;
