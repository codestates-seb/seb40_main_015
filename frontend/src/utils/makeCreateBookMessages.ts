import { bookCreateInterface } from '../redux/slice/bookCreateSlice';

export const makeCreateBookMessages = (bookCreate: bookCreateInterface) => {
	const { title } = bookCreate.bookInfo;
	const { rentalFee, description, imageUrl } = bookCreate.rentalInfo;
	return new Map([
		[!imageUrl, '도서를 촬영한 이미지를 등록해주세요.'],
		[!description, '도서 관련 특이사항을 입력해주세요.'],
		[
			rentalFee / 100 !== Math.floor(rentalFee / 100),
			'대여료를 100원 단위로 입력해주세요',
		],
		[!rentalFee, '대여료를 입력해주세요.'],
		[!title, '도서 정보를 입력해주세요.'],
	]);
};
