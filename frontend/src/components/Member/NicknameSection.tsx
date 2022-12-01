import axios from 'axios';
import styled from 'styled-components';
import Input from '../common/Input';

type NicknameSectionProps = {
	data: {
		label: string;
		state: string;
		setState: Function;
		validity: boolean;
		setValidity: Function;
	};
	notify: Function;
};

const IdSection = ({ data, notify }: NicknameSectionProps) => {
	const { label, state, setState, setValidity } = data;

	const handleValidateNickname = (
		label: string,
		state: string,
		validate: Function,
	) => {
		if (label === '닉네임') {
			notify('닉네임은 15자 이하입니다');
		} else {
			const endPoint = label === '닉네임' ? 'nickname' : 'null';
			const endPointTemp = label === '닉네임' ? 'nickname' : 'null';
			state
				? axios
						.get(`/auth/signup/check${endPointTemp}?${endPoint}=${state}`)
						.then(res => {
							if (res.data.success) {
								validate(true);
								notify(`사용가능한 ${label}입니다.`);
								console.log(res);
							} else notify(res.data.message);
						})
						.catch(e => {
							notify(e.message);
						})
				: notify(`${label === '닉네임을'} 입력해주세요.`);
		}
	};

	return (
		<StyledIdSection>
			<IdWrapper key={label}>
				<Input label={label} state={state} setState={setState} maxLength={15} />
				<div
					className="overlapCheck"
					onClick={() => handleValidateNickname(label, state, setValidity)}>
					중복확인
				</div>
			</IdWrapper>
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
		top: 4rem;
		right: 4.1rem;
		cursor: pointer;
	}
`;

export default IdSection;
