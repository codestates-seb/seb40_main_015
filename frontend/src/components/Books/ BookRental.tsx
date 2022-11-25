import styled from 'styled-components';
import { BookInfo } from './BookElements';

const CalendarWrapper = styled.div`
	p {
		margin: 0.6rem 0;
		font-size: 14px;

		margin-bottom: 3rem;
	}
`;

const RentalInfo = styled(BookInfo)`
	margin-bottom: 4rem;
`;

export { RentalInfo, CalendarWrapper };
