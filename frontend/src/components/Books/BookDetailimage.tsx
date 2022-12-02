import styled from 'styled-components';

// types
import { BookDetailProps } from './type';

const BookImage = ({ book, merchant }: BookDetailProps) => {
	return (
		<BookImgWrapper>
			<BookImg src={book?.bookImgUrl} alt="Book_image" />
			{book?.state !== '대여가능' ? (
				<BookNotAvailable>
					{book?.state !== '거래중단' ? (
						<>
							{book?.state === '거래중' ? (
								''
							) : (
								<>
									<span>이미 누가 대여중이에요 😭</span>
									<span>{`${book?.rentalStart} ~ ${book?.rentalEnd}`}</span>
								</>
							)}
							<span
								className={
									book?.state === '대여중&예약가능' ? 'possible' : 'impossible'
								}>
								{book?.state === '대여중&예약가능' && '예약가능'}
								{book?.state === '대여중&예약불가' && '예약불가'}
								{book?.state === '거래중' && '거래중'}
							</span>
						</>
					) : (
						<span>{book?.state}</span>
					)}
				</BookNotAvailable>
			) : (
				''
			)}
		</BookImgWrapper>
	);
};

const BookImgWrapper = styled.div`
	width: 40vw;
	display: flex;
	justify-content: center;
	align-items: center;
	position: relative;

	min-width: 230px;
`;

const BookImg = styled.img`
	width: 100%;
	height: 100%;

	max-width: 340px;

	@media screen and (min-width: 800px) {
		margin-top: 21px;
	}
`;

const BookNotAvailable = styled.div`
	width: 100%;
	height: 100%;

	max-width: 340px;

	@media screen and (min-width: 801px) {
		margin-top: 21px;
		height: 96%;
	}

	position: absolute;
	background-color: rgba(1, 1, 1, 0.4);

	display: flex;
	flex-direction: column;
	justify-content: center;
	align-items: center;
	span {
		font-size: ${props => props.theme.fontSizes.subtitle};
		background-color: transparent;
		margin-bottom: 0.7rem;
		color: white;
	}
	.possible {
		color: #38e54d;
		font-weight: bold;
	}
	.impossible {
		color: #ff6464;
		font-weight: bold;
	}
`;

export default BookImage;
