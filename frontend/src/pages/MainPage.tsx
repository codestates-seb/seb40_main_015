import React, { useState, useEffect, useRef } from 'react';
import ReactDOM from 'react-dom';

import styled from 'styled-components';
// import hero2 from '../assets/image/pc2.png';
// import hero3 from '../assets/image/pc3.png';
// import hero4 from '../assets/image/pc4.png';
// import hero5 from '../assets/image/pc5.png';
// import hero6 from '../assets/image/pc6.png';
// import hero7 from '../assets/image/pc7.png';
// import hero1 from '../assets/image/pc1.png';

const MainPage = () => {
	const images = useRef([
		{
			src: 'https://velog.velcdn.com/images/fejigu/post/888f3c35-10f5-4564-a5c3-f47fa6dac8e3/image.png',
		},
		{
			src: 'https://velog.velcdn.com/images/fejigu/post/f2c18b0c-5f54-4f81-ac2f-068909c7bf8f/image.png',
		},
		{
			src: 'https://velog.velcdn.com/images/fejigu/post/9309dd75-001e-4fff-9e86-5c7ed0e381de/image.png',
		},
		{
			src: 'https://velog.velcdn.com/images/fejigu/post/4092570b-38a2-4c38-af0a-f5b8d94443b8/image.png',
		},
		{
			src: 'https://velog.velcdn.com/images/fejigu/post/c500b56b-4057-4915-bfb7-398d559a3987/image.png',
		},
	]);
	const [current, setCurrent] = useState(0);
	const [style, setStyle] = useState({
		marginLeft: `-${current}00%`,
	});
	const imgSize = useRef(images.current.length);

	const moveSlide = (i: any) => {
		let nextIndex = current + i;

		if (nextIndex < 0) nextIndex = imgSize.current - 1;
		else if (nextIndex >= imgSize.current) nextIndex = 0;

		setCurrent(nextIndex);
	};
	useEffect(() => {
		setStyle({ marginLeft: `-${current}00%` });
	}, [current]);
	return (
		<Container>
			<div className="slide">
				<div
					className="btn"
					onClick={() => {
						moveSlide(-1);
					}}>
					&lt;
				</div>
				<div className="window">
					<div className="flexbox" style={style}>
						{images.current.map((img, i) => (
							<div
								key={i}
								className="img"
								style={{ backgroundImage: `url(${img.src})` }}></div>
						))}
					</div>
				</div>
				<div
					className="btn"
					onClick={() => {
						moveSlide(1);
					}}>
					&gt;
				</div>
			</div>
			<div className="position">
				{images.current.map((x, i) => (
					<div key={i} className={i === current ? 'dot current' : 'dot'}></div>
				))}
			</div>
		</Container>
	);
	// return (
	// 	<Container>
	// 		<Body>
	// 			<img src={mohero1} alt="동네북이란" className="moheroimage" />
	// 			<img src={mohero2} alt="지도검색" className="moheroimage" />
	// 			<img src={mohero3} alt="내동네설정" className="moheroimage" />
	// 			<img src={mohero4} alt="책등록" className="moheroimage" />
	// 			<img src={mohero5} alt="대여예약" className="moheroimage" />
	// 			{/* <img src="" alt="메인 안내 이미지1" className="heroimage" />
	// 			<img src={hero2} alt="동네북이란" className="heroimage" />
	// 			<img src={hero3} alt="지도 서비스" className="heroimage" />
	// 			<img src={hero4} alt="내 동네 설정" className="heroimage" />
	// 			<img src={hero5} alt="전체 조회 및 책 등록" className="heroimage" />
	// 			<img src={hero6} alt="대여 및 예약 캘린더" className="heroimage" />
	// 			<img src={hero7} alt="반응형 웹앱" className="heroimage" />
	// 			<img src={hero1} alt="메인 동네북" className="heroimage" /> */}
	// 		</Body>
	// 	</Container>
	// );
};

const Container = styled.div`
	display: flex;
	flex-direction: column;
	align-items: center;
	background-color: #016241;

	body {
		display: flex;
		align-items: center;
		justify-content: center;
		width: 100vw;
		height: 100vh;
		user-select: none;
	}

	div {
		transition: all 0.3s ease-out;
	}

	.slide {
		display: flex;
		align-items: center;
	}

	.window {
		width: 414px;
		height: 896px;
		overflow: hidden;
	}

	.flexbox {
		display: flex;
	}

	.img {
		width: 414px;
		height: 896px;
		background-size: contain;
		background-repeat: no-repeat;
		flex: none;
	}

	.btn {
		display: flex;
		align-items: center;
		cursor: pointer;
		font-size: 3.3rem;
		color: gray;
		padding: 0px 10px;
	}

	.position {
		margin-top: 15px;
		display: flex;
		justify-content: center;
	}

	.dot {
		background: lightgray;
		border-radius: 100%;
		height: 10px;
		width: 10px;
	}
	.dot + .dot {
		margin-left: 25px;
	}

	.current {
		background: gray;
	}
`;

// const Body = styled.div`
// 	display: flex;
// 	.moheroimage {
// 		width: 414px;
// 		height: 896px;
// 	}
// 	.heroimage {
// 		transition-duration: 0.8s;
// 		body {
// 			margin: 0;
// 			padding: 0;
// 		}
// 		img {
// 			width: 414px;
// 			height: 896px;
// 			height: 250px;
// 			object-fit: contain;
// 			flex: none;
// 		}
// 	}
ReactDOM.render(<MainPage />, document.getElementById('root'));
export default MainPage;
