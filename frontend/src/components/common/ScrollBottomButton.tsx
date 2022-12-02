import styled from 'styled-components';
import { FaArrowDown } from 'react-icons/fa';
import { useEffect, useState, useMemo, useRef } from 'react';
import { throttle } from 'lodash';
// 아직 안됨
const ScrollBottomButton = () => {
	const [scroll, setScroll] = useState(false);
	const beforeScrollY = useRef(
		window.innerWidth > 500
			? document.body.scrollHeight
			: document.body.scrollHeight - 300,
	);

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
				if (beforeScrollY.current >= currentScrollY) {
					setScroll(true);
				} else {
					setScroll(false);
				}
			}, 300),
		[beforeScrollY],
	);

	const moveBottom = () => {
		window.scrollTo(0, document.body.scrollHeight);
	};

	return (
		<CustomBox>
			<CircleFab
				aria-label="scroll to bottom"
				onClick={moveBottom}
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
	right: 3%;
	bottom: 15%;
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
	background-color: ${props => props.theme.colors.main};
	opacity: ${props => (props.scroll ? 1 : 0)};
	visibility: ${props => (props.scroll ? '' : 'hidden')};
	transition: ${props =>
		props.scroll
			? 'all 225ms cubic-bezier(0.4, 0, 0.2, 1) 0ms'
			: 'all 195ms cubic-bezier(0.4, 0, 0.2, 1) 0ms'};
	:hover {
		background-color: ${props => props.theme.colors.logoGreen};
	}
`;

const StyledIcon = styled(FaArrowDown)`
	width: 2rem;
	height: 2rem;
	color: white;
`;

export default ScrollBottomButton;
