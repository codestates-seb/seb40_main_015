import { useEffect, useState } from 'react';
import styled from 'styled-components';

interface TabListsProps {
	tabs: any[];
}

const TabLists = ({ tabs }: TabListsProps) => {
	const [items, setItems] = useState<any[]>([]);

	useEffect(() => {
		const newItems = tabs.map((tab, i) => {
			let item = {};
			if (i === 0) {
				item = { id: i, name: tab, selected: true };
			} else {
				item = { id: i, name: tab, selected: false };
			}
			return item;
		});
		setItems(newItems);
	}, [tabs]);

	const handleTabChange = (id: number) => {
		const newItems = items.map(item =>
			item.id === id
				? { ...item, selected: true }
				: { ...item, selected: false },
		);
		setItems(newItems);
	};

	return (
		<Container>
			{items.map(item => {
				const { id, name, selected } = item;
				return (
					<Tab
						key={id}
						selected={selected}
						onClick={() => {
							handleTabChange(id);
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
