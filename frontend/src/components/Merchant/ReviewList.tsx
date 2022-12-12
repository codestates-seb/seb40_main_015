import React from 'react';
import styled from 'styled-components';

const ReviewList = () => {
	return (
		<Container>
			<List>
				<UserInfo>
					<img src="" alt="" />
					<span>닉네임</span>
				</UserInfo>
				<p>도서명: </p>
				<p>평점: </p>
				<p>내용: </p>
			</List>
		</Container>
	);
};

const Container = styled.div`
	padding: 1rem 0.5rem;
	display: flex;
	flex-direction: column;
`;

const UserInfo = styled.div`
	display: flex;
	align-items: center;

	img {
		width: 30px;
		height: 30px;
		margin-right: 1rem;
	}
`;

const List = styled.div`
	width: 90vw;
	height: 8rem;
	border: 1px solid ${props => props.theme.colors.grey};
	padding: 1rem;
	display: flex;
	flex-direction: column;
	justify-content: space-between;
`;

export default ReviewList;
