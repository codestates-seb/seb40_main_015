import type { Meta, StoryObj } from '@storybook/react';
import { Button } from 'components';

const meta: Meta<typeof Button> = {
	title: 'common/Button',
	component: Button,
	argTypes: {
		fontSize: {
			options: ['none', 'small'],
			control: { type: 'radio' },
		},
		backgroundColor: {
			options: ['none', 'grey'],
			control: { type: 'radio' },
		},
		padding: { control: 'text' },
		width: { control: 'number' },
		newLine: { control: 'boolean' },
	},
};

export default meta;
type Story = StoryObj<typeof Button>;

export const Basic: Story = {
	render: args => {
		return <Button {...args}>Button</Button>;
	},
};
