import styled from 'styled-components';

const RentalAvailable = () => {
	return (
		<Container>
			<Font>
				<p>대여</p>
				<p>가능</p>
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
	width: 50px;
	height: 50px;

	p {
		display: flex;
		flex-direction: row;
		padding-top: 1.25rem;
		padding-left: 0.3rem;
		font-size: 1rem;
		color: white;
		line-height: 0.125rem;
	}
`;
export default RentalAvailable;
