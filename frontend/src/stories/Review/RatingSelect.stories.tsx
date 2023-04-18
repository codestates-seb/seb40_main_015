import type { Meta } from '@storybook/react';
import { RatingSelect } from 'components';
import { useState } from 'react';

const meta: Meta<typeof RatingSelect> = {
	title: 'Review/RatingSelect',
	component: RatingSelect,
};

export default meta;

export const Primary = () => {
	const [hovered, setHovered] = useState<number | null>(null);
	const [clicked, setClicked] = useState<number>(0);
	return (
		<RatingSelect
			hovered={hovered}
			setHovered={setHovered}
			clicked={clicked}
			setClicked={setClicked}
		/>
	);
};
