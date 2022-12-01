import { ReviewCreateInterface } from '../redux/slice/reviewCreateSlice';

export const makeCreateReviewMessages = (
	reviewCreate: ReviewCreateInterface,
) => {
	const { bookId, reviewMessage, grade } = reviewCreate;
	return new Map([
		[!bookId, '상인명을 입력해주세요'],
		[!grade, '상인 평점을 입력해주세요'],
		[!reviewMessage, '리뷰를 입력해주세요'],
	]);
};
