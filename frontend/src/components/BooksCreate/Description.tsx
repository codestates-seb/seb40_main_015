import { useState } from 'react';
import { useAppDispatch } from '../../redux/hooks';
import { updateRentalInfo } from '../../redux/slice/bookCreateSlice';
import { StyledBookInfo } from '../../pages/BooksCreatePage';

const Description = () => {
	const [text, setText] = useState('');
	const dispatch = useAppDispatch();

	const handleChange = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
		setText(e.target.value);
	};

	const handleBlur = () => {
		dispatch(updateRentalInfo({ key: 'description', value: text }));
	};

	return (
		<StyledBookInfo>
			<textarea
				placeholder="등록하실 책과 관련된 내용을 입력해주세요"
				value={text}
				onChange={handleChange}
				onBlur={handleBlur}
			/>
		</StyledBookInfo>
	);
};

export default Description;
