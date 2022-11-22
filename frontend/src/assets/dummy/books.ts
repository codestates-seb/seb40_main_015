export const dummyBooks = [
	{
		bookId: '1',
		title: '모던 자바스크립트',
		status: '예약가능',
		bookImage: 'sdkflasdkf',
		merchantName: '역삼북스',
	},
	{
		bookId: '2',
		title: '자바의 정석',
		status: '대여/예약불가',
		bookImage: 'sdkflasdkf',
		merchantName: '강남문고',
	},
	{
		bookId: '3',
		title: '클린 코드',
		status: '대여가능',
		bookImage: 'sdkflasdkf',
		merchantName: '책팔아요',
	},
];

export const dummyBooskDetail = [
	{
		books: {
			bookId: 1,
			title: '모던 자바스크립트',
			publisher: '모던출판사',
			rentalfee: '1000',
			content: '대충 어디어디가 찣어졌다는 내용',
			status: '대여가능',
			rentalStart: '2022-11-11',
			rentalEnd: '2022-11-21',
		},
		merchant: {
			merchantId: '1',
			name: '역삼북스',
			grade: '4',
		},
	},
];

export const dummyBooksRental = [
	{
		bookInfo: {
			bookId: '1',
			bookUrl: 'IMG_1.jpg',
			title: '자바의 정석',
			author: '남궁성',
			publisher: '도우출판',
			rental_fee: '1500',
			content: 'apple is delicious',
			location: {
				latitude: '37.5340',
				longitude: '126.7064',
			},
			bookStatus: 'TRADING',
			merchantName: 'jujang',
		},
		rentalInfo: {
			rentalId: '9',
			customerName: 'jj',
			rentalState: 'TRADING',
			rentalStartedAt: '2022-11-15T00:17:34.045376400',
			rentalDeadline: '2022-11-24T23:59:59.045376400',
			rentalReturnedAt: '',
			rentalCanceledAt: '',
		},
	},
	{
		bookInfo: {
			bookId: '8',
			bookUrl: 'IMG_2.jpg',
			title: '하루 3분 네트워크 교실',
			author: '아미노 에이지',
			publisher: '영진닷컴',
			rental_fee: '1000',
			content: 'banana is delicious',
			location: {
				latitude: '37.5340',
				longitude: '126.7064',
			},
			bookStatus: 'UNRENTABLE_UNRESERVABLE',
			merchantName: 'jujang',
		},
		rentalInfo: {
			rentalId: '8',
			customerName: 'jj',
			rentalState: 'BEING_RENTED',
			rentalStartedAt: '2022-11-15T00:17:34.045376400',
			rentalDeadline: '2022-11-24T23:59:59.045376400',
			rentalReturnedAt: '',
			rentalCanceledAt: '',
		},
	},
	{
		bookInfo: {
			bookId: '7',
			bookUrl: 'IMG_3.jpg',
			title: '알고리즘 기초',
			author: 'Jones and Bartlett',
			publisher: '홍릉과학',
			rental_fee: '2000',
			content: 'carrot is delicious',
			location: {
				latitude: '37.5340',
				longitude: '126.7064',
			},
			bookStatus: 'UNRENTABLE_RESERVABLE',
			merchantName: 'jujang',
		},
		rentalInfo: {
			rentalId: '7',
			customerName: 'jj',
			rentalState: 'RETURN_UNREVIEWED',
			rentalStartedAt: '2022-11-15T00:17:34.045376400',
			rentalDeadline: '2022-11-24T23:59:59.045376400',
			rentalReturnedAt: '2022-11-16T17:00:00.045376400',
			rentalCanceledAt: '',
		},
	},
	{
		bookInfo: {
			bookId: '6',
			bookUrl: 'IMG_5.jpg',
			title: '알고리즘 기초2',
			author: 'Jones and Bartlett',
			publisher: '홍릉과학',
			rental_fee: '2500',
			content: 'donut is delicious',
			location: {
				latitude: '37.5340',
				longitude: '126.7064',
			},
			bookStatus: 'RENTABLE',
			merchantName: 'jujang',
		},
		rentalInfo: {
			rentalId: '6',
			customerName: 'jj',
			rentalState: 'RETURN_REVIEWED',
			rentalStartedAt: '2022-11-15T00:17:34.045376400',
			rentalDeadline: '2022-11-24T23:59:59.045376400',
			rentalReturnedAt: '2022-11-16T17:00:00.045376400',
			rentalCanceledAt: '',
		},
	},
	{
		bookInfo: {
			bookId: '5',
			bookUrl: 'IMG_5.jpg',
			title: '알고리즘 기초3',
			author: 'Jones and Bartlett',
			publisher: '홍릉과학',
			rental_fee: '4000',
			content: 'egg is delicious',
			location: {
				latitude: '37.5340',
				longitude: '126.7064',
			},
			bookStatus: 'TRADING',
			merchantName: 'jujang',
		},
		rentalInfo: {
			rentalId: '5',
			customerName: 'jj',
			rentalState: 'CANCELED',
			rentalStartedAt: '2022-11-15T00:17:34.045376400',
			rentalDeadline: '2022-11-24T23:59:59.045376400',
			rentalReturnedAt: '',
			rentalCanceledAt: '2022-11-15T17:00:00.045376400',
		},
	},
	{
		bookInfo: {
			bookId: '4',
			bookUrl: 'IMG_5.jpg',
			title: '알고리즘 기초3',
			author: 'Jones and Bartlett',
			publisher: '홍릉과학',
			rental_fee: '4000',
			content: 'egg is delicious',
			location: {
				latitude: '37.5340',
				longitude: '126.7064',
			},
			bookStatus: 'TRADING',
			merchantName: 'jujang',
		},
		rentalInfo: {
			rentalId: '4',
			customerName: 'jj',
			rentalState: 'CANCELED',
			rentalStartedAt: '2022-11-15T00:17:34.045376400',
			rentalDeadline: '2022-11-24T23:59:59.045376400',
			rentalReturnedAt: '',
			rentalCanceledAt: '2022-11-15T17:00:00.045376400',
		},
	},
];

