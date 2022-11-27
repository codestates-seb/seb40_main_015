import styled from 'styled-components';
import { HiSearch } from 'react-icons/hi';
import { Dispatch, SetStateAction } from 'react';
import notify from '../../utils/notify';
import { useAppDispatch } from '../../redux/hooks';

interface SearchProps {
	searchInput: string;
	setSearchInput: Dispatch<SetStateAction<string>>;
	current: { lat: number; lon: number };
	setMerchantSector: Dispatch<SetStateAction<any>>;
	setBookSector: Dispatch<SetStateAction<any>>;
	setMerchantLists: Dispatch<SetStateAction<any>>;
	setBookLists: Dispatch<SetStateAction<any>>;
	zoomLevel: number;
	size: { width: number; height: number };
	merchantCurrentRefetch: any;
	bookCurrentRefetch: any;
}

const Search = (props: SearchProps) => {
	const {
		searchInput,
		setSearchInput,
		current,
		setMerchantSector,
		setBookSector,
		setMerchantLists,
		setBookLists,
		zoomLevel,
		size,
		merchantCurrentRefetch,
		bookCurrentRefetch,
	} = props;

	const dispatch = useAppDispatch();

	const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
		setSearchInput(e.target.value);
	};

	const handleSearchInput = (e: React.KeyboardEvent) => {
		if (e.key === 'Enter') {
			if (!searchInput) {
				merchantCurrentRefetch();
				setBookSector([]);
				setBookLists([]);
				// getTotalMerchant(
				// 	current.lat,
				// 	current.lon,
				// 	size.width,
				// 	size.height,
				// 	zoomLevel < 3 ? 3 : zoomLevel,
				// ).then(res => {
				// 	console.log(res);
				// 	setMerchantSector(res);
				// setBookSector([]);
				// setBookLists([]);
				// });
			} else {
				bookCurrentRefetch();
				setMerchantSector([]);
				setMerchantLists([]);
				// getTotalBook(
				// 	searchInput,
				// 	current.lat,
				// 	current.lon,
				// 	size.width,
				// 	size.height,
				// 	zoomLevel < 3 ? 3 : zoomLevel,
				// ).then(res => {
				// 	if (res) {
				// 		setBookSector(res);
				// 		setMerchantSector([]);
				// 		setMerchantLists([]);
				// 	} else {
				// 		notify(dispatch, `검색한 ${searchInput}가 주변에 없어요`);
				// 		setBookSector([]);
				// 		setBookLists([]);
				// 		setMerchantSector([]);
				// 		setMerchantLists([]);
				// 	}
				// });
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
	// height: 5rem;
`;

export default Search;
