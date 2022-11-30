import styled from 'styled-components';
import { useNavigate } from 'react-router-dom';
import { useInfiniteQuery, useQuery } from '@tanstack/react-query';
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
import { useMypageAPI } from '../api/mypage';

const BooksPage = () => {
	const { isLogin, id } = useAppSelector(state => state.loginInfo);
	const dispatch = useAppDispatch();
	const { getAllBooksListInfinite } = useBooksAPI();
	const { getMyInfo } = useMypageAPI();
	const navigate = useNavigate();
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

	const address = useQuery({
		queryKey: ['myprofile'],
		queryFn: () => getMyInfo(id),
		retry: false,
	}).data?.address;

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

	//가로스크롤 없애기
	overflow-x: hidden;

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
	padding: 10px;

	/* height: 75vh; */
	/* overflow-y: scroll; */
	/* ::-webkit-scrollbar {
		display: none;
	} */

	/* max-width: 1300px; */

	display: grid;
	grid-gap: 10px;
	@media screen and (min-width: 1101px) {
		grid-template-columns: repeat(2, 1fr);
	}
`;

const LinkStyled = styled.div`
	display: flex;
	flex-direction: column;
`;

const ScrollEnd = styled.div`
	background-color: ${props => props.theme.colors.grey};
	height: 10rem;
`;

export default BooksPage;
