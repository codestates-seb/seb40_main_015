import { useState } from 'react';
import { useAppDispatch, useAppSelector } from '../../../redux/hooks';
import { updateUserInfo } from '../../../redux/slice/userInfoSlice';
import useGetPhotoUrl from '../common/useGetPhotoUrl';

const Avatar = () => {
	const { mutate } = useGetPhotoUrl();
	const userInfo = useAppSelector(state => state.persistedReducer.userInfo);
	const [Image, setImage] = useState<string>(userInfo.avatarUrl);
	const dispatch = useAppDispatch();

	const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
		const { files } = e.target;
		const formData = new FormData();
		if (files) {
			const fileRef = files[0];
			formData.append('img', fileRef);
			mutate(formData, {
				onSuccess: res => {
					setImage(res.data);
					dispatch(updateUserInfo({ key: 'avatarUrl', value: res.data }));
				},
			});
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
