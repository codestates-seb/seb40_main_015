import { useState } from 'react';
import { HiPhotograph } from 'react-icons/hi';
import styled from 'styled-components';
import useGetPhotoUrl from '../../api/hooks/useGetPhotoUrl';
import { BookInfo } from '../Books/BookElements';

const Photo = () => {
	const [imageName, setImageName] = useState('');
	const { mutate } = useGetPhotoUrl();

	const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
		const { files } = e.target;
		const formData = new FormData();
		if (files) {
			const fileRef = files[0];
			setImageName(fileRef.name);
			formData.append('img', fileRef);
			mutate(formData);
		}
	};

	return (
		<BookInfo>
			<div className="book--info__photo">
				<label htmlFor="photo">
					<Photicon />
				</label>
				<div>{imageName || 'image file'}</div>
				<input
					id="photo"
					type="file"
					accept=".png,.jpg,.jpeg"
					multiple={false}
					onChange={handleChange}
				/>
			</div>
		</BookInfo>
	);
};

const Photicon = styled(HiPhotograph)`
	color: ${props => props.theme.colors.logoGreen};
	width: 4rem;
	height: 4rem;
	cursor: pointer;
`;

export default Photo;
