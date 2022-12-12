import { IconContext } from 'react-icons';
import styled from 'styled-components';

interface ThirdPartyIconProps {
	company: {
		icon: JSX.Element;
		iconColor?: string;
		background: string;
	};
}

const ThirdPartyIcon = ({ company }: ThirdPartyIconProps) => {
	const { icon, iconColor, background } = company;
	return (
		<IconContext.Provider value={{ color: iconColor, size: '2rem' }}>
			<ThirdPartyWrapper background={background}>{icon}</ThirdPartyWrapper>
		</IconContext.Provider>
	);
};

const ThirdPartyWrapper = styled.div<{ background: string }>`
	height: 3rem;
	width: 3rem;
	border-radius: 50%;
	background-color: ${props => props.background};
	box-shadow: 0 5px 10px -7px rgba(0, 0, 0, 1);
	display: flex;
	justify-content: center;
	align-items: center;
`;

export default ThirdPartyIcon;
