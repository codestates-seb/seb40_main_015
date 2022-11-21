import { useEffect, useState } from 'react';

export default function useWindowSize(level: number = 5) {
	const [windowSize, setWindowSize] = useState({
		width: +(((window.innerWidth / 150) * 150) / 50).toFixed(2),
		height: +(((window.innerHeight / 150) * 150) / 50).toFixed(2),
	});
	const levelMenu = [0, 38, 70, 150, 330, 500];

	useEffect(() => {
		function handleResize() {
			setWindowSize({
				width: +(((window.innerWidth / 150) * levelMenu[level]) / 50).toFixed(
					2,
				),
				height: +(((window.innerHeight / 150) * levelMenu[level]) / 50).toFixed(
					2,
				),
			});
		}
		window.addEventListener('resize', handleResize);
		handleResize();
		return () => window.removeEventListener('resize', handleResize);
	}, [level]);

	return windowSize;
}
