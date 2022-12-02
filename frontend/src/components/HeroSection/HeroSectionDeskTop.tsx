import styled from 'styled-components';
import { ISlideConfig, PageSlides, SlideParallaxType } from 'react-page-slides';

const HeroSectionDeskTop = () => {
	const slides: ISlideConfig[] = [
		{
			content: <div className="box a"></div>,
			style: {
				backgroundImage:
					'url("https://kr-media.apjonlinecdn.com/magefan_blog/25-best-hd-wallpapers-laptops159561982840438.jpg")',
			},
		},
		{
			content: <div className="box b"></div>,
			style: {
				backgroundImage: 'url("public/photo/photo_2.jpg")',
			},
		},
		{
			content: <div className="box c"></div>,
			style: {
				backgroundImage: 'url("public/photo/photo_2.jpg")',
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
	width: 100%;
	height: 100%;
	.a {
		background-color: red;
	}
	.b {
		background-color: orange;
	}
	.c {
		background-color: yellow;
	}
	.box {
		width: 100%;
		height: 100%;
		position: relative;
		color: #ffffff;
		font-size: 24pt;
	}
	/* .box:first-of-type p { opacity:1;} */
	.box p {
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
	}
`;

export default HeroSectionDeskTop;
