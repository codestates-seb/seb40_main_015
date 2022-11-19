import { useState } from 'react';
import styled from 'styled-components';
import dummyImage from '../../assets/image/dummy.png';
import convertDate from '../../utils/convertDate';
import RentStatusButton from './RentStatusButton';
import { rentalDummy } from './dummy';
import { axiosCancleByCustomer } from '../../api/history';

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

const RentBookLists = () => {
	const [test, setTest] = useState<ListProps[]>(rentalDummy);

	return (
		<Box>
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
											{/* <p>대여기간</p> */}
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
											)}
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
