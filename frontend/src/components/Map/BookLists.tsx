import styled from 'styled-components';
import { useNavigate } from 'react-router-dom';
import { Dispatch, SetStateAction } from 'react';

interface Props {
	bookLists: any;
	setHoverLists: Dispatch<SetStateAction<any>>;
	bookListRef: any;
}

const BookLists = (props: Props) => {
	const { bookLists, setHoverLists, bookListRef } = props;

	const navigate = useNavigate();
	const handleSearchBookDetailInfo = (id: string) => {
		navigate(`/books/${id}`);
	};

	const handleHoverMap = (location: {
		latitude: number;
		longitude: number;
	}) => {
		setHoverLists(location);
	};

	return (
		<>
			<Container>
				<div className="state1" />
				<span className="string">대여가능</span>
				<div className="state4" />
				<span className="string">거래중</span>
				<div className="state2" />
				<span className="string">예약가능</span>
				<div className="state3" />
				<span className="string">대여/예약불가</span>
			</Container>
			<Box>
				{bookLists?.map((item: any, i: number) => {
					const { bookId, title, status, merchantName, location } = item;
					return (
						<List
							key={bookId}
							onClick={() => handleSearchBookDetailInfo(bookId)}
							onMouseOver={() => handleHoverMap(location)}
							onMouseOut={() => setHoverLists({ latitude: 0, longitude: 0 })}>
							<div className="bookstate">
								<span className="book">{title}</span>
								{status === '대여가능' && <div className="state1"></div>}
								{status === '거래중' && <div className="state4"></div>}
								{status === '대여중&예약가능' && <div className="state2"></div>}
								{status === '대여/예약불가' && <div className="state3"></div>}
							</div>
							<span className="merchents">{merchantName}</span>
						</List>
					);
				})}
				<div ref={bookListRef} style={{ height: '3px' }} />
			</Box>
		</>
	);
};
const Container = styled.div`
	width: 100%;
	height: 25px;
	background-color: ${props => props.theme.colors.main};
	/* border-radius: 1000px; */
	display: flex;
	flex-wrap: nowrap;
	justify-content: flex-end;
	align-items: center;
	padding-top: 5px;
	margin-left: 0;
	border-bottom: 1px solid rgb(196, 182, 186);
	padding-top: 5px;
	padding-bottom: 5px;

	.string {
		padding-right: 15px;
		padding-left: 5px;
		font-weight: bold;
	}
	.state1 {
		width: 12px;
		height: 12px;
		background-color: #009539;
		border-radius: 1000px;
	}

	.state2 {
		width: 12px;
		height: 12px;
		background-color: #ffa500;
		border-radius: 1000px;
	}

	.state3 {
		width: 12px;
		height: 12px;
		background-color: #ff0000;
		border-radius: 1000px;
	}

	.state4 {
		width: 12px;
		height: 12px;
		background-color: #1e1ef4;
		border-radius: 1000px;
	}
`;

const Box = styled.div`
	overflow-y: scroll;
	min-height: 65px;
	max-height: 185px;
`;

const List = styled.div`
	padding-top: 25px;
	padding-bottom: 25px;
	display: flex;
	align-items: center;
	justify-content: center;
	background-color: #fbfbfb;
	border-bottom: 0.5px solid rgb(196, 182, 186);
	justify-content: space-between;

	:hover {
		background-color: ${props => props.theme.colors.grey};
		font-weight: bold;
		cursor: pointer;
	}
	.bookstate {
		display: flex;
		align-items: center;
		margin-left: 15px;
	}
	.merchents {
		padding-right: 35px;
		font-size: 1.16rem;
	}
	.state {
		width: 12px;
		height: 12px;
		background-color: red;
		border-radius: 1000px;
		margin-left: 15px;
	}
	.book {
		padding-left: 15px;
		font-size: 1.16rem;
		max-width: 20rem;
		word-break: keep-all;
		text-overflow: ellipsis;
		white-space: nowrap;
		overflow: hidden;
	}

	.state1 {
		width: 12px;
		height: 12px;
		background-color: #009539;
		border-radius: 1000px;
		margin-left: 15px;
	}

	.state2 {
		width: 12px;
		height: 12px;
		background-color: #ffa500;
		border-radius: 1000px;
		margin-left: 15px;
	}

	.state3 {
		width: 12px;
		height: 12px;
		background-color: #ff0000;
		border-radius: 1000px;
		margin-left: 15px;
	}

	.state4 {
		width: 12px;
		height: 12px;
		background-color: #1e1ef4;
		border-radius: 1000px;
		margin-left: 15px;
	}
`;

export default BookLists;
