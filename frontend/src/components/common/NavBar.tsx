import { useState } from 'react';
import styled from 'styled-components';
import { Link, useLocation } from 'react-router-dom';
import { useAppDispatch, useAppSelector } from '../../redux/hooks';
import { MdOutlineLogin } from 'react-icons/md';
import {
	HiOutlineSearch,
	HiOutlineBookOpen,
	HiOutlineChat,
	HiOutlineUser,
	HiHome,
} from 'react-icons/hi';

import theme from '../../styles/theme';
import notify from '../../utils/notify';

interface MenuProps {
	id: number;
	icon: JSX.Element | JSX.Element[];
	text: string;
	path: string;
	selected: boolean;
}
const Unrestricted = (id: number) => {
	// 미로그인시 접근 가능한 페이지
	return id === 0 || id === 2 || id === 4;
};
const NavBar = () => {
	// login 체크 로직
	const isLogin = useAppSelector(state => state.loginInfo.isLogin);
	const dispatch = useAppDispatch();
	const location = useLocation();

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
			icon: <HiHome size="30" />,
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
			icon: isLogin ? (
				<HiOutlineUser size="30" />
			) : (
				<MdOutlineLogin size="30" />
			),
			text: isLogin ? '마이페이지' : '로그인',
			selected: false,
			path: isLogin ? '/profile' : '/login',
		},
	]);

	const handleChangeMenu = (id: number): void => {
		// login 체크 로직
		if (!Unrestricted(id) && !isLogin) notify(dispatch, '로그인이 필요합니다.');

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
					<Link
						to={Unrestricted(id) || isLogin ? path : location.pathname}
						key={id}>
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

export default NavBar;

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
	border-top: 1px solid ${props => props.theme.colors.headerBorder};
	background-color: #f6f5ef;
	z-index: 999;
`;

interface BoxProps {
	selected?: boolean;
}

const Box = styled.div<BoxProps>`
	display: flex;
	flex-direction: column;
	align-items: center;
	color: ${props => (props.selected ? theme.colors.main : theme.colors.black)};
`;
