import { Dispatch, SetStateAction, useState } from 'react';
import { HiOutlineSearch } from 'react-icons/hi';
import styled from 'styled-components';
import ModalForTitle from './ModalForTitle';

interface SearchFormProps {
	title: string;
	setTitle: Dispatch<SetStateAction<string>>;
}

const SearchForm = ({ title, setTitle }: SearchFormProps) => {
	const [isModalOpened, setIsModalOpened] = useState(true);
	const handleTitleSubmit = (
		e: React.FormEvent<HTMLFormElement>,
		title: string,
	) => {
		e.preventDefault();
		console.log(title);
	};

	const handleTitleClick = () => {
		setIsModalOpened(pre => !pre);
	};

	return (
		<StyledSearchForm onSubmit={e => handleTitleSubmit(e, title)}>
			<input
				onClick={() => handleTitleClick()}
				type="text"
				placeholder="책 제목을 입력해 주세요."
				value={title}
			/>
			<SubmitButton>
				<SearchIcon />
			</SubmitButton>
			<ModalForTitle
				isModalOpened={isModalOpened}
				setIsModalOpened={setIsModalOpened}
			/>
		</StyledSearchForm>
	);
};

const StyledSearchForm = styled.form`
	margin-bottom: 1rem;
	padding: 0.2rem 0;
	padding-bottom: 0.4rem;
	border-bottom: 1px solid rgba(1, 1, 1, 0.3);
	position: relative;
	input {
		cursor: pointer;
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
