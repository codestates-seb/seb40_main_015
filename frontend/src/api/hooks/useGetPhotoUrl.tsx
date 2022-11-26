import { useMutation } from '@tanstack/react-query';
import axios from 'axios';
import { BASE_URL } from '../../constants/constants';
import { useAppDispatch } from '../../redux/hooks';
import { updateRentalInfo } from '../../redux/slice/bookCreateSlice';

const config = {
	baseURL: BASE_URL,
	headers: { 'Content-Type': 'multipart/form-data' },
};
const axiosInstance = axios.create(config);

const useGetPhotoUrl = () => {
	const dispatch = useAppDispatch();
	return useMutation(
		(formData: FormData) => axiosInstance.post(`/upload`, formData),
		{
			onSuccess: res =>
				dispatch(updateRentalInfo({ key: 'imageUrl', value: res.data })),
		},
	);
};

export default useGetPhotoUrl;
