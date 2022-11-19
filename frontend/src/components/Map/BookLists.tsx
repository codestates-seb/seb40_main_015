import styled from 'styled-components';

interface Props {
	bookLists: any;
}

const BookLists = (props: Props) => {
	const { bookLists } = props;
	return (
		<>
			{bookLists?.map((item: any, i: number) => (
				<List key={i}>
					<div style={{ display: 'flex' }}>
						<div className="state1"></div>
						<div className="rental">대여가능</div>
						<div className="state2"></div>
						<div className="reservation">예약가능</div>
						<div className="state3"></div>
						<div className="books">모던 자바스트립트</div>
						<div className="merchents">역삼북스</div>
					</div>
				</List>
			))}
		</>
	);
};
const Container = styled.div`
	.state1 {
		width: 12px;
		height: 12px;
		background-color: red;
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
		background-color: green;
		border-radius: 1000px;
	}
`;

const List = styled.div`
	padding-top: 30px;
	padding-bottom: 30px;
	display: flex;
	align-items: center;
	justify-content: center;
	background-color: white;
	border-bottom: 0.5px solid rgb(196, 182, 186);
`;

export default BookLists;
