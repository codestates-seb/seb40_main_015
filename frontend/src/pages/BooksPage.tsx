import styled from 'styled-components';
import { Link } from 'react-router-dom';
import { useInfiniteQuery } from '@tanstack/react-query';
import { useEffect, useRef } from 'react';

//components
import BookItem from '../components/Books/BookItem';
import Button from '../components/common/Button';
import Title from '../components/common/Title';
import Animation from '../components/Loading/Animation';

//hooks
import { useBooksAPI } from '../api/books';
import { useAppDispatch, useAppSelector } from '../redux/hooks';
import notify from '../utils/notify';

const BooksPage = () => {
	const { isLogin } = useAppSelector(state => state.loginInfo);
	const dispatch = useAppDispatch();
	const { getAllBooksListInfinite } = useBooksAPI();
	const target = useRef<HTMLDivElement>(null);

	//무한스크롤
	const { data, fetchNextPage, isLoading, hasNextPage, isFetchingNextPage } =
		useInfiniteQuery({
			queryKey: ['allBooks'],
			queryFn: ({ pageParam = undefined }) =>
				getAllBooksListInfinite(pageParam),
			getNextPageParam: lastPage => {
				return lastPage?.content?.slice(-1)[0]?.bookId;
			},
		});

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

	return (
		<Main>
			<TitleWrapper>
				<Title text="동네북" isLogo={true} />
			</TitleWrapper>

			<BtnWrapper>
				<LinkStyled to={isLogin ? '/books/create' : ''}>
					<Button
						onClick={() => isLogin || notify(dispatch, '로그인이 필요합니다')}>
						책 등록하기
					</Button>
				</LinkStyled>
			</BtnWrapper>

			<BooksList>
				{isLoading ? (
					<Animation width={20} height={20} />
				) : (
					data?.pages?.map(el =>
						el?.content?.map(el => {
							// if (el.status === '거래중단') return '';
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
	/* padding: 10px; */

	/* height: 75vh; */
	/* overflow-y: scroll; */
	/* ::-webkit-scrollbar {
		display: none;
	} */

	max-width: 800px;
	display: grid;
	place-items: center;
	grid-gap: 10px;
	@media screen and (min-width: 801px) {
		grid-template-columns: repeat(2, 1fr);
	}
`;

const LinkStyled = styled(Link)`
	display: flex;
	flex-direction: column;
`;

const ScrollEnd = styled.div`
	background-color: ${props => props.theme.colors.grey};
	height: 10rem;
`;

export default BooksPage;
