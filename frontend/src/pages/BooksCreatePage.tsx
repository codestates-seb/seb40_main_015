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
import useAPI from '../hooks/useAPI';
import { makeCreateBookMessages } from '../utils/makeCreateBookMessages';
import notify from '../utils/notify';

const BooksCreatePage = () => {
	const bookCreate = useAppSelector(state => state.persistedReducer.bookCreate);
	const { title, authors, publisher } = bookCreate.bookInfo;
	const { rentalFee, description, imageUrl } = bookCreate.rentalInfo;
	const createBookMessages = makeCreateBookMessages(bookCreate);
	const api = useAPI();
	const dispatch = useAppDispatch();
	const goNotify = (message: string) => notify(dispatch, message);

	const payload = {
		title,
		author: authors.join(', '),
		publisher,
		rentalFee,
		description,
		imageUrl,
	};

	const isValid = () => {
		const values = Object.values(payload);
		for (let i = 0; i < values.length; i++) {
			if (!values[i]) return false;
		}
		return true;
	};

	const handleCreate = () => {
		createBookMessages.forEach((message, notifyCase) => {
			if (notifyCase) {
				goNotify(message);
			}
		});
		if (isValid()) {
			api.post('/books', payload).then(res => console.log(res));
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
