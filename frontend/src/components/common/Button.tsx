import styled from 'styled-components';

interface ButtonInterface {
	fontSize?: string;
	backgroundColor?: string;
	padding?: string;
	width?: number;
	newLine?: boolean;
}

const Button = styled.button<ButtonInterface>`
	font-size: ${props => (props.fontSize === 'small' ? 0.7 : 1.2)}rem;
	color: ${props => (props.backgroundColor === 'grey' ? 'black' : 'white')};
	background-color: ${props =>
		props.backgroundColor === 'grey'
			? props => props.theme.colors.buttonGrey
			: props => props.theme.colors.buttonGreen};
	border-radius: 5px;
	border: none;
	padding: ${props => props.padding || '7px 15px'};
	width: ${props => props.newLine && 3.1}rem;
	cursor: pointer;
`;

export default Button;

/**
 * < 사용 예시 >
 * 줄 바꿈이 필요한 작은 초록색 버튼인 경우 아래와 같이 사용합니다.
 * <Button fontSize={"small"} newLine={true}>수령 완료</Button>
 *
 * 줄 바꿈이 필요한 작은 녹색 버튼인 경우 아래와 같이 사용합니다.
 * <Button fontSize={"small"} backgroundColor={'grey'} newLine={true}>수령 완료</Button>
 *
 * 일반적인 큰 사이즈의 녹색 버튼인 경우 아래와 같이 사용합니다.
 * <Button >로그인</Button>
 *
 * 줄 바꿈이 필요 없는 작은 버튼인 경우 아래와 같이 사용합니다.
 * <Button fontSize={"small"}>취소</Button>
 */
