import { useEffect, useState } from 'react';
import { Link, useLocation } from 'react-router-dom';
import styled from 'styled-components';
import { useAppDispatch, useAppSelector } from '../../../../redux/hooks';
import notify from '../../../../utils/notify';
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
			id: 0,
			text: '전체도서목록',
			selected: false,
			link: '/books',
		},
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
			selected: false,
			link: '/chats',
		},
	]);

	const { pathname } = useLocation();
	const dispatch = useAppDispatch();
	const { isLogin } = useAppSelector(state => state.loginInfo);

	useEffect(() => {
		const loginOnly = ['/history', '/chats', '/profile'];
		const newMenus = menus.map(menu =>
			menu.link === pathname
				? { ...menu, selected: true }
				: { ...menu, selected: false },
		);
		setMenus(newMenus);

		if (!isLogin && loginOnly.includes(pathname))
			notify(dispatch, '로그인이 필요합니다.');
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [pathname]);

	return (
		<Container>
			<Left>
				<Logo />
				{menus.map(menu => {
					const { id, text, selected, link } = menu;
					return (
						<Link to={link} key={id}>
							<Box selected={selected}>
								<StyledP>{text}</StyledP>
							</Box>
						</Link>
					);
				})}
			</Left>
			<Link to={isLogin ? '/profile' : '/login'}>
				<Box selected={pathname === '/profile' || pathname === '/login'}>
					<StyledP>{isLogin ? '마이페이지' : '로그인'}</StyledP>
				</Box>
			</Link>
		</Container>
	);
};

const Container = styled.div`
	width: 100%;
	height: 60px;
	background-color: white;
	border-bottom: 1px solid #a7a7a7;
	padding: 10px 0;
	position: fixed;
	top: 0;
	display: flex;
	align-items: center;
	justify-content: space-between;
	z-index: 91;
	transform: translateY(-100%);

	@media screen and (min-width: 800px) {
		/* display: none; */
		transform: translateY(0);
	}
	transition: all 1s;
`;

const Left = styled.div`
	display: flex;
	align-items: center;
`;

const Box = styled.div<BoxProps>`
	color: ${props => (props.selected ? '#26795D' : '#000000')};
	margin: 0 3rem;

	@media screen and (max-width: 1000px) {
		margin: 0 2.5rem;
	}
`;

const StyledP = styled.p`
	font-size: 1.3rem;
	font-weight: bold;
	white-space: nowrap;
`;

export default Header;
