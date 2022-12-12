import { Link } from 'react-router-dom';
import styled from 'styled-components';

interface LinkToSignProps {
	message: string;
	link: string;
	linkText: string;
}

const LinkToSign = (props: LinkToSignProps) => {
	const { message, link, linkText } = props;
	return (
		<StyledLinkToSign>
			{message}{' '}
			<Link to={link}>
				<StyledSpan>{linkText}</StyledSpan>
			</Link>
		</StyledLinkToSign>
	);
};

const StyledLinkToSign = styled.div`
	margin: 1.5rem;
	text-align: center;
`;

const StyledSpan = styled.span`
	color: ${props => props.theme.colors.buttonGreen};
	font-weight: bold;
`;

export default LinkToSign;
