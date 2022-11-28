import axios from 'axios';
import { useState } from 'react';
import { HiPhotograph } from 'react-icons/hi';
import styled from 'styled-components';
import { BASE_URL } from '../../../constants/constants';
import { useAppDispatch } from '../../../redux/hooks';
import { updateRentalInfo } from '../../../redux/slice/bookCreateSlice';
import { BookInfo } from '../../Books/BookElements';

const Avatar = () => {
	const [imageName, setImageName] = useState('');
	const dispatch = useAppDispatch();

	const [Image, setImage] = useState<string>(
		'https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png',
	);
	const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
		const { files } = e.target;
		const formData = new FormData();
		if (files) {
			const fileRef = files[0];
			setImageName(fileRef.name);
			formData.append('img', fileRef);
			axios
				.post(`${BASE_URL}/upload`, formData)
				.then(res =>
					dispatch(updateRentalInfo({ key: 'imageUrl', value: res.data })),
				);
		}
	};

	return (
		<div className="avatar">
			<label htmlFor="photo">
				<img className="image" src={Image} alt="profile image" />
			</label>
			<input
				id="photo"
				type="file"
				style={{ display: 'none' }}
				accept="image/jpg,image/png,image/jpeg"
				name="profile_img"
				onChange={handleChange}
			/>
		</div>
	);
};

export default Avatar;
