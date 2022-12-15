import React from 'react';
import styled from 'styled-components';
import Review from '../components/Review/Review';

const ReviewCreatePage = () => {
	return (
		<Layout>
			<Review />
		</Layout>
	);
};

const Layout = styled.div`
	width: 100%;
	height: 90vh;
`;

export default ReviewCreatePage;
