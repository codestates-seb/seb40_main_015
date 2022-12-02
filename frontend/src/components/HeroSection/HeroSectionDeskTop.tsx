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
					'url("https://velog.velcdn.com/images/2pandi/post/1070b9b0-b8cf-4b8f-8041-78ad40ed629f/image.png")',
			},
		},
	];

	// <img src={hero2} alt="동네북이란" className="heroimage" />

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

	.heroimage {
		position: relative;
		width: 100vw;
		background-size: 100%;
	}

	/* .box p {
		width: 50%;
		height: 50%;
		background: rgba(0, 0, 0, 0.3);
		position: absolute;
		font-size: 200px;
		left: 50%;
		top: 50%;
		transform: translate(-50%, -50%);
		margin: 0;
		opacity: 0;
		text-align: center;
	}
	.move p {
		animation: fade_in 0.5s 1.5s forwards;
	}

	@keyframes fade_in {
		0% {
			opacity: 0;
			left: 40%;
		}
		100% {
			opacity: 1;
			left: 50%;
		}
	} */
`;

export default HeroSectionDeskTop;
