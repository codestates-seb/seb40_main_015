import styled from 'styled-components';
import LendStatusButton from './LendStatusButton';
import BookItem from '../Books/BookItem';
import LendBookUserInfo from './LendBookUserInfo';
import useLendBookLists from '../../api/hooks/history/useLendBookLists';
import { BookInfo, RentalInfo } from './types';

interface ILentBookListsProps {
	filters: string;
}

interface Lists {
	bookInfo: BookInfo;
	rentalInfo: RentalInfo;
}

const LentBookLists = ({ filters }: ILentBookListsProps) => {
	const { lists, hasNextPage, ref } = useLendBookLists(filters);

	return (
		<Box>
			{lists?.length ? (
				lists?.map((el: Lists) => {
					const { rentalInfo, bookInfo } = el;
					const { customerName, rentalId, rentalState } = rentalInfo;
					const { author, bookId, bookUrl, publisher, rentalFee, title } =
						bookInfo;
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
							<LendBookUserInfo rentalInfo={rentalInfo} />
							<LendStatusButton
								status={rentalState}
								customerName={customerName}
								rental={rentalInfo}
							/>
						</Wrapper>
					);
				})
			) : (
				<EmptyBox>
					<p>빌려준 책이 없어요</p>
				</EmptyBox>
			)}
			{hasNextPage ? <ScrollEnd ref={ref}>Loading...</ScrollEnd> : null}
		</Box>
	);
};

const Box = styled.div``;

const Wrapper = styled.div`
	width: 100%;
	display: flex;
	flex-direction: column;
	margin-bottom: 3rem;

	button {
		height: 3rem;
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
export default LentBookLists;
