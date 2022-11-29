import React from 'react';
import styled from 'styled-components';
import { AiFillGithub } from 'react-icons/ai';
import { FaVimeoSquare } from 'react-icons/fa';

const TeamMember = () => {
	return (
		<Container>
			<div className="member">
				<div className="name">부주용</div>
				<AiFillGithub />
				<FaVimeoSquare />
			</div>
			<div className="member">
				<div className="name">안지수</div>
				<AiFillGithub />
				<FaVimeoSquare />
			</div>
			<div className="member">
				<div className="name">이예빈</div>
				<AiFillGithub />
				<FaVimeoSquare />
			</div>
			<div className="member">
				<div className="name">우상헌</div>
				<AiFillGithub />
				<FaVimeoSquare />
			</div>
			<div className="member">
				<div className="name">송주안</div>
				<AiFillGithub />
				<FaVimeoSquare />
			</div>
			<div className="member">
				<div className="name">이성준</div>
				<AiFillGithub />
				<FaVimeoSquare />
			</div>
			<div className="member">
				<div className="name">장정욱</div>
				<AiFillGithub />
				<FaVimeoSquare />
			</div>
		</Container>
	);
};

const Container = styled.div`
	display: flex;
	width: 259px;
	flex-direction: column;
	background-color: white;
	.member {
		padding: 10px;
		justify-content: space-between;
		display: flex;
		background-color: #d9d9d9;
	}
`;
export default TeamMember;
