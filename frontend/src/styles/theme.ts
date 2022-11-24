// 반응형 디자인을 위한 픽셀 컨버팅 함수
const pixelToRem = (size: number) => `${size / 16}rem`;

// font size를 객체로 반환해주자.
const fontSizes = {
	title: pixelToRem(60),
	subtitle: pixelToRem(25),
	paragraph: pixelToRem(18),
};

// const fontSize = {
//   xs: '0.5rem',
//   sm: '0.75rem',
//   base: '1rem',
//   md: '1.25rem',
//   lg: '1.5rem',
// };

// 자주 사용하는 색을 객체로 만들자.
const colors = {
	black: '#000000',
	grey: '#EAEAEA',
	main: '#26795D',
	background: '#FBFBFB',
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

// 자주 사용하는 스타일 속성을 theme으로 만들어보자.
// const common = {
// 	flexCenter: `
//     display: flex;
//     justify-contents: center;
//     align-items: center;
//   `,
// 	flexCenterColumn: `
//     display: flex;
//     flex-direction: column;
//     justify-contents: center;
//     align-items: center;
//   `,
// };

// theme 객체에 감싸서 반환한다.
const theme = {
	fontSizes,
	colors,
	fontWeigth,
	radius,
	// common,
};

export default theme;
