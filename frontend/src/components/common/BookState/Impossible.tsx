import styled from 'styled-components';

const Impossible = () => {
	return (
		<Container>
			<Font>
				<p>대여</p>
				<p>불가능</p>
			</Font>
		</Container>
	);
};

const Container = styled.div`
	display: flex;
`;

const Font = styled.div`
	background-color: #ff0000;
	border-radius: 1000%;
	width: 55px;
	height: 55px;

	p {
		display: flex;
		flex-direction: row;
		padding-top: 16.5px;
		padding-left: 1.3px;
		font-size: 12px;
		color: white;
		line-height: 2px;
	}
`;
export default Impossible;
