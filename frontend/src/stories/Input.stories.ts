import type { Meta, StoryObj } from '@storybook/react';
import { Input } from 'components';

const meta: Meta<typeof Input> = {
	title: 'common/Input',
	component: Input,
	argTypes: {
		// backgroundColor: { control: 'color' },
	},
};

export default meta;
type Story = StoryObj<typeof Input>;

export const Primary: Story = {
	args: {},
};
