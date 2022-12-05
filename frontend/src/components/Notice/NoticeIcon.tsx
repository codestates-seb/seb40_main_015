import styled from 'styled-components';
import { HiOutlineBell } from 'react-icons/hi';
import { useLocation, useNavigate } from 'react-router-dom';
import { useEffect } from 'react';
import { BASE_URL } from '../../constants/constants';
import { useAppDispatch, useAppSelector } from '../../redux/hooks';
import { setState } from '../../redux/slice/alarmSlice';

const NoticeIcon = () => {
	const { pathname } = useLocation();
	const navigate = useNavigate();
	const dispatch = useAppDispatch();
	const { id } = useAppSelector(state => state.loginInfo);
	const { hasNewMessage } = useAppSelector(
		state => state.persistedReducer.alarm,
	);

	useEffect(() => {
		const eventSource = new EventSource(`${BASE_URL}/sub/${id}`);
		eventSource.addEventListener('sse', () => {
			dispatch(setState(true));
		});
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	const handleButtonClick = () => {
		if (pathname === '/notice') {
			navigate(-1);
		} else {
			navigate('/notice');
		}
	};

	if (pathname === '/books/search' || pathname === '/') return null;

	return (
		<StyledNoticeIcon onClick={handleButtonClick}>
			<Red hasNewMessage={hasNewMessage} />
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
	top: 1rem;
	border-radius: 50%;
	display: flex;
	justify-content: center;
	align-items: center;
	box-shadow: 0 0 5px rgba(0, 0, 0, 0.6);
	z-index: 10;
	cursor: pointer;

	.icon {
		font-size: 3rem;
		color: ${props => props.theme.colors.grey};
	}

	@media screen and (min-width: 800px) {
		top: calc(1rem + 80px);
	}
`;

const Red = styled.div<{ hasNewMessage: boolean }>`
	display: ${props => (props.hasNewMessage ? 'block' : 'none')};
	background-color: red;
	width: 1.2rem;
	height: 1.2rem;
	border-radius: 50%;
	position: absolute;
	top: -2px;
	right: -2px;
`;

export default NoticeIcon;
