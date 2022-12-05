import styled from 'styled-components';

const Review = () => {
	return (
		<Container>
			<p>준비중입니다.</p>
		</Container>
	);
};

export default Review;

const Container = styled.div`
	width: 100%;
	height: 75vh;
	display: flex;
	justify-content: center;
	align-items: center;
	p {
		font-size: ${props => props.theme.fontSizes.subtitle};
		font-weight: 600;
	}
	@media (min-width: 800px) {
		width: 800px;
	}
`;
