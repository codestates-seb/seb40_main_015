import styled from 'styled-components';

type BubbleProps = {
	type: string;
};

const Bubble = ({ type }: BubbleProps) => {
	const typeKey = type;
	const errors = {
		password: '영문, 숫자, 특수문자 포함 8글자 이상이어야 합니다.',
		passwordCheck: '비밀번호가 일치하지 않습니다.',
	};
	return (
		<StyledBubble className="bubble">
			{errors[typeKey as keyof typeof errors]}
		</StyledBubble>
	);
};

const StyledBubble = styled.div`
	max-width: 10rem;
	min-width: 10rem;
	width: 10rem;
	height: 4rem;
	text-align: center;
	line-height: 1.15rem;
	background-color: #f1f1f1;
	border: gray 1px solid;
	padding: 5px;
	border-radius: 5px;
	position: relative;
	top: 3rem;
	right: 8rem;
	display: flex;
	justify-content: center;
	align-items: center;
	z-index: 1;
`;

export default Bubble;
