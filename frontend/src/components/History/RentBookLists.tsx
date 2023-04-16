import styled from 'styled-components';
import BookItem from '../Books/BookItem';
import LendBookUserInfo from './LendBookUserInfo';
import RentStatusButton from './RentStatusButton';
import useRentalBookLists from '../../api/hooks/history/useRentalBookLists';
import { BookInfo, RentalInfo } from './types';
interface IRentBookListsProps {
	filters: string;
}

interface RentalBookInfo extends BookInfo {
	merchantName: string;
}

interface Lists {
	bookInfo: RentalBookInfo;
	rentalInfo: RentalInfo;
}

const RentBookLists = ({ filters }: IRentBookListsProps) => {
	const { lists, hasNextPage, ref } = useRentalBookLists(filters);

	return (
		<Box>
			{lists?.length ? (
				<>
					{lists.map((el: Lists) => {
						const { rentalInfo, bookInfo } = el;
						const { rentalId, rentalState } = rentalInfo;
						const {
							author,
							bookId,
							bookUrl,
							publisher,
							rentalFee,
							title,
							merchantName,
							merchantId,
						} = bookInfo;
						return (
							<Wrapper key={rentalId}>
								<BookItem
									bookId={bookId}
									title={title}
									bookImage={bookUrl}
									rentalfee={rentalFee}
									author={author}
									publisher={publisher}
									status={rentalState}
									rental={rentalInfo}
								/>
								<LendBookUserInfo
									rentalInfo={rentalInfo}
									merchantName={merchantName}
									bookId={bookId}
									merchantId={merchantId}
								/>
								<RentStatusButton
									status={rentalState}
									merchantName={merchantName}
									rental={rentalInfo}
									bookId={bookId}
									title={title}
								/>
							</Wrapper>
						);
					})}
				</>
			) : (
				<EmptyBox>
					<p>빌린 책이 없어요</p>
				</EmptyBox>
			)}
			{hasNextPage && <ScrollEnd ref={ref}>Loading...</ScrollEnd>}
		</Box>
	);
};

const Box = styled.div`
	height: 100%;
`;

const Wrapper = styled.div`
	width: 100%;
	display: flex;
	flex-direction: column;
	margin-bottom: 3rem;

	position: relative;
	.cancel {
		color: black;
		padding: 0.6rem 0.8rem;
		background-color: inherit;
		border-radius: 0 5px 0 5px;
		border-left: 1px solid rgba(1, 1, 1, 0.1);
		border-bottom: 1px solid rgba(1, 1, 1, 0.1);

		position: absolute;
		top: 0;
		right: 0;
		:hover {
			background-color: ${props => props.theme.colors.grey};
		}
	}
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

const ScrollEnd = styled.div`
	background-color: #fbfbfb;
`;

export default RentBookLists;
