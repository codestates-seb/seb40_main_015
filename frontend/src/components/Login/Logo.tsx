import styled from 'styled-components';
import logo from '../../assets/image/logo4.png';

const Logo = () => {
	return (
		<StyledLogo>
			<StyledImg src={logo} />
			<StyledDiv>동네북</StyledDiv>
		</StyledLogo>
	);
};

const StyledLogo = styled.div`
	margin-bottom: 20px;
`;

const StyledImg = styled.img`
	width: 5rem;
	margin-bottom: 5px;
`;

const StyledDiv = styled.div`
	height: 2.7rem;
	font-family: 'kotra';
	font-size: 1.8rem;
	text-align: center;
`;

export default Logo;
