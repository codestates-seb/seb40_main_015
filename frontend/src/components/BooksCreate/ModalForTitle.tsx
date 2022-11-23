import React, { SetStateAction } from 'react';
import styled from 'styled-components';
import Button from '../common/Button';
import SearchItem from './SearchItem';

interface ModalDefaultType {
	isModalOpened: boolean;
	setIsModalOpened: React.Dispatch<SetStateAction<boolean>>;
}

const ModalForTitle = ({
	isModalOpened,
	setIsModalOpened,
}: ModalDefaultType) => {
	return (
		<>
			{isModalOpened && (
				<ModalContainer>
					<DialogBox>
						<ModalHeader>
							<h1>제목으로 검색 하기</h1>
							<InputWrapper>
								<input id="book-search" />
								<StyledButton fontSize="small">검색</StyledButton>
							</InputWrapper>
						</ModalHeader>
						<SearchItems>
							<SearchItem
								content={{ title: '제목', author: '작가', publisher: '출판사' }}
							/>
							<SearchItem
								content={{ title: '제목', author: '작가', publisher: '출판사' }}
							/>
							<SearchItem
								content={{ title: '제목', author: '작가', publisher: '출판사' }}
							/>
							<SearchItem
								content={{ title: '제목', author: '작가', publisher: '출판사' }}
							/>
							<SearchItem
								content={{ title: '제목', author: '작가', publisher: '출판사' }}
							/>
							<SearchItem
								content={{ title: '제목', author: '작가', publisher: '출판사' }}
							/>
							<SearchItem
								content={{ title: '제목', author: '작가', publisher: '출판사' }}
							/>
							<SearchItem
								content={{ title: '제목', author: '작가', publisher: '출판사' }}
							/>
							<SearchItem
								content={{ title: '제목', author: '작가', publisher: '출판사' }}
							/>
							<SearchItem
								content={{ title: '제목', author: '작가', publisher: '출판사' }}
							/>
						</SearchItems>
					</DialogBox>
					<Backdrop
						className="back"
						onClick={() => {
							setIsModalOpened(pre => !pre);
							console.log(isModalOpened);
						}}
					/>
				</ModalContainer>
			)}
		</>
	);
};

const ModalHeader = styled.div`
	box-sizing: border-box;
	background-color: ${props => props.theme.colors.unViewedNotice};
	padding: 2em 1em 0 1em;
`;

const StyledButton = styled(Button)`
	height: 3rem;
	width: 4rem;
	margin-left: 0.5rem;
	white-space: nowrap;
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
	border-radius: 3px;
	padding: 0;
	box-shadow: 0 0 30px rgba(30, 30, 30, 0.185);
	display: flex;
	flex-direction: column;
	z-index: 1000;

	h1 {
		font-size: 1.5rem;
		text-align: center;
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
