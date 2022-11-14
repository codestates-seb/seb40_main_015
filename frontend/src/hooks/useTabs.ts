import { useEffect, useState } from 'react';

interface Props {
	id?: number;
	name?: string;
	selected?: boolean;
}

const initialValue = [
	{ id: 0, name: '책 ', selected: true },
	{ id: 1, name: '리뷰 보기', selected: false },
];

function useTabs(values: string[]) {
	const [items, setItems] = useState<Props[]>(initialValue);

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

	const handleTabChange = (id: number) => {
		const newItems = items?.map(item =>
			item.id === id
				? { ...item, selected: true }
				: { ...item, selected: false },
		);
		setItems(newItems);
	};

	return [items, handleTabChange];
}

export default useTabs;
