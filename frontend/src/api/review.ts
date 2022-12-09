import useAPI from '../hooks/useAPI';

export const useReviewAPI = () => {
	const api = useAPI();

	const createReview = (
		rentalId: number | string,
		bookId: number | string,
		content: string,
		grade: number,
	) =>
		api
			.post(`/review/${rentalId}`, {
				bookId,
				reviewMessage: content,
				grade,
			})
			.then(res => res.data);

	const getReviewList = (merchantId: number | string) =>
		api.get(`/review/${merchantId}`).then(res => res.data);

	return { createReview, getReviewList };
};
