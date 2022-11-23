import styled from 'styled-components';

interface SearchItemProps {
	content: {
		title: string;
		author: string;
		publisher: string;
	};
}

const SearchItem = ({ content }: SearchItemProps) => {
	const { title, author, publisher } = content;
	return (
		<StyledSearchItem>
			<h2>{title}</h2>
			<div>
				{author} / {publisher}
			</div>
		</StyledSearchItem>
	);
};

const StyledSearchItem = styled.div`
	box-sizing: border-box;
	border: 1px solid ${props => props.theme.colors.grey};
	padding: 10px;
	margin-bottom: 1em;
	display: flex;
	flex-direction: column;
	justify-content: space-between;

	:last-child {
		margin-bottom: 0;
	}

	h2 {
		font-size: 1.3rem;
		margin-bottom: 0.3rem;
	}
`;

export default SearchItem;
