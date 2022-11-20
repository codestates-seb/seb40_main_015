import { IoAlertCircle } from 'react-icons/io5';
import styled from 'styled-components';
import Input from '../common/Input';
import Bubble from './Bubble';

type PasswordSectionProps = {
	data: {
		label: string;
		state: string;
		setState: Function;
		validity: boolean;
		setValidity: Function;
		type: string;
	};
};

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
	display: grid;
	align-items: flex-end;
	grid-template-columns: 22rem 1px;
	.icon {
		font-size: 25px;
		color: #ff6a00;
		position: absolute;
		cursor: pointer;
	}
`;

const AlertSection = styled.div<{ error: boolean }>`
	position: relative;
	left: 1rem;
	top: 2.3rem;
	display: ${props => (props.error ? 'flex' : 'none')};
	flex-direction: ${props => props.error && 'column'};
	.bubble {
		visibility: hidden;
	}
	:hover {
		.bubble {
			visibility: visible;
		}
	}
`;

export default PasswordSection;
