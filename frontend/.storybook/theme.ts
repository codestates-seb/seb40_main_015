// 반응형 디자인을 위한 픽셀 컨버팅 함수
const pixelToRem = (size: number) => `${size / 16}rem`;

// font size를 객체로 반환해주자.
const fontSizes = {
	title: pixelToRem(60),
	maintitle: pixelToRem(35),
	subtitle: pixelToRem(25),
	paragraph: pixelToRem(18),
};

// 자주 사용하는 색을 객체로 만들자.
const colors = {
	black: '#000000',
	grey: '#EAEAEA',
	main: '#26795D',
	background: '#FBFBFB',
	backgroundGreen: '#C6DDD5',
	logoGreen: '#016241',
	buttonGreen: '#26795D',
	buttonGrey: '#D9D9D9',
	buttonHoverGreen: '#1f966e',
	headerBorder: '#A7A7A7',
	unViewedNotice: '#C4E1D7',
	errorColor: '#ff6a00',
};

const radius = {
	small: '3px',
	base: '5px',
	large: '10px',
};

const fontWeigth = {
	thin: 400,
	base: 500,
	bold: 600,
};

const theme = {
	fontSizes,
	colors,
	fontWeigth,
	radius,
	// common,
};

export default theme;
