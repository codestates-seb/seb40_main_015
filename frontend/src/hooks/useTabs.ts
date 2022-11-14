import { useEffect, useState } from 'react';

interface Props {
	id: number;
	name: string;
	selected: boolean;
}

type ReturnTypes = [Props[], Props, (id: number) => void];

function useTabs(values: string[]): ReturnTypes {
	const [items, setItems] = useState<any[]>([]);
	const [curTab, setCurTab] = useState(items[0]);

	useEffect(() => {
		const newItems = values.map((value, i) => {
			let item = {};
			if (i === 0) {
				item = { id: i, name: value, selected: true };
			} else {
				item = { id: i, name: value, selected: false };
			}
			return item;
		});
		setItems(newItems);
	}, []);

	useEffect(() => {
		const filterTab = items.filter(item => item.selected === true);
		setCurTab(filterTab[0]);
	}, [items]);

	const handleTabChange = (id: number) => {
		const newItems = items?.map(item =>
			item.id === id
				? { ...item, selected: true }
				: { ...item, selected: false },
		);
		setItems(newItems);
	};

	return [items, curTab, handleTabChange];
}

export default useTabs;
