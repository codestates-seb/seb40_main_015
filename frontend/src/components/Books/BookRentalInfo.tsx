import { RentalCheck, RentalInfo } from './BookElements';

interface IProps {
	legend: string;
	id: string;
	checked: boolean;
	setChecked: () => void;
	label: string;
}

const BookRentalInfo = ({ legend, id, checked, setChecked, label }: IProps) => {
	return (
		<RentalInfo>
			<legend>{legend}</legend>
			<RentalCheck>
				<input
					type="checkbox"
					required
					id={id}
					checked={checked}
					onInput={setChecked}
				/>
				<label htmlFor={id} className="checkBoxLabel">
					확인
				</label>
				<label>{label}</label>
			</RentalCheck>
		</RentalInfo>
	);
};

export default BookRentalInfo;
