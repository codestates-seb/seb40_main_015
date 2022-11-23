import styled from 'styled-components';

interface SearchItemProps {
	content: {
		title: string;
		authors: string[];
		publisher: string;
	};
	setTitle: Function;
}

const SearchItem = ({ content, setTitle }: SearchItemProps) => {
	const { title, authors, publisher } = content;
	const handleBookSelect = () => {
		setTitle(title);
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
