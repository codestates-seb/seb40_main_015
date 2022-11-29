import styled from 'styled-components';

const ReservationAvailable = () => {
	return (
		<Container>
			<Font>
				<p>예약</p>
				<p>가능</p>
			</Font>
		</Container>
	);
};

const Container = styled.div`
	display: flex;
`;

const Font = styled.div`
	background-color: #ffa500;
	border-radius: 1000%;
	width: 50px;
	height: 50px;

	p {
		display: flex;
		flex-direction: row;
		padding-top: 15px;
		padding-left: 1.5px;
		font-size: 12px;
		color: white;
		line-height: 2px;
	}
`;
export default ReservationAvailable;
