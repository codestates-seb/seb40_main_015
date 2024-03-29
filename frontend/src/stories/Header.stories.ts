import type { Meta, StoryObj } from '@storybook/react';
import { Header } from 'components';

const meta: Meta<typeof Header> = {
	title: 'common/Header(minWidth 801px)',
	component: Header,
};

export default meta;
type Story = StoryObj<typeof Header>;

export const Primary: Story = {
	args: {},
};
