import type { Meta, StoryObj } from '@storybook/react';
import { NavBar } from 'components';

const meta: Meta<typeof NavBar> = {
	title: 'common/NavBar(maxWidth 800px)',
	component: NavBar,
};

export default meta;
type Story = StoryObj<typeof NavBar>;

export const Primary: Story = {
	args: {},
};
