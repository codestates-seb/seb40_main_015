import { useAppSelector } from '../redux/hooks';
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

const BooksCreatePage = () => {
	const bookCreate = useAppSelector(state => state.persistedReducer.bookCreate);
	const { title, authors, publisher } = bookCreate.bookInfo;
	const { rentalFee, description, imageUrl } = bookCreate.rentalInfo;
	const api = useAPI();

	const payload = {
		title,
		author: authors.join(', '),
		publisher,
		rentalFee,
		description,
		imageUrl,
	};

	const handleCreate = () => {
		api.post('/books', payload).then(res => console.log(res));
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
