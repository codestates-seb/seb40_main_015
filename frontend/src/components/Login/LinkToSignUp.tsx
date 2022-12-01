import { Link } from 'react-router-dom';
import styled from 'styled-components';

const LinkToSignUp = () => {
	return (
		<StyledLinkToSignUp>
			회원이 아니신가요?{' '}
			<Link to="/signup">
				<StyledSpan>회원가입</StyledSpan>
			</Link>
		</StyledLinkToSignUp>
	);
};

const StyledLinkToSignUp = styled.div`
	margin: 1.5rem;
`;

const StyledSpan = styled.span`
	color: ${props => props.theme.colors.buttonGreen};
	font-weight: bold;
`;

export default LinkToSignUp;
