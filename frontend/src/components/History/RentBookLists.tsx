import styled from 'styled-components';
import BookItem from '../Books/BookItem';
import { useHistoryAPI } from '../../api/history';
import { useQuery } from '@tanstack/react-query';
import Animation from '../Loading/Animation';
import { dummyBooksRental } from '../../assets/dummy/books';
import LendBookUserInfo from './LendBookUserInfo';
import LendStatusButton from './LendStatusButton';
import RentStatusButton from './RentStatusButton';

const RentBookLists = () => {
	const { getRentalBookLists } = useHistoryAPI();

	const { data } = useQuery(
		['rentBookList'],
		() => getRentalBookLists().then(res => res.data),
		{ retry: 1 },
	);

	return (
		<Box>
			{dummyBooksRental.map((el: any) => (
				<>
					<BookItem
						key={el.rentalInfo.rentalId}
						bookId={el.bookInfo.bookId}
						title={el.bookInfo.title}
						bookImage={el.bookInfo.bookUrl}
						rentalfee={el.bookInfo.rentalFee}
						author={el.bookInfo.author}
						publisher={el.bookInfo.publisher}
						// merchantName={el.bookInfo.merchantName}
						status={el.rentalInfo.rentalState}
						rental={el.rentalInfo}
					/>
					{/* lendbook 참고해서 rentbook 전용 유저 인포 컴포넌트와 상태 버튼을 만들어야 할듯, 통합 컴포넌트를 만들거나 */}
					<LendBookUserInfo
						rentalInfo={el.rentalInfo}
						merchantName={el.bookInfo.merchantName}
					/>
					<LendStatusButton
						status={el.rentalInfo.rentalState}
						customerName={el.rentalInfo.customerName}
						rental={el.rentalInfo}
					/>
				</>
			))}
			{/* {data?.content.length ? (
				data?.content?.map((el: any) => (
					<BookItem
						key={el.rentalInfo.rentalId}
						bookId={el.bookInfo.bookId}
						title={el.bookInfo.title}
						bookImage={el.bookInfo.bookUrl}
						rentalfee={el.bookInfo.rentalFee}
						author={el.bookInfo.author}
						publisher={el.bookInfo.publisher}
						merchantName={el.bookInfo.merchantName}
						status={el.rentalInfo.rentalState}
						rental={el.rentalInfo}
					/>
				))
			) : (
				<EmptyBox>
					<p>빌린 책이 없어요</p>
				</EmptyBox>
			)} */}
		</Box>
	);
};

const Box = styled.div`
	/* padding: 0 1rem; */
	height: 100%;
`;

const EmptyBox = styled.div`
	width: 100%;
	height: 75vh;
	display: flex;
	justify-content: center;
	align-items: center;
	p {
		font-size: ${props => props.theme.fontSizes.subtitle};
		font-weight: 600;
	}
`;
export default RentBookLists;
