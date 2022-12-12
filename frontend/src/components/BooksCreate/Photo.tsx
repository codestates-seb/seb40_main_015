import { useState } from 'react';
import { HiPhotograph } from 'react-icons/hi';
import styled from 'styled-components';
import useGetPhotoUrl from '../../api/hooks/common/useGetPhotoUrl';
import { useAppDispatch } from '../../redux/hooks';
import { updateRentalInfo } from '../../redux/slice/bookCreateSlice';
import { StyledBookInfo } from '../../pages/BooksCreatePage';
import resizeImageToBlob from '../../utils/resizeImage';

const Photo = () => {
	const [imageName, setImageName] = useState('');
	const { mutate } = useGetPhotoUrl();
	const dispatch = useAppDispatch();

	const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
		const { files } = e.target;
		const formData = new FormData();
		if (files) {
			const fileRef = files[0];
			if (fileRef.name.length > 35) {
				setImageName(fileRef.name.substring(0, 35) + '...');
			} else {
				setImageName(fileRef.name);
			}
			resizeImageToBlob(fileRef).then((blob: Blob) => {
				formData.append('img', blob);
				mutate(formData, {
					onSuccess: res =>
						dispatch(updateRentalInfo({ key: 'imageUrl', value: res.data })),
				});
			});
		}
	};

	return (
		<StyledBookInfo>
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
		</StyledBookInfo>
	);
};

const Photicon = styled(HiPhotograph)`
	color: ${props => props.theme.colors.logoGreen};
	width: 4rem;
	height: 4rem;
	cursor: pointer;
`;

export default Photo;
