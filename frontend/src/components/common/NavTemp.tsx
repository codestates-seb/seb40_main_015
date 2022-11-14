import { useState } from 'react';
import styled from 'styled-components';
import {
	HiOutlineSearch,
	HiOutlineBookOpen,
	HiOutlineChat,
	HiOutlineUser,
	HiHome,
} from 'react-icons/hi';
import home from '../../assets/image/logo4.png';
import { Link } from 'react-router-dom';

interface MenuProps {
	id: number;
	icon: JSX.Element | JSX.Element[];
	text: string;
	path: string;
	selected: boolean;
}

const NavTemp = () => {
	const [menus, setMenus] = useState<MenuProps[]>([
		{
			id: 0,
			icon: <HiOutlineSearch size="30" />,
			text: '검색',
			selected: false,
			path: '/books/search',
		},
		{
			id: 1,
			icon: <HiOutlineBookOpen size="30" />,
			text: '대여목록',
			selected: false,
			path: '/history',
		},
		{
			id: 2,
			icon: <img src={home} alt="" width={30} height={30} />,
			text: '홈',
			selected: true,
			path: '/books',
		},
		{
			id: 3,
			icon: <HiOutlineChat size="30" />,
			text: '채팅',
			selected: false,
			path: '/chats',
		},
		{
			id: 4,
			icon: <HiOutlineUser size="30" />,
			text: '마이페이지',
			selected: false,
			path: '/profile',
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
			{menus.map(menu => {
				const { id, icon, text, selected, path } = menu;
				return (
					<Link to={path} key={id}>
						<Box selected={selected} onClick={() => handleChangeMenu(id)}>
							{icon}
							{text}
						</Box>
					</Link>
				);
			})}
		</Container>
	);
};

export default NavTemp;

const Container = styled.div`
	position: fixed;
	width: 100%;
	/* max-width: 425px; */
	display: flex;
	justify-content: space-around;
	align-items: center;
	text-align: center;
	bottom: 0;
	padding: 10px 0;
	border-top: 1px solid #a7a7a7;
`;

interface BoxProps {
	selected?: boolean;
}

const Box = styled.div<BoxProps>`
	display: flex;
	flex-direction: column;
	align-items: center;
	color: ${props => (props.selected ? '#26795D' : '#000000')};
`;
