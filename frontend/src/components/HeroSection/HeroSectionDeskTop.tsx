import styled from 'styled-components';
import { ISlideConfig, PageSlides, SlideParallaxType } from 'react-page-slides';
import { heroSlides } from './heroSlides';

const HeroSectionDeskTop = () => {
	const slides: ISlideConfig[] = heroSlides;

	return (
		<StyledHeroSection>
			<PageSlides
				enableAutoScroll={true}
				transitionSpeed={1000}
				slides={slides}
				parallax={{
					offset: 0.6,
					type: SlideParallaxType.reveal,
				}}
			/>
		</StyledHeroSection>
	);
};

const StyledHeroSection = styled.div`
	width: 100vw;
	display: flex;
	justify-content: center;
	align-items: center;
	position: relative;

	.rps-slide-background {
		background-size: 100% !important;
		background-repeat: no-repeat;
		background-position: center center;
	}

	.heroimage {
		width: 100vw;
	}

	@media screen and (max-width: 800px) {
		display: none;
	}
`;

export default HeroSectionDeskTop;
