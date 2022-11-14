import styled from 'styled-components';

const Title = ({ text }: { text: string }) => {
	return <Text>{text}</Text>;
};

export default Title;

const Text = styled.p`
	width: 100%;
	font-size: 2.5rem;
	text-align: center;
	padding-bottom: 1rem;
	margin-bottom: 1rem;
	border-bottom: 1px solid #a7a7a7;
`;
