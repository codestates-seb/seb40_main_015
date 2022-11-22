import { useState } from 'react';
import styled from 'styled-components';
import dummyImage from '../../assets/image/dummy.png';
import convertDate from '../../utils/convertDate';
import RentStatusButton from './RentStatusButton';
import { rentalDummy } from './dummy';

import BookItem from '../Books/BookItem';
import { dummyBooksRental } from '../../assets/dummy/books';
import { RentalProps } from '../Books/type';
import { useHistoryAPI } from '../../api/history';

interface ListProps {
	bookInfo: {
		bookId: string;
		bookUrl: string;
		title: string;
		author: string;
		publisher: string;
		rental_fee: string;
		bookDescription: string;
		location: {
			latitude: string;
			longitude: string;
		};
		bookStatus: string;
		merchantName: string;
	};
	rentalInfo: {
		rentalId: string;
		customerName: string;
		rentalState: string;
		rentalStartedAt: string;
		rentalDeadline: string;
		rentalReturnedAt: string;
		rentalCanceledAt: string;
	};
}
const RentalPeriodConversion = ({
	rentalState,
	rentalStartedAt,
	rentalDeadline,
	rentalReturnedAt,
	rentalCanceledAt,
}: RentalProps) => {
	{
		/* <p>대여기간</p> */
	}
	if (rentalState === 'TRADING' || rentalState === 'BEING_RENTED')
		return <p>{convertDate(rentalStartedAt, rentalDeadline, true)}</p>;
	if (rentalState === 'RETURN_UNREVIEWED' || rentalState === 'RETURN_REVIEWED')
		return <p>{convertDate(rentalStartedAt, rentalReturnedAt, true)}</p>;
	if (rentalState === 'CANCELED')
		return <p>{convertDate(rentalStartedAt, rentalCanceledAt, true)}</p>;
};

const RentBookLists = () => {
	const { axiosCancleByCustomer } = useHistoryAPI();
	const [test, setTest] = useState<ListProps[]>(rentalDummy);

	return (
		<Box>
			{/* 통합본 추가 */}
			{dummyBooksRental?.map(el => (
				<BookItem
					key={+el.bookInfo.bookId}
					bookId={el.bookInfo.bookId}
					title={el.bookInfo.title}
					bookImage={el.bookInfo.bookUrl}
					rentalfee={+el.bookInfo.rental_fee}
					author={el.bookInfo.author}
					publisher={el.bookInfo.publisher}
					merchantName={el.bookInfo.merchantName}
					status={el.rentalInfo.rentalState}
					rental={el.rentalInfo}
				/>
			))}

			{/* 컴포넌트 통합 전 */}
			{test
				? test.map((item, i) => {
						const { bookInfo, rentalInfo } = item;
						const {
							bookId,
							bookUrl,
							title,
							author,
							publisher,
							bookStatus,
							merchantName,
						} = bookInfo;
						const {
							rentalId,
							rentalState,
							rentalStartedAt,
							rentalDeadline,
							rentalReturnedAt,
							rentalCanceledAt,
						} = rentalInfo;
						return (
							<Wrapper key={Number(bookId)}>
								<Container>
									<FlexBox>
										<img src={dummyImage} alt="" width={90} height={105} />
										<InfoWrapped>
											<p>{title}</p>
											<p>{merchantName}</p>
											<p>
												{author} / {publisher}
											</p>
											{RentalPeriodConversion(rentalInfo)}
											{/* <p>대여기간</p>
											{(rentalState === 'TRADING' ||
												rentalState === 'BEING_RENTED') && (
												<p>
													{convertDate(rentalStartedAt, rentalDeadline, true)}
												</p>
											)}
											{(rentalState === 'RETURN_UNREVIEWED' ||
												rentalState === 'RETURN_REVIEWED') && (
												<p>
													{convertDate(rentalStartedAt, rentalReturnedAt, true)}
												</p>
											)}
											{rentalState === 'CANCELED' && (
												<p>
													{convertDate(rentalStartedAt, rentalCanceledAt, true)}
												</p>
											)} */}
										</InfoWrapped>
									</FlexBox>
									<RentStatusButton
										status={rentalState}
										merchantName={merchantName}
									/>
								</Container>
							</Wrapper>
						);
				  })
				: null}
		</Box>
	);
};

const Box = styled.div`
	/* padding: 0 1rem; */
`;

const Wrapper = styled.div`
	width: 100%;
	max-width: 850px;
	display: flex;
	flex-direction: column;
	align-items: center;
	margin-bottom: 1rem;
`;

const Container = styled.div`
	width: 90vw;
	display: flex;
	justify-content: space-between;
	margin-bottom: 0.5rem;
	border: 1px solid #eaeaea;
	border-radius: 5px;
	padding: 1rem;
	background-color: white;
`;

const FlexBox = styled.div`
	display: flex;
`;

const InfoWrapped = styled.div`
	display: flex;
	margin-left: 0.3rem;
	flex-direction: column;
	justify-content: space-evenly;
	justify-items: stretch;
	background-color: white;
	p {
		font-size: ${props => props.theme.fontSizes.paragraph};
		margin-left: 1rem;
		background-color: white;
	}
`;

export default RentBookLists;
