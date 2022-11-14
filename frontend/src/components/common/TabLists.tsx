import styled from 'styled-components';

interface TabListsProps {
	tabs: string[];
}

const TabLists = ({ tabs }: TabListsProps) => {
	return (
		<Container>
			{tabs.map(tab => {
				return <Tab key={tab}>{tab}</Tab>;
			})}
		</Container>
	);
};

export default TabLists;

const Container = styled.div`
	width: 100%;
	display: flex;
	justify-content: space-evenly;
`;

const Tab = styled.button`
	padding: 0.8rem 3rem;
	background-color: ${props => props.theme.colors.buttonGreen};
	border: none;
	border-radius: 5px;
	box-shadow: nonoe;
	color: white;
	cursor: pointer;
	margin: 1rem 0;
	font-size: 0.8rem;
`;
