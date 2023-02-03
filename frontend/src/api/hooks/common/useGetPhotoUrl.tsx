import { useMutation } from '@tanstack/react-query';
import { AxiosResponse } from 'axios';
import { axiosInstance } from '../../instance';

const config = {
	headers: { 'Content-Type': 'multipart/form-data' },
};

const useGetPhotoUrl = () => {
	return useMutation(
		(formData: FormData): Promise<AxiosResponse> =>
			axiosInstance.post(`/upload`, formData, config),
	);
};

export default useGetPhotoUrl;
