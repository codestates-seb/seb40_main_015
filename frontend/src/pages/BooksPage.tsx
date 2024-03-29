import styled from 'styled-components';
import { useNavigate } from 'react-router-dom';
import { useEffect, useRef } from 'react';

import { Title, Button, BookItem, Animation } from 'components';

//hooks
import { useAppDispatch, useAppSelector } from 'redux/hooks';
import { useGetBooksList } from 'api/hooks/books/useGetBooksList';
import { useGetAddress } from 'api/hooks/member/useGetAddress';

//etc
import notify from 'utils/notify';

const BooksPage = () => {
	const { isLogin, id } = useAppSelector(state => state.loginInfo);
	const dispatch = useAppDispatch();
	const navigate = useNavigate();
	const target = useRef<HTMLDivElement>(null);

	const {
		booksListData,
		fetchNextPage,
		isLoading,
		hasNextPage,
		isFetchingNextPage,
	} = useGetBooksList();

	useEffect(() => {
		const observer = new IntersectionObserver(
			entries => {
				const [entry] = entries;
				if (!entry.isIntersecting) return;
				fetchNextPage();
			},
			{
				root: null,
				threshold: 0.4,
			},
		);
		observer.observe(target.current as Element);
		return () => observer.disconnect();
	}, []);

	const address = useGetAddress(id);

	const handleClickCreate = () => {
		if (!isLogin) notify(dispatch, '로그인이 필요합니다');
		else if (!address) {
			if (
				window.confirm(
					'책 등록을 위해 위치정보 등록이 필요합니다.\n마이페이지로 이동할까요?',
				)
			) {
				navigate('/profile');
				notify(dispatch, '위치정보 등록을 위해 회원 정보를 수정해 주세요.');
			} else {
				return;
			}
		} else navigate('/books/create');
	};

	return (
		<Main>
			<TitleWrapper>
				<Title text="동네북" isLogo={true} />
			</TitleWrapper>

			<BtnWrapper>
				<LinkStyled>
					<Button onClick={handleClickCreate}>책 등록하기</Button>
				</LinkStyled>
			</BtnWrapper>

			<BooksList>
				{isLoading ? (
					<Animation width={20} height={20} />
				) : (
					booksListData?.pages?.map(el =>
						el?.content?.map(el => {
							return (
								<BookItem
									key={+el.bookId}
									bookId={el.bookId}
									title={el.title}
									bookImage={el.bookImage}
									status={el.status}
									rentalfee={el.rentalFee}
									merchantName={el.merchantName}
									styleGrid={true}
								/>
							);
						}),
					)
				)}
				<ScrollEnd ref={target} className={`${hasNextPage ? '' : 'hidden'}`}>
					{isFetchingNextPage ? <p>Loading more books ...</p> : ''}
				</ScrollEnd>
			</BooksList>
		</Main>
	);
};

const Main = styled.div`
	display: flex;
	flex-direction: column;

	align-items: center;
	//가로스크롤 없애기
	overflow-x: hidden;

	.hidden {
		display: none;
	}
`;

const TitleWrapper = styled.div`
	width: 100%;
	@media screen and (min-width: 800px) {
		transform: translateY(-100%);
	}
	transition: all 0.6s;
`;

const BtnWrapper = styled.div`
	width: 100%;
	max-width: 800px;
	display: flex;
	flex-direction: column;
	padding: 10px 20px;
	margin-bottom: 10px;
`;

const BooksList = styled.div`
	max-width: 800px;
	display: grid;
	place-items: center;
	grid-gap: 10px;
	@media screen and (min-width: 801px) {
		grid-template-columns: repeat(2, 1fr);
	}
`;

const LinkStyled = styled.div`
	display: flex;
	flex-direction: column;

	button {
		height: 3rem;
	}
`;

const ScrollEnd = styled.div`
	background-color: #fbfbfb;
	height: 10rem;
`;

export default BooksPage;
