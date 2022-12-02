import styled from 'styled-components';
import Header from '../components/common/PcVersion/Header/Header';
import HeroSectionDeskTop from '../components/HeroSection/HeroSectionDeskTop';
import hero2 from '../assets/image/pc2.png';
import hero3 from '../assets/image/pc3.png';
import hero4 from '../assets/image/pc4.png';
import hero5 from '../assets/image/pc5.png';
import hero6 from '../assets/image/pc6.png';
import hero7 from '../assets/image/pc7.png';
import hero1 from '../assets/image/pc1.png';

const MainPage = () => {
	return (
		<div>
			<Header />
			<Body>
				<img src={hero2} alt="동네북이란" className="heroimage" />
				<img src={hero3} alt="지도 서비스" className="heroimage" />
				<img src={hero4} alt="내 동네 설정" className="heroimage" />
				<img src={hero5} alt="전체 조회 및 책 등록" className="heroimage" />
				<img src={hero6} alt="대여 및 예약 캘린더" className="heroimage" />
				<img src={hero7} alt="반응형 웹앱" className="heroimage" />
				<img src={hero1} alt="메인 동네북" className="heroimage" />
				<HeroSectionDeskTop />
			</Body>
		</div>
	);
};

const Body = styled.div`
	transition-duration: 0.8s;
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

// const Body = styled.div`
// 	background-color: #016241;
// 	width: 100vw;
// 	height: 100vh;

// 	.phone {
// 		background-color: #ffffff;
// 		width: 307px;
// 		height: 664px;
// 		border-radius: 20px;
// 		align-items: center;
// 		justify-content: center;
// 		display: flex;
// 	}
// `;
