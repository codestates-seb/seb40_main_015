import { useMutation, useQueryClient } from '@tanstack/react-query';
import { useNavigate } from 'react-router';
import { useAppDispatch } from '../../../redux/hooks';
import notify from '../../../utils/notify';
import { useReviewAPI } from '../../review';

const useCreateReview = (
	rentalId: number | string,
	bookId: number | string,
	content: string,
	grade: number,
) => {
	const { createReview } = useReviewAPI();
	const dispatch = useAppDispatch();
	const queryClient = useQueryClient();
	const navigate = useNavigate();
	const { mutate } = useMutation(
		() => createReview(rentalId, bookId, content, grade),
		{
			onSuccess: res => {
				queryClient.invalidateQueries({ queryKey: ['rentBookList'] });
				notify(dispatch, '리뷰 등록완료');
				navigate('/history');
			},
		},
	);

	return { mutate };
};

export default useCreateReview;
