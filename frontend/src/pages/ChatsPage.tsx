import React from 'react';
import styled from 'styled-components';

const ChatsPage = () => {
	return (
		<Container>
			<div className="modal">
				<h1 className="title">현재 페이지는 서비스 준비중입니다</h1>
			</div>
		</Container>
	);
};

const Container = styled.div`
	.modal {
		position: absolute;
		top: 0;
		left: 0;
		width: 100%;
		background-color: grey;
		display: block;
		z-index: 200;
		top: 20%;
		left: 30%;
		width: 40%;
		background: #eaeaea;
		padding: 3rem;
		border: 1px solid #ccc;
		box-shadow: 1px 1px 1px rgba(0, 0, 0, 0.5);
	}

	.title {
		align-items: center;
		text-align: center;
		margin-top: 30px;
		margin-bottom: 30px;
	}
`;

export default ChatsPage;
