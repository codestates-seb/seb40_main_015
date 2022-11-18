import styled from 'styled-components';
import { HiSearch } from 'react-icons/hi';
import { Dispatch, SetStateAction } from 'react';

interface SearchProps {
	searchInput: string;
	setSearchInput: Dispatch<SetStateAction<string>>;
	setReset: Dispatch<SetStateAction<boolean>>;
}

const Search = (props: SearchProps) => {
	const { searchInput, setSearchInput, setReset } = props;

	const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
		setSearchInput(e.target.value);
	};

	const handleSearchInput = (e: React.KeyboardEvent) => {
		console.log(e.key);
		if (e.key === 'Enter') {
			if (searchInput === '') {
				// 현재위치 기준 상인정보 데이터 읽어오기
			}
			// 책검색 api 요청 -> 데이터가 잇으면?
			setReset(true);
			// 없으면 ?
			// 리셋안하고 toast 팝업;
		}
	};

	return (
		<SearchBox>
			<HiSearch size={30} color="#a7a7a7" />
			<SearchInput
				placeholder="책 검색"
				value={searchInput}
				onChange={handleInputChange}
				onKeyPress={handleSearchInput}
			/>
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
