import { useState } from 'react';
import styled from 'styled-components';
import useAPI from '../../hooks/useAPI';
import { useAppDispatch } from '../../redux/hooks';
import notify from '../../utils/notify';
import Button from '../common/Button';
import SearchItem from './SearchItem';

interface ModalDefaultType {
	isModalOpened: boolean;
	setIsModalOpened: Function;
}

const ModalForTitle = ({
	isModalOpened,
	setIsModalOpened,
}: ModalDefaultType) => {
	const [searchText, setSearchText] = useState('');
	const [bookData, setBookData] = useState([]);
	const dispatch = useAppDispatch();
	const goNotify = (message: string) => notify(dispatch, message);
	const api = useAPI();

	const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
		e.preventDefault();
		if (searchText) {
			api
				.get(`/books/bookInfo?bookTitle=${searchText}`)
				.then(res => setBookData(res.data));
		} else goNotify('검색어를 입력해주세요');
	};

	return (
		<>
			{isModalOpened && (
				<ModalContainer>
					<DialogBox>
						<ModalForm onSubmit={handleSubmit}>
							<h1>제목으로 검색 하기</h1>
							<InputWrapper>
								<input
									value={searchText}
									onChange={e => {
										setSearchText(e.target.value);
									}}
									id="book-search"
								/>
								<StyledButton type="submit" fontSize="small">
									검색
								</StyledButton>
							</InputWrapper>
						</ModalForm>
						<SearchItems>
							{bookData.map((el, idx) => (
								<SearchItem
									key={idx}
									content={el}
									setIsModalOpened={setIsModalOpened}
								/>
							))}
						</SearchItems>
					</DialogBox>
					<Backdrop
						className="back"
						onClick={() => {
							setIsModalOpened(false);
							setSearchText('');
							setBookData([]);
						}}
					/>
				</ModalContainer>
			)}
		</>
	);
};

const ModalForm = styled.form`
	box-sizing: border-box;
	background-color: ${props => props.theme.colors.unViewedNotice};
	border-radius: 10px 10px 0 0;
	padding: 2em 1em 0 1em;
`;

const StyledButton = styled(Button)`
	height: 3rem;
	width: 4rem;
	margin-left: 0.5rem;
	white-space: nowrap;
	justify-content: sp;
`;

const ModalContainer = styled.div`
	width: 100vw;
	height: 100vh;
	position: fixed;
	top: 0;
	left: 0;
	display: flex;
	align-items: center;
	justify-content: center;
	position: fixed;
`;

const SearchItems = styled.div`
	box-sizing: border-box;
	overflow-y: scroll;
	::-webkit-scrollbar {
		display: none;
	}
	padding: 1em;
`;

const InputWrapper = styled.div`
	padding: 1rem 0;
	display: flex;
	align-items: center;

	#book-search {
		max-width: 100%;
		font-size: ${props => props.theme.fontSizes.paragraph};
		border-radius: 5px;
		border: 1px solid ${props => props.theme.colors.grey};
		background-color: white;
		padding: 10px 15px;
		:focus {
			outline: none;
			border-color: ${props => props.theme.colors.buttonGreen};
		}
	}
`;

const DialogBox = styled.dialog`
	box-sizing: border-box;
	background-color: white;
	width: 400px;
	height: 500px;
	border: none;
	border-radius: 10px;
	padding: 0;
	box-shadow: 0 0 30px rgba(30, 30, 30, 0.185);
	display: flex;
	flex-direction: column;
	z-index: 1000;
	animation: moveUp 0.5s cubic-bezier(0.165, 0.84, 0.44, 1) forwards;

	h1 {
		font-size: 1.5rem;
		text-align: center;
	}

	@keyframes moveUp {
		0% {
			transform: translateY(20px);
		}
		100% {
			transform: translateY(0);
		}
	}
`;

const Backdrop = styled.div`
	width: 100vw;
	height: 100vh;
	position: fixed;
	top: 0;
	left: 0;
	z-index: 999;
	background-color: rgba(0, 0, 0, 0.2);
`;

export default ModalForTitle;
