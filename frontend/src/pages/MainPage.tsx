import React from 'react';
import styled from 'styled-components';
// import hero2 from '../assets/image/pc2.png';
// import hero3 from '../assets/image/pc3.png';
// import hero4 from '../assets/image/pc4.png';
// import hero5 from '../assets/image/pc5.png';
// import hero6 from '../assets/image/pc6.png';
// import hero7 from '../assets/image/pc7.png';
// import hero1 from '../assets/image/pc1.png';
import mohero1 from '../assets/image/mo1.png';
import mohero2 from '../assets/image/mo2.png';
import mohero3 from '../assets/image/mo3.png';
import mohero4 from '../assets/image/mo4.png';
import mohero5 from '../assets/image/mo5.png';

const MainPage = () => {
	return (
		<Container>
			<Body>
				<img src={mohero1} alt="동네북이란" className="moheroimage" />
				<img src={mohero2} alt="지도검색" className="moheroimage" />
				<img src={mohero3} alt="내동네설정" className="moheroimage" />
				<img src={mohero4} alt="책등록" className="moheroimage" />
				<img src={mohero5} alt="대여예약" className="moheroimage" />
				{/* <img src="" alt="메인 안내 이미지1" className="heroimage" />
				<img src={hero2} alt="동네북이란" className="heroimage" />
				<img src={hero3} alt="지도 서비스" className="heroimage" />
				<img src={hero4} alt="내 동네 설정" className="heroimage" />
				<img src={hero5} alt="전체 조회 및 책 등록" className="heroimage" />
				<img src={hero6} alt="대여 및 예약 캘린더" className="heroimage" />
				<img src={hero7} alt="반응형 웹앱" className="heroimage" />
				<img src={hero1} alt="메인 동네북" className="heroimage" /> */}
			</Body>
		</Container>
	);
};

const Container = styled.div`
	background-color: #016241;
`;
const Body = styled.div`
	.moheroimage {
		width: 414px;
		height: 896px;
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
			background-color: red;
			height: 100%;
			text-align: center;
		}
	}
`;

export default MainPage;
