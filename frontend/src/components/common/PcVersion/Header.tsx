import { useState } from 'react';
import styled from 'styled-components';
import home from '../../assets/image/logo4.png';

interface MenuProps {
	id: number;
	text: string;
	selected: boolean;
}

interface BoxProps {
	selected?: boolean;
}

const Header = () => {
	const [menus, setMenus] = useState<MenuProps[]>([
		{
			id: 0,
			text: '동네북',
			selected: false,
		},
		{
			id: 1,
			text: '전체조회',
			selected: false,
		},
		{
			id: 2,

			text: '대여목록',
			selected: false,
		},
		{
			id: 3,

			text: '채팅',
			selected: true,
		},
		{
			id: 4,

			text: '지도검색',
			selected: false,
		},
		{
			id: 5,

			text: '마이페이지',
			selected: false,
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
				const { id, text, selected } = menu;
				return (
					<Box
						key={id}
						selected={selected}
						onClick={() => handleChangeMenu(id)}>
						<p>{text}</p>
					</Box>
				);
			})}
		</Container>
	);
};

const Container = styled.div`
	position: fixed;
	width: 100%;
	display: flex;
	justify-content: space-around;
	align-items: center;
	text-align: center;
	bottom: 0;
	padding: 10px 0;
	border-top: 1px solid #a7a7a7;
`;

const Box = styled.div<BoxProps>`
	color: ${props => (props.selected ? '#26795D' : '#000000')};
`;

export default Header;
