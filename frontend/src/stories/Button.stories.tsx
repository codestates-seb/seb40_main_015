import type { Meta, StoryObj } from '@storybook/react';
import { Button } from 'components';

const meta: Meta<typeof Button> = {
	title: 'common/Button',
	component: Button,
	argTypes: {
		// backgroundColor: { control: 'color' },
	},
};

export default meta;
type Story = StoryObj<typeof Button>;

export const Primary: Story = {
	render: () => {
		return <Button>123</Button>;
	},
};
