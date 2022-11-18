import axios from 'axios';
import styled from 'styled-components';
import { BASE_URL } from '../../constants/constants';
import Input from '../common/Input';

type IdSectionDataProps = {
	idSectionData: {
		label: string;
		state: string;
		setState: Function;
		validate: Function;
	}[];
	notify: Function;
};

const IdSection = ({ idSectionData, notify }: IdSectionDataProps) => {
	const handleValidateClick = (
		label: string,
		state: string,
		validate: Function,
	) => {
		state
			? axios
					.get(
						`${BASE_URL}auth/signup/check${
							label === '아이디' ? 'Id' : 'nickname'
						}?${state}`,
					)
					.then(res => {
						if (res.data.success) validate(true);
						else notify(res.data.message);
					})
			: notify(`${label === '아이디' ? '아이디를' : '닉네임을'} 입력해주세요.`);
	};

	return (
		<StyledIdSection>
			{idSectionData.map(el => (
				<IdWrapper key={el.label}>
					<Input label={el.label} state={el.state} setState={el.setState} />
					<div
						className="overlapCheck"
						onClick={() =>
							handleValidateClick(el.label, el.state, el.validate)
						}>
						중복확인
					</div>
				</IdWrapper>
			))}
		</StyledIdSection>
	);
};

const StyledIdSection = styled.div`
	display: grid;
`;

const IdWrapper = styled.div`
	min-width: 117.5%;
	display: grid;
	grid-template-columns: 22rem 1px;
	.overlapCheck {
		width: 4.1rem;
		height: 1rem;
		background-color: transparent;
		color: ${props => props.theme.colors.buttonGreen};
		font-weight: bold;
		position: relative;
		top: 3.4rem;
		right: 4.1rem;
		cursor: pointer;
	}
`;

export default IdSection;
