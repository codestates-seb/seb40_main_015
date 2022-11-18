import styled from 'styled-components';
import { HiSearch } from 'react-icons/hi';

const Search = () => {
	return (
		<SearchBox>
			<HiSearch size={30} color="#a7a7a7" />
			<SearchInput placeholder="책 검색" />
		</SearchBox>
	);
};

const SearchBox = styled.div`
	width: 30rem;
	height: 3rem;
	display: flex;
	align-items: center;
	border: 1px solid white;
	border-radius: 5px;
	background-color: white;
	width: 90vw;
	padding: 5px;
	box-shadow: 2px 2px 2px 2px rgba(108, 122, 137, 0.2);
`;

const SearchInput = styled.input`
	border: none;
	width: 100%;
	outline: none;
	padding-left: 10px;
	font-size: ${props => props.theme.fontSizes.paragraph};
	// height: 5rem;
`;

export default Search;
