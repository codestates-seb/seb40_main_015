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
				threshold: 0.5,
			},
		);
		observer.observe(target.current as Element);
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
					<Animation />
				) : (
					data?.pages?.map(el =>
						el?.content?.map(el => {
							// if (el.status === '거래중단') return;
							return (
								<BookItem
									key={+el.bookId}
									bookId={el.bookId}
									title={el.title}
									bookImage={el.bookImage}
									status={el.status}
									rentalfee={el.rentalFee}
									merchantName={el.merchantName}
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
	/* align-items: center; */

	.hidden {
		display: none;
	}
`;

const TitleWrapper = styled.div``;

const BtnWrapper = styled.div`
	display: flex;
	flex-direction: column;
	padding: 10px 20px;
`;

const BooksList = styled.div`
	height: 75vh;
	padding: 10px;

	overflow-x: scroll;

	::-webkit-scrollbar {
		display: none;
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
