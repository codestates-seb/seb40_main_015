import { useLocation } from 'react-router';
import styled from 'styled-components';
import repository from '../../../../assets/image/repository.png';
import { useAppSelector } from '../../../../redux/hooks';

const Repository = () => {
	const { pathname } = useLocation();
	const { isLogin } = useAppSelector(state => state.loginInfo);

	if (pathname === '/history' && !isLogin) return null;

	return (
		<a href="https://github.com/codestates-seb/seb40_main_015">
			<StyledImg src={repository} alt="" />
		</a>
	);
};

const StyledImg = styled.img`
	width: 13rem;
	margin: 10rem 2rem 0 4.5vw;
	position: sticky;
	top: 8rem;
	display: none;
	@media screen and (min-width: 1400px) {
		animation: fadeInLeft 1s;
		display: block;
	}
	@keyframes fadeInLeft {
		0% {
			opacity: 0;
			transform: translate3d(-100%, 0, 0);
		}
		to {
			opacity: 1;
			transform: translateZ(0);
		}
	}
`;

export default Repository;
