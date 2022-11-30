import styled from 'styled-components';
import logo from '../../assets/image/logo1.png';

const Title = ({
	text,
	isLogo,
	marginBottom = true,
}: {
	text: string;
	isLogo?: boolean;
	marginBottom?: boolean;
}) => {
	return (
		<Text marginBottom={marginBottom}>
			{isLogo ? (
				<>
					<Logo src={logo} />
					<span>{text}</span>
				</>
			) : (
				<span>{text}</span>
			)}
		</Text>
	);
};

export default Title;

const Logo = styled.img`
	width: 50px;
	height: 50px;
	margin-right: 10px;
`;

interface TextProps {
	marginBottom: boolean;
}

const Text = styled.div<TextProps>`
	width: 100%;
	/* text-align: center; */
	padding: 1rem 0;
	margin-bottom: ${props => (props.marginBottom ? '1rem' : 0)};
	border-bottom: 1px solid #a7a7a7;

	display: flex;
	justify-content: center;
	align-items: center;
	span {
		font-size: ${props => props.theme.fontSizes.title};
		font-family: 'kotra';
	}
`;
