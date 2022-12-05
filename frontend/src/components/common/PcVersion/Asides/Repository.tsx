import { useLocation } from 'react-router';
import styled from 'styled-components';
import repository from '../../../../assets/image/repository.png';
import qr from '../../../../assets/image/qr.png';
import { useAppSelector } from '../../../../redux/hooks';

const Repository = () => {
	const { pathname } = useLocation();
	const { isLogin } = useAppSelector(state => state.loginInfo);

	if (pathname === '/history' && !isLogin) return null;

	return (
		<StyledRepository>
			<Container>
				<div>
					<QrImg src={qr} alt="동네북 qr코드" />
				</div>
				<a href="https://github.com/codestates-seb/seb40_main_015">
					<RepoImg src={repository} alt="동네북 레포지토리 바로가기" />
				</a>
			</Container>
		</StyledRepository>
	);
};

const StyledRepository = styled.div`
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

const Container = styled.div`
	position: sticky;
	top: 6rem;
	margin: 6rem 2rem 0 4.5vw;
	display: flex;
	flex-direction: column;
	align-items: center;
`;

const QrImg = styled.img`
	width: 10rem;
	margin-bottom: 1rem;
`;

const RepoImg = styled.img`
	width: 12rem;
`;

export default Repository;
