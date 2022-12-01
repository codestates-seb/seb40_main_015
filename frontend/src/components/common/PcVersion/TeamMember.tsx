import React from 'react';
import styled from 'styled-components';
import { memberInfo } from './TeamMembers/memberInfo';
import MemberCard, { MemberInfo } from './TeamMembers/MemberCard';

const TeamMember = () => {
	return (
		<Container>
			{memberInfo
				.sort(() => Math.random() - 0.5)
				.map((el: MemberInfo, idx) => {
					const { name, link, img } = el;
					return <MemberCard name={name} link={link} img={img} key={idx} />;
				})}
		</Container>
	);
};

const Container = styled.div`
	height: 100%;
	margin: 10rem 4.5vw 0 2rem;
	position: sticky;
	top: 8rem;
	display: none;

	@media screen and (min-width: 1400px) {
		animation: fadeInRight 1s;
		display: flex;
		flex-direction: column;
	}
	@keyframes fadeInRight {
		0% {
			opacity: 0;
			transform: translate3d(100%, 0, 0);
		}
		to {
			opacity: 1;
			transform: translateZ(0);
		}
	}
`;
export default TeamMember;
