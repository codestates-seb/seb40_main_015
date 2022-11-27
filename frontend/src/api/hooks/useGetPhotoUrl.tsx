import { useMutation } from '@tanstack/react-query';
import { axiosInstance } from '..';
import { useAppDispatch } from '../../redux/hooks';
import { updateRentalInfo } from '../../redux/slice/bookCreateSlice';

const config = {
	headers: { 'Content-Type': 'multipart/form-data' },
};

const useGetPhotoUrl = () => {
	const dispatch = useAppDispatch();
	return useMutation(
		(formData: FormData) => axiosInstance.post(`/upload`, formData, config),
		{
			onSuccess: res =>
				dispatch(updateRentalInfo({ key: 'imageUrl', value: res.data })),
		},
	);
};

export default useGetPhotoUrl;
