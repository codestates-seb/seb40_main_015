import styled from 'styled-components';

export interface MemberInfo {
	name: string;
	link: string;
	img: string;
}

const MemberCard = ({ name, link, img }: MemberInfo) => {
	return (
		<StyledCard href={link}>
			<img src={img} alt={`${name}의 깃허브 바로가기`} />
			<span>{name}</span>
		</StyledCard>
	);
};

const StyledCard = styled.a`
	box-sizing: border-box;
	width: 13rem;
	height: 4.5rem;
	background-color: white;
	border-radius: 5px;
	box-shadow: 1px 1px 5px 1px rgb(182, 182, 182);
	padding: 0 30px 0 10px;
	margin-bottom: 1rem;
	display: flex;
	align-items: center;
	justify-content: space-between;

	:hover {
		background-color: white;
		box-shadow: 1px 1px 1px 1px rgb(182, 182, 182);
	}

	img {
		width: 4rem;
	}
`;

export default MemberCard;
