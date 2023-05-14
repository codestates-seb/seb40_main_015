import styled from 'styled-components';
import logo from '../../assets/image/logo1.png';
import { IoArrowBack } from 'react-icons/io5';
import { useNavigate } from 'react-router-dom';

const Title = ({
	text,
	isLogo,
	marginBottom = true,
}: {
	text: string;
	isLogo?: boolean;
	marginBottom?: boolean;
}) => {
	const navigate = useNavigate();
	return (
		<Text marginBottom={marginBottom}>
			<IoArrowBack onClick={() => navigate(-1)} className="icon" />
			{isLogo ? (
				<>
					<Logo src={logo} />
					<span>{text}</span>
				</>
			) : (
				<IoArrowBack onClick={() => navigate(-1)} className="icon" />
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
	padding: 1rem 0;
	margin-bottom: ${props => (props.marginBottom ? '1rem' : 0)};
	border-bottom: 1px solid #a7a7a7;
	display: flex;
	justify-content: center;
	align-items: center;

	.icon {
		font-size: 2rem;
		position: absolute;
		left: 2rem;
		cursor: pointer;
	}

	span {
		font-size: ${props => props.theme.fontSizes.title};
		font-family: 'kotra';
	}
	@media screen and (min-width: 800px) {
		transform: translateY(-100%);
		/* visibility: hidden; */
	}
	transition: all 0.6s;
`;
