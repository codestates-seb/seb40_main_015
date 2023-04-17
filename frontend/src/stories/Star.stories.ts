import type { Meta, StoryObj } from '@storybook/react';
import { Star } from 'components';

const meta: Meta<typeof Star> = {
	title: 'common/Star',
	component: Star,
	argTypes: {
		reviewGrade: {
			options: [0, 1, 2, 3, 4, 5],
			control: { type: 'radio' },
		},
	},
};

export default meta;
type Story = StoryObj<typeof Star>;

export const Primary: Story = {
	args: {},
};
