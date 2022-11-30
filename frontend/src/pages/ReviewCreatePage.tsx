import React from 'react';
import styled from 'styled-components';
import Title from '../components/common/Title';
import Button from '../components/common/Button';

const ReviewCreatePage = () => {
	return (
		<>
			<Layout>
				<Title text="리뷰 남기기" />
				<Container>
					<p>상인명 : </p>
					<p>상인평점 : </p>
					<p>리뷰 : </p>
				</Container>
			</Layout>
			<Button>리뷰 등록</Button>
			<input type="text"></input>
		</>
	);
};

const Layout = styled.div``;
const Container = styled.div``;

export default ReviewCreatePage;
