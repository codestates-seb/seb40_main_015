import { Link } from 'react-router-dom';
import styled from 'styled-components';

// 현재 책상세, 책등록 페이지에서 사용중

const Main = styled.div`
	display: flex;
	flex-direction: column;

	font-size: 20px;
	padding-bottom: 30px;
`;
const TitleWrapper = styled.div``;

const BodyContainer = styled.div`
	display: flex;
	flex-direction: column;
	justify-content: center;
	align-items: center;
	margin-bottom: 2rem;
`;

const Div = styled.fieldset`
	width: 40vh;
	border-radius: 4px;
	border: 1px solid rgba(1, 1, 1, 0.2);

	padding: 0.8rem;
	margin: 0.6rem 0;

	legend {
		padding: 0 0.4rem;
	}
`;

const BookInfo = styled(Div)`
	display: flex;
	align-items: center;

	.partition {
		width: 2px;
		height: 20px;
		background-color: rgba(1, 1, 1, 0.3);
		margin-left: 1rem;
	}

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
		font-size: 30px;
		margin-left: 1rem;
	}

	input {
		font-size: 20px;
		width: 100%;
		border: none;
		background-color: transparent;
		outline: none;
		::placeholder {
			color: rgba(1, 1, 1, 0.2);
		}
	}

	textarea {
		font-size: 20px;
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

		.book--info__title {
			margin-bottom: 1rem;
			padding: 0.2rem 0;
			padding-bottom: 0.4rem;
			border-bottom: 1px solid rgba(1, 1, 1, 0.3);
			position: relative;
		}
		.book--info__default {
			display: flex;
			input {
				font-size: 14px;
			}
		}
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

const MerchantInfo = styled(Div)`
	font-size: 1.4rem;
	display: flex;

	justify-content: space-between;
	align-items: center;

	div {
		&:nth-child(1) {
			display: flex;
			justify-content: space-between;
			align-items: center;
			cursor: pointer;
		}

		&:nth-child(2) {
			font-size: 1.1rem;

			display: flex;
			justify-content: space-between;
			align-items: center;

			width: 30%;
			height: 2rem;
			border-radius: 4px;
			border: 1px solid rgba(1, 1, 1, 0.4);
			padding: 0 0.6rem;
		}
	}
`;

const BookDsc = styled(Div)`
	height: 20vh;
	margin-bottom: 1rem;
`;
const LinkStyled = styled(Link)`
	display: flex;
	flex-direction: column;
`;

export {
	Main,
	BodyContainer,
	TitleWrapper,
	Div,
	BookInfo,
	BookRentalFee,
	MerchantInfo,
	BookDsc,
	LinkStyled,
};
