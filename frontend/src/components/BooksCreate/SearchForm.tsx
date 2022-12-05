import { useState } from 'react';
import { HiOutlineSearch } from 'react-icons/hi';
import styled from 'styled-components';
import { useAppSelector } from '../../redux/hooks';
import { BookInfo } from '../Books/BookElements';
import ModalForTitle from './ModalForTitle';

const SearchForm = () => {
	const [isModalOpened, setIsModalOpened] = useState(false);
	const bookInfo = useAppSelector(
		state => state.persistedReducer.bookCreate.bookInfo,
	);
	const { title, authors, publisher } = bookInfo;
	const titleView = title.length < 20 ? title : title.slice(0, 20) + '...';

	const handleTitleClick = () => {
		setIsModalOpened(pre => !pre);
	};

	return (
		<StyledBookInfo>
			<InputWrapper>
				<input
					onClick={() => handleTitleClick()}
					type="text"
					placeholder="책 제목을 입력해 주세요."
					value={titleView}
					readOnly
				/>
				<SubmitButton>
					<SearchIcon />
				</SubmitButton>
			</InputWrapper>
			<ModalForTitle
				isModalOpened={isModalOpened}
				setIsModalOpened={setIsModalOpened}
			/>
			<AuthorAndPublisher>
				<label>저자 :</label>
				<input type="text" value={authors} readOnly disabled />
				<label>출판사 :</label>
				<input type="text" value={publisher} readOnly disabled />
			</AuthorAndPublisher>
		</StyledBookInfo>
	);
};

const StyledBookInfo = styled(BookInfo)`
	box-sizing: border-box;
	width: 100%;
	background-color: white;
	display: grid;
`;

const InputWrapper = styled.div`
	padding-bottom: 0.4rem;
	position: relative;
	padding: 0.2rem 0;
	margin-bottom: 1rem;
	border-bottom: 1px solid rgba(1, 1, 1, 0.3);
	input {
		cursor: pointer;
	}
`;

const AuthorAndPublisher = styled.div`
	display: flex;
	font-size: 14px;
	label {
		width: fit-content;
		font-size: inherit;
		white-space: nowrap;
		margin: 0;
	}
	input {
		font-size: inherit;
	}
`;

const SubmitButton = styled.button`
	width: 2.5rem;
	height: 100%;
	border: none;
	background-color: transparent;
	position: absolute;
	top: 0;
	right: 0;
	display: flex;
	justify-content: center;
	align-items: center;
	cursor: pointer;
`;

const SearchIcon = styled(HiOutlineSearch)`
	width: 1.4rem;
	height: 1.4rem;
`;

export default SearchForm;
