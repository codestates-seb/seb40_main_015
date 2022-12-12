import { IoAlertCircle } from 'react-icons/io5';
import styled from 'styled-components';
import Input from '../common/Input';
import Bubble from './Bubble';

interface PasswordSectionProps {
	data: {
		label: string;
		state: string;
		setState: Function;
		validity: boolean;
		setValidity: Function;
		type: string;
	};
}

const PasswordSection = ({ data }: PasswordSectionProps) => {
	const { label, state, setState, validity, type } = data;
	return (
		<div>
			<PasswordWrapper error={validity} key={label}>
				<Input
					label={label}
					state={state}
					setState={setState}
					type="password"
					placeholder="영문, 숫자, 특수문자 포함 8글자"
				/>
				<AlertSection error={validity}>
					<IoAlertCircle className="icon" />
					<Bubble type={type} />
				</AlertSection>
			</PasswordWrapper>
		</div>
	);
};

const PasswordWrapper = styled.div<{ error: boolean }>`
	display: flex;
	align-items: flex-end;
	grid-template-columns: 22rem 1px;
	position: relative;
	.icon {
		font-size: 25px;
		color: ${props => props.theme.colors.errorColor};
		position: absolute;
		cursor: pointer;
	}
`;

const AlertSection = styled.div<{ error: boolean }>`
	height: 1px;
	width: 1px;
	position: absolute;
	right: -1rem;
	top: 1rem;
	display: ${props => (props.error ? 'none' : 'flex')};
	flex-direction: ${props => props.error && 'column'};
	.bubble {
		visibility: hidden;
	}
	:hover {
		.bubble {
			visibility: visible;
		}
	}

	@media screen and (max-width: 800px) {
		top: 3.5rem;
	}
`;

export default PasswordSection;
