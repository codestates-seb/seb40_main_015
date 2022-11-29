import { useState } from 'react';
import { HiPhotograph } from 'react-icons/hi';
import styled from 'styled-components';
import useGetPhotoUrl from '../../api/hooks/common/useGetPhotoUrl';
import { useAppDispatch } from '../../redux/hooks';
import { updateRentalInfo } from '../../redux/slice/bookCreateSlice';
import notify from '../../utils/notify';
import { BookInfo } from '../Books/BookElements';

const Photo = () => {
	const [imageName, setImageName] = useState('');
	const { mutate } = useGetPhotoUrl();
	const dispatch = useAppDispatch();

	const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
		const { files } = e.target;
		const formData = new FormData();
		if (files) {
			const fileRef = files[0];
			const maxSize = 2 * 1024 * 1024;
			if (fileRef.size > maxSize) {
				notify(dispatch, '사진은 2MB 이내로 등록 가능합니다.');
			} else {
				setImageName(fileRef.name);
				formData.append('img', fileRef);
				mutate(formData, {
					onSuccess: res =>
						dispatch(updateRentalInfo({ key: 'imageUrl', value: res.data })),
				});
			}
		}
	};

	return (
		<BookInfo>
			<div className="book--info__photo">
				<label htmlFor="photo">
					<Photicon />
				</label>
				<div>
					{imageName || 'image file (2MB 미만의 파일만 등록 가능합니다.)'}
				</div>
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
