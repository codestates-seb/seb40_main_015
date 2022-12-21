import { useQuery } from '@tanstack/react-query';
import { useNavigate } from 'react-router';
import styled from 'styled-components';
import { useAppDispatch, useAppSelector } from 'redux/hooks';
import { resetBookCreateInfo } from 'redux/slice/bookCreateSlice';
import usePostBooks from 'api/hooks/createBooks/usePostBooks';
import { useMypageAPI } from 'api/mypage';
import { makeCreateBookMessages } from 'utils/makeCreateBookMessages';
import notify from 'utils/notify';
import { validateBookCreatePayloads } from 'utils/validateBookCreatePayload';

import {
	// Button,
	Main,
	BodyContainer,
	TitleWrapper,
	BookInfo,
	Description,
	Photo,
	RentalFee,
	SearchForm,
	Title,
} from 'components';
import Button from 'components/common/Button';
////// button error

const BooksCreatePage = () => {
	const bookCreate = useAppSelector(state => state.persistedReducer.bookCreate);
	const { id } = useAppSelector(state => state.loginInfo);
	const { title, authors, publisher } = bookCreate.bookInfo;
	const { rentalFee, description, imageUrl } = bookCreate.rentalInfo;
	const createBookMessages = makeCreateBookMessages(bookCreate);
	const dispatch = useAppDispatch();
	const goNotify = (message: string) => notify(dispatch, message);
	const navigate = useNavigate();
	const { getMyInfo } = useMypageAPI();
	const { mutate } = usePostBooks();

	const payload = {
		title,
		author: authors.join(', '),
		publisher,
		rentalFee,
		description,
		imageUrl,
	};

	const { data } = useQuery({
		queryKey: ['myprofile'],
		queryFn: () => getMyInfo(id),
		retry: false,
	});

	const handleCreate = () => {
		createBookMessages.forEach((message, notifyCase) => {
			if (notifyCase) {
				goNotify(message);
			}
		});

		if (validateBookCreatePayloads(payload)) {
			mutate(payload, {
				onSuccess: () => {
					dispatch(resetBookCreateInfo());
					goNotify('게시글이 작성되었습니다.');
					navigate('/books');
				},
				onError: () => {
					goNotify('게시글 작성에 실패했습니다. 잠시 후 다시 시도해주세요.');
				},
			});
		}
	};

	return (
		<>
			<TitleWrapper>
				<Title text="책 등록하기" />
			</TitleWrapper>
			<StyledMain>
				<StyledBodyContainer>
					<SearchForm />
					<RentalFee />
					<Description />
					<StyledBookInfo>
						<span>거래 위치 : {data?.address}</span>
					</StyledBookInfo>
					<Photo />
					<StyledButton onClick={handleCreate}>등록하기</StyledButton>
				</StyledBodyContainer>
			</StyledMain>
		</>
	);
};

export const StyledBookInfo = styled(BookInfo)`
	box-sizing: border-box;
	width: 100%;
	background-color: white;
`;

const StyledBodyContainer = styled(BodyContainer)`
	width: 70%;
	max-width: 800px;
`;

const StyledButton = styled(Button)`
	height: 3rem;
	width: 100%;
	margin-top: 1rem;
`;

const StyledMain = styled(Main)`
	height: 100%;
	align-items: center;
`;

export default BooksCreatePage;
