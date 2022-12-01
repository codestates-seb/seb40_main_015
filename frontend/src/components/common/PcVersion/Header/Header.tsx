import { useState } from 'react';
import { Link } from 'react-router-dom';
import styled from 'styled-components';
import Logo from './Logo';

interface MenuProps {
	id: number;
	text: string;
	selected: boolean;
	link: string;
}

interface BoxProps {
	selected?: boolean;
}

const Header = () => {
	const [menus, setMenus] = useState<MenuProps[]>([
		{
			id: 1,
			text: '지도검색',
			selected: false,
			link: '/books/search',
		},
		{
			id: 2,
			text: '대여목록',
			selected: false,
			link: '/history',
		},
		{
			id: 3,
			text: '채팅',
			selected: true,
			link: '/chats',
		},
	]);

	const handleChangeMenu = (id: number): void => {
		const newMenus = menus.map(menu =>
			menu.id === id
				? { ...menu, selected: true }
				: { ...menu, selected: false },
		);
		setMenus(newMenus);
	};

	return (
		<Container>
			<Logo />
			{menus.map(menu => {
				const { id, text, selected, link } = menu;
				return (
					<Link to={link} key={id}>
						<Box selected={selected} onClick={() => handleChangeMenu(id)}>
							<p>{text}</p>
						</Box>
					</Link>
				);
			})}
		</Container>
	);
};

const Container = styled.div`
	width: 100%;
	height: 70px;
	background-color: white;
	border-bottom: 1px solid #a7a7a7;
	padding: 10px 0;
	position: fixed;
	top: 0;
	display: flex;
	align-items: center;
`;

const Box = styled.div<BoxProps>`
	color: ${props => (props.selected ? '#26795D' : '#000000')};
	margin: 0 3rem;

	p {
		font-size: 1.4rem;
		font-weight: bold;
	}
`;

export default Header;
