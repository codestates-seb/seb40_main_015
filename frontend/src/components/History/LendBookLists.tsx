import { useState } from 'react';
import styled from 'styled-components';
import dummyImage from '../../assets/image/dummy.png';
import convertDate from '../../utils/convertDate';
import LendStatusButton from './LendStatusButton';
import { lendDummy } from './dummy';
import { dummyBooksLending } from '../../assets/dummy/books';
import BookItem from '../Books/BookItem';

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
			lat: string;
			lon: string;
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

const LentBookLists = () => {
	const [test, setTest] = useState<ListProps[]>(lendDummy);

	return (
		<Box>
			{/* 통합본 추가 */}
			{dummyBooksLending?.map(el => (
				<Wrapper key={+el.bookInfo.bookId}>
					<BookItem
						bookId={el.bookInfo.bookId}
						title={el.bookInfo.title}
						bookImage={el.bookInfo.bookUrl}
						rentalfee={+el.bookInfo.rental_fee}
						author={el.bookInfo.author}
						publisher={el.bookInfo.publisher}
						merchantName={el.bookInfo.merchantName}
						status={el.rentalInfo.rentalState}
					/>
				</Wrapper>
			))}
			{test
				? test.map((item, i) => {
						const { bookInfo, rentalInfo } = item;
						const { bookId, author, title, publisher, merchantName } = bookInfo;
						const {
							rentalState,
							rentalStartedAt,
							rentalDeadline,
							rentalReturnedAt,
							rentalCanceledAt,
							customerName,
						} = rentalInfo;
						return (
							<Wrapper key={bookId}>
								<Container>
									<FlexBox>
										<img src={dummyImage} alt="" width={90} height={105} />
										<InfoWrapped>
											<p>{title}</p>
											<p>{merchantName}</p>
											<p>
												{author} / {publisher}
											</p>
										</InfoWrapped>
									</FlexBox>
								</Container>
								<BottomContainer>
									<UserInfoBox>
										<span>주민: {customerName}</span>
										{(rentalState === 'TRADING' ||
											rentalState === 'BEING_RENTED') && (
											<p>
												대여기간:{' '}
												{convertDate(rentalStartedAt, rentalDeadline, true)}
											</p>
										)}
										{(rentalState === 'RETURN_UNREVIEWED' ||
											rentalState === 'RETURN_REVIEWED') && (
											<p>
												대여기간:{' '}
												{convertDate(rentalStartedAt, rentalReturnedAt, true)}
											</p>
										)}
										{rentalState === 'CANCELED' && (
											<p>
												대여기간:{' '}
												{convertDate(rentalStartedAt, rentalCanceledAt, true)}
											</p>
										)}
									</UserInfoBox>
									<LendStatusButton
										status={rentalState}
										customerName={customerName}
									/>
								</BottomContainer>
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
	/* max-width: 850px; */
	display: flex;
	flex-direction: column;
	margin-bottom: 1rem;
`;

const Container = styled.div`
	width: 90vw;
	display: flex;
	justify-content: space-between;
	border: 1px solid #eaeaea;
	border-radius: 5px;
	padding: 1rem;
	margin-bottom: 0.5rem;
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
	p {
		font-size: ${props => props.theme.fontSizes.paragraph};
		margin-left: 1rem;
	}
`;

const BottomContainer = styled.div`
	width: 90vw;
	display: flex;
	justify-content: center;
	flex-direction: column;
	margin: auto;
	margin-bottom: 1rem;
`;

const UserInfoBox = styled.div`
	border: 1px solid #eaeaea;
	border-radius: 5px;
	display: flex;
	justify-content: space-evenly;
	margin-bottom: 1rem;
	padding: 1rem 0;
	background-color: white;
	span {
		font-size: ${props => props.theme.fontSizes.paragraph};
	}
`;

export default LentBookLists;
