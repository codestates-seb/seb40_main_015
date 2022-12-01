import styled from 'styled-components';
import { FaArrowUp } from 'react-icons/fa';
import { useEffect, useState, useMemo, useRef } from 'react';
import { throttle } from 'lodash';
// 아직 안됨
const ScrollBottomButton = () => {
	const [scroll, setScroll] = useState(false);
	const beforeScrollY = useRef(100);

	useEffect(() => {
		window.addEventListener('scroll', handleScroll);
		return () => {
			window.removeEventListener('scroll', handleScroll); //clean up
		};
	}, []);

	const handleScroll = useMemo(
		() =>
			throttle(() => {
				const currentScrollY = window.scrollY;
				if (beforeScrollY.current <= currentScrollY) {
					setScroll(true);
				} else {
					setScroll(false);
				}
			}, 300),
		[beforeScrollY],
	);

	const moveTop = () => {
		window.scrollTo({ top: 5000, behavior: 'smooth' });
	};

	return (
		<CustomBox>
			<CircleFab
				aria-label="scroll to top"
				onClick={moveTop}
				scroll={scroll ? 'true' : ''}>
				<StyledIcon />
			</CircleFab>
		</CustomBox>
	);
};

const CustomBox = styled.div`
	position: fixed;
	z-index: 1000;
	opacity: 0.9;
	right: 4%;
	bottom: 5%;
`;

interface CircleFabProps {
	scroll: string;
}

const CircleFab = styled.div<CircleFabProps>`
	box-shadow: none;
	display: flex;
	align-items: center;
	justify-content: center;
	width: 4rem;
	height: 4rem;
	border-radius: 50%;
	background-color: #f48225;
	opacity: ${props => (props.scroll ? 1 : 0)};
	visibility: ${props => (props.scroll ? '' : 'hidden')};
	transition: ${props =>
		props.scroll
			? 'all 225ms cubic-bezier(0.4, 0, 0.2, 1) 0ms'
			: 'all 195ms cubic-bezier(0.4, 0, 0.2, 1) 0ms'};
	:hover {
		background-color: #da680c;
	}
`;

const StyledIcon = styled(FaArrowUp)`
	width: 2rem;
	height: 2rem;
	color: white;
`;

export default ScrollBottomButton;
