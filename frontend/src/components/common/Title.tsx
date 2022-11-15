import styled from 'styled-components';

const Title = ({ text, isLogo }: { text: string; isLogo?: boolean }) => {
	return (
		<Text>
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

const Text = styled.div`
	width: 100%;
	/* text-align: center; */
	padding: 1rem 0;
	margin-bottom: 1rem;
	border-bottom: 1px solid #a7a7a7;

	display: flex;
	justify-content: center;
	align-items: center;
	span {
		font-size: ${props => props.theme.fontSizes.title};
		font-family: 'kotra';
	}
`;
