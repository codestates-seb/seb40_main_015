import bookImage from '../../assets/image/dummy.png';
import styled from 'styled-components';
import Button from '../common/Button';

interface IProps {
	bookState: string;
	bookUrl: string;
	title: string;
	onClick: () => void;
}

const BookInfo = ({ bookState, bookUrl, title, onClick }: IProps) => {
	switch (bookState) {
		case 'RENTABLE':
			bookState = '대여가능';
			break;
		case 'TRADING':
			bookState = '거래중';
			break;
		case 'UNRENTABLE_UNRESERVABLE':
			bookState = '예약불가';
			break;
		case 'UNRENTABLE_RESERVABLE':
			bookState = '예약가능';
			break;
		case 'DELETED':
			bookState = '거래중단';
			break;
	}

	return (
		<>
			<Container>
				<LeftBox>
					<BookImage src={bookUrl} alt="상대 이미지" />
					<LeftContent>
						<p>{title}</p>
						<p>{bookState}</p>
					</LeftContent>
				</LeftBox>
				<Button fontSize="small" onClick={onClick}>
					채팅 종료
				</Button>
			</Container>
		</>
	);
};

const Container = styled.div`
	display: flex;
	justify-content: space-between;
	align-items: center;
	padding: 1.5rem 2rem;
	border-bottom: 1px solid #c7c6c6;
	:hover {
		background-color: ${props => props.theme.colors.grey};
	}
`;

const LeftBox = styled.div`
	display: flex;
	align-items: center;
`;

const BookImage = styled.img`
	width: 4.5rem;
	height: 5rem;
	border-radius: 5px;
`;

const LeftContent = styled.div`
	display: flex;
	flex-direction: column;
	margin-left: 1rem;
	p {
		&:first-child {
			margin-bottom: 1rem;
			font-weight: bold;
		}
		font-size: 1.2rem;
	}
`;

export default BookInfo;
