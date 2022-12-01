import { Link } from 'react-router-dom';
import styled from 'styled-components';
import logo from '../../../../assets/image/logo4.png';

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
	height: 70px;
	display: flex;
	align-items: center;
	margin: 0 4rem 0 8rem;
`;

const StyledImg = styled.img`
	width: 3rem;
	margin-right: 1rem;
`;

const StyledDiv = styled.div`
	font-family: 'kotra';
	font-size: 2.5rem;
	text-align: center;
	white-space: nowrap;
`;

export default Logo;
