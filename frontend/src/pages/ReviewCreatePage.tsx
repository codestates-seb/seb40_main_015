import React from 'react';
import styled from 'styled-components';
import Title from '../components/common/Title';
import Button from '../components/common/Button';
import Review from '../components/Review/Review';
import ConfirmModal from '../components/common/ConfirmModal';

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
const Container = styled.div``;

export default ReviewCreatePage;
