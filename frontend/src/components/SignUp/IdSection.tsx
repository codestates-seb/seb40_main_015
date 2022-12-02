import axios from 'axios';
import styled from 'styled-components';
import { BASE_URL, USERID_REGEX } from '../../constants/constants';
import Input from '../common/Input';

type IdSectionProps = {
	data: {
		label: string;
		state: string;
		setState: Function;
		validity: boolean;
		setValidity: Function;
	};
	notify: Function;
};

const IdSection = ({ data, notify }: IdSectionProps) => {
	const { label, state, setState, setValidity } = data;

	const handleValidateClick = (
		label: string,
		state: string,
		validate: Function,
	) => {
		if (label === '아이디' && !USERID_REGEX.test(state)) {
			notify('아이디는 영문과 숫자로만 입력해주세요.');
		} else {
			const endPoint = label === '아이디' ? 'id' : 'nickname';
			state
				? axios
						.get(
							`${BASE_URL}/auth/signup/check${endPoint}?${endPoint}=${state}`,
						)
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
				: notify(
						`${label === '아이디' ? '아이디를' : '닉네임을'} 입력해주세요.`,
				  );
		}
	};

	return (
		<StyledIdSection>
			<IdWrapper key={label}>
				<Input label={label} state={state} setState={setState} maxLength={15} />
				<div
					className="overlapCheck"
					onClick={() => handleValidateClick(label, state, setValidity)}>
					중복확인
				</div>
			</IdWrapper>
		</StyledIdSection>
	);
};

const StyledIdSection = styled.div`
	width: 100%;
	display: grid;
`;

const IdWrapper = styled.div`
	/* min-width: 117.5%; */
	width: 100%;
	display: flex;
	flex-wrap: wrap;
	position: relative;
	/* grid-template-columns: 22rem 1px; */
	.overlapCheck {
		width: 4.1rem;
		height: 1rem;
		background-color: transparent;
		color: ${props => props.theme.colors.buttonGreen};
		font-weight: bold;
		position: absolute;
		top: 45%;
		right: 0.5rem;
		cursor: pointer;

		@media screen and (max-width: 800px) {
			top: 65%;
		}
	}
`;

export default IdSection;
