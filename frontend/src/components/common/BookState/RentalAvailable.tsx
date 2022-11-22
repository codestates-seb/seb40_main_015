import styled from 'styled-components';

const RentalAvailable = () => {
	return (
		<Container>
			<Font>
				<p>대여 가능</p>
			</Font>
		</Container>
	);
};

const Container = styled.div`
	display: flex;
`;

const Font = styled.div`
	background-color: #009539;
	border-radius: 1000%;
	width: 4rem;
	height: 4rem;
	display: flex;
	justify-content: center;
	align-items: center;
	flex-wrap: wrap;

	p {
		font-size: 1rem;
		color: white;
		line-height: 0.125rem;
	}
`;
export default RentalAvailable;
