import { Link } from 'react-router-dom';
import styled from 'styled-components';

// 책상세, 책등록, 대여/예약 페이지에서 사용중

const Main = styled.div`
	display: flex;
	flex-direction: column;
	align-items: center;

	padding-bottom: 30px;

	.books__detail--btn {
		width: 100%;
		max-width: 408px;
	}
`;
const TitleWrapper = styled.div`
	width: 100%;
`;

const BodyContainer = styled.div`
	display: flex;
	flex-direction: column;
	justify-content: center;
	align-items: center;
	/* margin: 2rem 0; */

	/* @media screen and (min-width: 801px) {
		flex-direction: row;
	} */
`;

const Div = styled.fieldset`
	width: 40vh;
	/* max-width: 800px; */
	border-radius: 4px;
	border: 1px solid rgba(1, 1, 1, 0.2);

	padding: 1rem 1.2rem;
	/* margin: 0.6rem 0; */
	margin: 0.7rem 0;

	legend {
		padding: 0 0.4rem;
	}
`;

const BookInfo = styled(Div)`
	display: flex;
	align-items: center;
	margin: 1rem 0;

	.book--info__photo {
		width: 100%;
		display: flex;
		justify-content: flex-start;
		align-items: center;

		#photo {
			display: none;
		}
	}

	.book--info__fee {
		&::-webkit-inner-spin-button {
			-webkit-appearance: none;
		}
	}

	label {
		font-size: ${props => [props.theme.fontSizes.subtitle]};
	}

	input {
		font-size: ${props => [props.theme.fontSizes.subtitle]};

		width: 100%;
		border: none;
		background-color: transparent;
		outline: none;
		::placeholder {
			color: rgba(1, 1, 1, 0.2);
		}
	}

	textarea {
		font-size: ${props => [props.theme.fontSizes.subtitle]};
		width: 100%;
		height: 20vh;
		background-color: transparent;
		border: none;
		outline: none;
		resize: none;
		::placeholder {
			color: rgba(1, 1, 1, 0.2);
		}
	}

	div {
		width: 100%;
	}
`;
const BookContainer = styled.div`
	display: flex;
	flex-direction: column;
	justify-content: flex-start;

	/* max-width: 340px; */
`;
const BookTitle = styled.div`
	label {
		/* font-size: ${props => props.theme.fontSizes.maintitle}; */
		font-size: 1.5rem;
	}
	margin-bottom: 0.6rem;
`;
const BookSubTitle = styled.div`
	label {
		font-size: ${props => props.theme.fontSizes.paragraph};
	}
	margin-left: 0.2rem;
`;

const Partition = styled.span`
	width: 1px;
	height: 20px;
	/* background-color: rgba(1, 1, 1, 0.2); */
	margin: 0 0.5rem;
`;
const BookRentalInfo = styled.div`
	display: flex;
	flex-direction: column;
	label {
		font-size: 1.4rem;
		margin-bottom: 0.6rem;
	}
`;
const BookRentalFee = styled(Div)`
	border: none;
	display: flex;
	justify-content: flex-end;
	align-items: center;

	input {
		margin-left: 10px;
		width: 4rem;
		height: 1.6rem;
		text-align: right;
		padding: 0 0.4rem;
		background-color: inherit;
		border: none;
		outline: none;
		border-bottom: 1px solid rgba(1, 1, 1, 0.3);
		&:focus {
			border-bottom: 1px solid rgba(1, 1, 1, 0.7);
		}
	}
	input::-webkit-inner-spin-button {
		-webkit-appearance: none;
	}
`;

const MerchantInfo = styled.div`
	display: flex;
	justify-content: space-between;
	align-items: center;
	a {
		display: flex;
		width: 100%;
		/* justify-content: space-between; */
		align-items: center;
		cursor: pointer;
		span {
			margin-bottom: 6px;
			font-size: ${props => props.theme.fontSizes.subtitle};
			/* font-size: 1.4rem; */
		}
	}
`;

const Chat = styled.div`
	display: flex;
	justify-content: flex-end;
	/* font-size: 1.1rem; */
	/* width: 30%; */
	/* height: 2rem; */
	/* border-radius: 4px; */
	/* border: 1px solid rgba(1, 1, 1, 0.4); */
	/* padding: 0 0.6rem; */
`;

const BookDsc = styled(Div)`
	min-height: 20vh;
	margin-bottom: 1.4rem;
	div {
		font-size: 1.4rem;
		line-height: 24px;
	}
`;
const LinkStyled = styled(Link)`
	display: flex;
	flex-direction: column;
`;

////////////////////////////
// book rental & booking //

const CalendarWrapper = styled.div`
	p {
		margin: 0.6rem 0;
		font-size: 14px;

		margin-bottom: 3rem;
	}
	strong {
		font-size: 1.2rem;
	}
`;
const RentalInfo = styled(BookInfo)`
	/* width: 30rem; */
	margin-bottom: 2rem;
	display: flex;
	justify-content: space-between;
	align-items: center;
`;

const RentalCheck = styled.div`
	width: 1rem;
	display: flex;
	align-items: center;

	input {
		width: 20px;
		cursor: pointer;
	}
	label {
		margin-right: 2rem;
	}
	.checkBoxLabel {
		cursor: pointer;
	}
`;

export {
	Main,
	BodyContainer,
	TitleWrapper,
	Div,
	BookInfo,
	BookRentalFee,
	MerchantInfo,
	Chat,
	RentalInfo,
	BookDsc,
	LinkStyled,
	BookContainer,
	BookTitle,
	BookSubTitle,
	Partition,
	CalendarWrapper,
	RentalCheck,
	BookRentalInfo,
};
