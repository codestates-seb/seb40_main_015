import styled from 'styled-components';
import Button from '../common/Button';
import { BooksProps } from './type';

const BookItemState = ({ status = '' }: BooksProps) => {
	return (
		<BookStateWrapper>
			<Button>{status}</Button>
		</BookStateWrapper>
	);
};

const BookStateWrapper = styled.div`
	display: flex;
	justify-content: center;
	align-items: center;
`;
export default BookItemState;