export const dummyBooksLending = [
	{
		bookInfo: {
			bookId: '1',
			bookUrl: 'IMG_1.jpg',
			title: '자바의 정석',
			author: '남궁성',
			publisher: '도우출판',
			rental_fee: '1500',
			content: 'apple is delicious',
			location: {
				lat: '37.5340',
				lon: '126.7064',
			},
			bookStatus: 'TRADING',
			merchantName: 'jujang',
		},
		rentalInfo: {
			rentalId: '8',
			customerName: 'jj',
			rentalState: 'TRADING',
			rentalStartedAt: '2022-11-15T00:17:34.045376400',
			rentalDeadline: '2022-11-24T23:59:59.045376400',
			rentalReturnedAt: '',
			rentalCanceledAt: '',
		},
	},
	{
		bookInfo: {
			bookId: '7',
			bookUrl: 'IMG_2.jpg',
			title: '하루 3분 네트워크 교실',
			author: '아미노 에이지',
			publisher: '영진닷컴',
			rental_fee: '1000',
			content: 'banana is delicious',
			location: {
				lat: '37.5340',
				lon: '126.7064',
			},
			bookStatus: 'UNRENTABLE_UNRESERVABLE',
			merchantName: 'jujang',
		},
		rentalInfo: {
			rentalId: '7',
			customerName: 'jj',
			rentalState: 'BEING_RENTED',
			rentalStartedAt: '2022-11-15T00:17:34.045376400',
			rentalDeadline: '2022-11-24T23:59:59.045376400',
			rentalReturnedAt: '',
			rentalCanceledAt: '',
		},
	},
	{
		bookInfo: {
			bookId: '6',
			bookUrl: 'IMG_3.jpg',
			title: '알고리즘 기초',
			author: 'Jones and Bartlett',
			publisher: '홍릉과학',
			rental_fee: '2000',
			content: 'carrot is delicious',
			location: {
				lat: '37.5340',
				lon: '126.7064',
			},
			bookStatus: 'UNRENTABLE_RESERVABLE',
			merchantName: 'jujang',
		},
		rentalInfo: {
			rentalId: '6',
			customerName: 'jj',
			rentalState: 'RETURN_UNREVIEWED',
			rentalStartedAt: '2022-11-15T00:17:34.045376400',
			rentalDeadline: '2022-11-24T23:59:59.045376400',
			rentalReturnedAt: '2022-11-16T17:00:00.045376400',
			rentalCanceledAt: '',
		},
	},
	{
		bookInfo: {
			bookId: '5',
			bookUrl: 'IMG_5.jpg',
			title: '알고리즘 기초2',
			author: 'Jones and Bartlett',
			publisher: '홍릉과학',
			rental_fee: '2500',
			content: 'donut is delicious',
			location: {
				lat: '37.5340',
				lon: '126.7064',
			},
			bookStatus: 'RENTABLE',
			merchantName: 'jujang',
		},
		rentalInfo: {
			rentalId: '5',
			customerName: 'jj',
			rentalState: 'RETURN_REVIEWED',
			rentalStartedAt: '2022-11-15T00:17:34.045376400',
			rentalDeadline: '2022-11-24T23:59:59.045376400',
			rentalReturnedAt: '2022-11-16T17:00:00.045376400',
			rentalCanceledAt: '',
		},
	},
	{
		bookInfo: {
			bookId: '4',
			bookUrl: 'IMG_5.jpg',
			title: '알고리즘 기초3',
			author: 'Jones and Bartlett',
			publisher: '홍릉과학',
			rental_fee: '4000',
			content: 'egg is delicious',
			location: {
				lat: '37.5340',
				lon: '126.7064',
			},
			bookStatus: 'TRADING',
			merchantName: 'jujang',
		},
		rentalInfo: {
			rentalId: '4',
			customerName: 'jj',
			rentalState: 'CANCELED',
			rentalStartedAt: '2022-11-15T00:17:34.045376400',
			rentalDeadline: '2022-11-24T23:59:59.045376400',
			rentalReturnedAt: '',
			rentalCanceledAt: '2022-11-15T17:00:00.045376400',
		},
	},
	{
		bookInfo: {
			bookId: '3',
			bookUrl: 'IMG_6.jpg',
			title: '알고리즘 기초4',
			author: 'Jones and Bartlett',
			publisher: '홍릉과학',
			rental_fee: '4000',
			content: 'egg is delicious',
			location: {
				lat: '37.5340',
				lon: '126.7064',
			},
			bookStatus: 'TRADING',
			merchantName: 'jujang',
		},
		rentalInfo: {
			rentalId: '3',
			customerName: 'jj',
			rentalState: 'CANCELED',
			rentalStartedAt: '2022-11-15T00:17:34.045376400',
			rentalDeadline: '2022-11-24T23:59:59.045376400',
			rentalReturnedAt: '',
			rentalCanceledAt: '2022-11-15T17:00:00.045376400',
		},
	},
];

export const dummyBookWish = [
	{
		bookId: '1',
		title: '모던 자바스크립트',
		imageUrl: '이미지 url',
		rentalFee: 1000,
		status: '대여중',
	},
	{
		bookId: '3',
		title: '모던 자바스크립트',
		imageUrl: '이미지 url',
		rentalFee: 1000,
		status: '대여가능',
	},
	{
		bookId: '2',
		title: '모던 자바스크립트',
		imageUrl: '이미지 url',
		rentalFee: 1000,
		status: '대여중',
	},
];
