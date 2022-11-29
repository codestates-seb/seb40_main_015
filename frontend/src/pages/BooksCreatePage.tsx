import { useAppDispatch, useAppSelector } from '../redux/hooks';
import Title from '../components/common/Title';
import {
	BookInfo,
	Main,
	BodyContainer,
	TitleWrapper,
} from '../components/Books/BookElements';
import Button from '../components/common/Button';
import SearchForm from '../components/BooksCreate/SearchForm';
import RentalFee from '../components/BooksCreate/RentalFee';
import Description from '../components/BooksCreate/Description';
import Photo from '../components/BooksCreate/Photo';
import { makeCreateBookMessages } from '../utils/makeCreateBookMessages';
import notify from '../utils/notify';
import { validateBookCreatePayloads } from '../utils/validateBookCreatePayload';
import usePostBooks from '../api/hooks/createBooks/usePostBooks';
import { useNavigate } from 'react-router';

const BooksCreatePage = () => {
	const bookCreate = useAppSelector(state => state.persistedReducer.bookCreate);
	const { title, authors, publisher } = bookCreate.bookInfo;
	const { rentalFee, description, imageUrl } = bookCreate.rentalInfo;
	const createBookMessages = makeCreateBookMessages(bookCreate);
	const dispatch = useAppDispatch();
	const goNotify = (message: string) => notify(dispatch, message);
	const navigate = useNavigate();

	const { mutate } = usePostBooks();

	const payload = {
		title,
		author: authors.join(', '),
		publisher,
		rentalFee,
		description,
		imageUrl,
	};

	const handleCreate = () => {
		createBookMessages.forEach((message, notifyCase) => {
			if (notifyCase) {
				goNotify(message);
			}
		});

		if (validateBookCreatePayloads(payload)) {
			mutate(payload, {
				onSuccess: () => {
					goNotify('게시글이 작성되었습니다.');
					navigate('/books');
				},
				onError: () => {
					goNotify('게시글 작성에 실패했습니다.\n 잠시 후 다시 시도해주세요.');
				},
			});
		}
	};

	return (
		<Main>
			<TitleWrapper>
				<Title text="책 등록하기" />
			</TitleWrapper>
			<BodyContainer>
				<SearchForm />
				<RentalFee />
				<Description />
				<BookInfo>
					<span>거래 위치 : {''}</span>
				</BookInfo>
				<Photo />
			</BodyContainer>
			<Button onClick={handleCreate}>등록하기</Button>
		</Main>
	);
};

export default BooksCreatePage;
