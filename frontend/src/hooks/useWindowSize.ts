import { useEffect, useState } from 'react';

export default function useWindowSize(level: number = 5) {
	const [windowSize, setWindowSize] = useState({
		width: +(((window.innerWidth / 150) * 150) / 50).toFixed(),
		height: +(((window.innerHeight / 150) * 150) / 50).toFixed(),
	});
	const levelMenu = [0, 38, 70, 150, 330, 550];

	useEffect(() => {
		function handleResize() {
			setWindowSize({
				width: +(((window.innerWidth / 150) * levelMenu[level]) / 50).toFixed(),
				height: +(
					((window.innerHeight / 150) * levelMenu[level]) /
					50
				).toFixed(),
			});
		}
		window.addEventListener('resize', handleResize);
		handleResize();
		return () => window.removeEventListener('resize', handleResize);
	}, [level]);

	return windowSize;
}
