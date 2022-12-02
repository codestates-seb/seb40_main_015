import styled from 'styled-components';
import { ISlideConfig, PageSlides, SlideParallaxType } from 'react-page-slides';

const HeroSectionDeskTop = () => {
	const slides: ISlideConfig[] = [
		{
			content: <div className="heroimage"></div>,
			style: {
				backgroundImage:
					'url("https://velog.velcdn.com/images/2pandi/post/56e3171d-9620-49a6-9cb1-4f1e3a6b9d91/image.png")',
			},
		},
		{
			content: <div className="heroimage"></div>,
			style: {
				backgroundImage:
					'url("https://velog.velcdn.com/images/2pandi/post/367941fa-964f-405a-9b25-0074d3c3c0d4/image.png")',
			},
		},
		{
			content: <div className="heroimage"></div>,
			style: {
				backgroundImage:
					'url("https://velog.velcdn.com/images/2pandi/post/f094b5fb-589c-4c16-a83d-117f4a10343b/image.png")',
			},
		},
		{
			content: <div className="heroimage"></div>,
			style: {
				backgroundImage:
					'url("https://velog.velcdn.com/images/2pandi/post/799f9d34-3f78-4311-a3f6-c7b9637e3753/image.png")',
			},
		},
		{
			content: <div className="heroimage"></div>,
			style: {
				backgroundImage:
					'url("https://velog.velcdn.com/images/2pandi/post/d2709aee-aa41-48e3-b9e9-81fd00be45b1/image.png")',
			},
		},
		{
			content: <div className="heroimage"></div>,
			style: {
				backgroundImage:
					'url("https://velog.velcdn.com/images/2pandi/post/fd8d6902-7420-4aa8-8d0d-771c1dda81df/image.png")',
			},
		},
	];

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
