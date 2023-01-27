import styled from 'styled-components';
import { HiSearch } from 'react-icons/hi';
import { Dispatch, SetStateAction } from 'react';
import {
	BookList,
	BookSector,
	MerchantList,
	MerchantSectorList,
} from './KaKaoMapTypes';

interface SearchProps {
	searchInput: string;
	setSearchInput: Dispatch<SetStateAction<string>>;
	setMerchantSector: Dispatch<SetStateAction<MerchantSectorList[]>>;
	setBookSector: Dispatch<SetStateAction<BookSector[]>>;
	setMerchantLists: Dispatch<SetStateAction<MerchantList[]>>;
	setBookLists: Dispatch<SetStateAction<BookList[]>>;
	merchantCurrentRefetch: any;
	bookCurrentRefetch: any;
}

const Search = (props: SearchProps) => {
	const {
		searchInput,
		setSearchInput,
		setMerchantSector,
		setBookSector,
		setMerchantLists,
		setBookLists,
		merchantCurrentRefetch,
		bookCurrentRefetch,
	} = props;

	const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
		setSearchInput(e.target.value);
	};

	const handleSearchInput = (e: React.KeyboardEvent) => {
		if (e.key === 'Enter') {
			if (!searchInput) {
				merchantCurrentRefetch();
				setBookSector([]);
				setBookLists([]);
			} else {
				bookCurrentRefetch();
				setMerchantSector([]);
				setMerchantLists([]);
			}
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
`;

export default Search;
