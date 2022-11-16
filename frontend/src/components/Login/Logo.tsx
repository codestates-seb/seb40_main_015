import { Link } from 'react-router-dom';
import styled from 'styled-components';
import logo from '../../assets/image/logo4.png';

const Logo = () => {
	return (
		<Link to="/books">
			<StyledLogo>
				<StyledImg src={logo} />
				<StyledDiv>동네북</StyledDiv>
			</StyledLogo>
		</Link>
	);
};

const StyledLogo = styled.div`
	margin-bottom: 3rem;
`;

const StyledImg = styled.img`
	width: 9rem;
	margin-bottom: 5px;
`;

const StyledDiv = styled.div`
	font-family: 'kotra';
	font-size: 2.8rem;
	text-align: center;
`;

export default Logo;
