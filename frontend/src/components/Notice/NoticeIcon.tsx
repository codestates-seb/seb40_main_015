import styled from 'styled-components';
import { HiOutlineBell } from 'react-icons/hi';
import { useLocation, useNavigate } from 'react-router-dom';

const NoticeIcon = () => {
	const { pathname } = useLocation();
	const navigate = useNavigate();
	if (pathname === '/books/search') return null;

	const handleButtonClick = () => {
		if (pathname === '/notice') {
			navigate(-1);
		} else {
			navigate('/notice');
		}
	};
	return (
		<StyledNoticeIcon onClick={handleButtonClick}>
			<HiOutlineBell className="icon" />
		</StyledNoticeIcon>
	);
};

const StyledNoticeIcon = styled.div`
	background-color: ${props => props.theme.colors.buttonGreen};
	height: 4rem;
	width: 4rem;
	position: fixed;
	right: 2rem;
	top: 2rem;
	border-radius: 50%;
	display: flex;
	justify-content: center;
	align-items: center;
	box-shadow: 0 0 5px rgba(0, 0, 0, 0.6);
	cursor: pointer;

	.icon {
		font-size: 3rem;
		color: ${props => props.theme.colors.grey};
	}

	z-index: 10;
`;

export default NoticeIcon;
