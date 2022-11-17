import { IoAlertCircle } from 'react-icons/io5';
import styled from 'styled-components';
import Input from '../common/Input';
import Bubble from './Bubble';

type PasswordSectionDataProps = {
	passwordSectionData: {
		label: string;
		state: string;
		setState: Function;
		error: boolean;
		type: string;
	}[];
};

const PasswordSection = ({ passwordSectionData }: PasswordSectionDataProps) => {
	return (
		<div>
			{passwordSectionData.map(el => (
				<PasswordWrapper error={el.error}>
					<Input
						label={el.label}
						state={el.state}
						setState={el.setState}
						type="password"
					/>
					<AlertSection error={el.error}>
						<IoAlertCircle className="icon" />
						<Bubble type={el.type} />
					</AlertSection>
				</PasswordWrapper>
			))}
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
