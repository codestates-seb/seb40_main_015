import styled from 'styled-components';

interface TabListsProps {
	id: number;
	name: string;
	selected: boolean;
}

const TabLists = ({
	tabs,
	handleChange,
}: {
	tabs: TabListsProps[];
	handleChange: (id: number) => void;
}) => {
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
	padding: 0.8rem 3rem;
	background-color: ${props =>
		props.selected
			? props.theme.colors.buttonGreen
			: props.theme.colors.buttonGrey};
	border: none;
	border-radius: 5px;
	box-shadow: nonoe;
	color: ${props => (props.selected ? '#FFFFFF' : '#000000')};
	cursor: pointer;
	font-size: 0.8rem;
`;
