import styled from 'styled-components';
import { useAppDispatch } from '../../redux/hooks';
import { updateBookInfo } from '../../redux/slice/bookCreateSlice';

interface SearchItemProps {
	content: {
		title: string;
		authors: string[];
		publisher: string;
	};
	setIsModalOpened: Function;
}

const SearchItem = ({ content, setIsModalOpened }: SearchItemProps) => {
	const { title, authors, publisher } = content;
	const dispatch = useAppDispatch();

	const handleBookSelect = () => {
		dispatch(updateBookInfo(content));
		setIsModalOpened(false);
	};
	return (
		<StyledSearchItem onClick={handleBookSelect}>
			<h2>{title}</h2>
			<div>
				{authors.length === 1 ? authors[0] : `${authors[0]} 외`} / {publisher}
			</div>
		</StyledSearchItem>
	);
};

const StyledSearchItem = styled.div`
	box-sizing: border-box;
	border: 1px solid ${props => props.theme.colors.grey};
	border-radius: 5px;
	padding: 10px;
	margin-bottom: 1em;
	display: flex;
	flex-direction: column;
	justify-content: space-between;
	:hover {
		background-color: ${props => props.theme.colors.unViewedNotice};
	}

	:last-child {
		margin-bottom: 0;
	}

	h2 {
		font-size: 1.3rem;
		margin-bottom: 0.3rem;
	}
`;

export default SearchItem;
