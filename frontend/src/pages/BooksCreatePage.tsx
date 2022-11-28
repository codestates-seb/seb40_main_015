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
		console.log(payload);
		if (validateBookCreatePayloads(payload)) {
			mutate(payload);
			// navigate('/books');
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
