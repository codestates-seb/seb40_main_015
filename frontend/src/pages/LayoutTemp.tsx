import { Link } from 'react-router-dom';
import { Outlet } from 'react-router-dom';
import styled from 'styled-components';
import NavBar from '../components/common/NavBar';
import NavTemp from '../components/common/NavTemp';

const LayoutTemp = () => {
	return (
		<Main>
			<Body>
				<Outlet />
			</Body>
			<NavTemp />
			{/* <Nav>
					<li>
					<Link to={'/books/search'}>검색</Link>
					</li>
					<li>
					<Link to={'/history'}>대여목록</Link>
					</li>
					<li>
						<Link to={'/books'}>홈</Link>
						</li>
						<li>
						<Link to={'/chats'}>채팅</Link>
						</li>
						<li>
						<Link to={'/profile'}>마이페이지</Link>
						</li>
					</Nav> */}
		</Main>
	);
};
const Main = styled.div`
	display: flex;
	height: 100%;
	width: 100%;
	justify-content: center;
	width: 100%;
	height: 100%;
`;

const Body = styled.div`
	width: 100%;
	padding-bottom: 50px;
`;

const Nav = styled.ul`
	display: flex;
	justify-content: space-around;
	border: 1px solid black;
	width: 100%;
	padding: 10px;
	li {
		margin: 0px 5px;
	}
`;

export default LayoutTemp;
