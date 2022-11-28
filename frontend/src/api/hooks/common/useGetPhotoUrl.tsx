import { useMutation } from '@tanstack/react-query';
import { axiosInstance } from '../..';

const config = {
	headers: { 'Content-Type': 'multipart/form-data' },
};

const useGetPhotoUrl = () => {
	return useMutation((formData: FormData) =>
		axiosInstance.post(`/upload`, formData, config),
	);
};

export default useGetPhotoUrl;
