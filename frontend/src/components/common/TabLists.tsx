import styled from 'styled-components';

interface TabListsProps {
	tabs: { id: number; name: string; selected: boolean }[];
	handleChange: (id: number) => void;
}

const TabLists = (props: TabListsProps) => {
	const { tabs, handleChange } = props;
	return (
		<Container>
			{tabs.map(tab => {
				const { id, name, selected } = tab;
				return (
					<Tab
						key={id}
						selected={selected}
						onClick={() => {
							handleChange(id);
						}}>
						{name}
					</Tab>
				);
			})}
		</Container>
	);
};

export default TabLists;

const Container = styled.div`
	width: 100%;
	display: flex;
	justify-content: space-evenly;
	margin: 1rem 0;
`;

interface TabProps {
	selected: boolean;
}

const Tab = styled.button<TabProps>`
	padding: 0.8rem 5rem;
	width: 40%;
	max-width: 300px;
	background-color: ${props =>
		props.selected
			? props.theme.colors.buttonGreen
			: props.theme.colors.buttonGrey};
	border: none;
	border-radius: 5px;
	box-shadow: nonoe;
	color: ${props => (props.selected ? '#FFFFFF' : '#000000')};
	cursor: pointer;
	font-size: ${props => props.theme.fontSizes.paragraph};
	:hover {
		background-color: ${props =>
			props.selected ? props.theme.colors.buttonHoverGreen : '#CFCFCF'};
	}
`;
