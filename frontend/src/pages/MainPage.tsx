import React from 'react';
import styled from 'styled-components';

const MainPage = () => {
	return (
		<div>
			<Body>
				<img src="" alt="메인 안내 이미지1" className="heroimage" />
			</Body>
		</div>
	);
};

const Body = styled.div`
	.heroimage {
		width: 100%;
		height: 375px;
		background-color: red;
	}
`;

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

export default MainPage;
