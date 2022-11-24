import styled from 'styled-components';
import { useAppSelector } from '../redux/hooks';
import { HiPhotograph } from 'react-icons/hi';
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

// interface PayloadType {
// 	title: string;
// 	author: string;
// 	publisher: string;
// 	rentalFee: string;
// 	description: string;
// 	imageUrl: string;
// }

const BooksCreatePage = () => {
	const bookCreate = useAppSelector(state => state.persistedReducer.bookCreate);
	const { title, authors, publisher } = bookCreate.bookInfo;
	const { rentalFee, description, imageUrl } = bookCreate.rentalInfo;

	const payload = {
		title,
		authors: authors.join(', '),
		publisher,
		rentalFee,
		description,
		imageUrl,
		// location,
	};

	const handleCreate = () => {
		console.log('click: ', payload);
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
					<span>거래 위치 : {`서울시 종로구`}</span>
				</BookInfo>
				<BookInfo>
					<div className="book--info__photo">
						<label htmlFor="photo">
							<Photicon />
						</label>
						<div>image file</div>
						<input
							id="photo"
							type="file"
							accept=".png,.jpg,.jpeg"
							multiple={false}
						/>
					</div>
				</BookInfo>
			</BodyContainer>
			<Button onClick={handleCreate}>등록하기</Button>
		</Main>
	);
};

const Photicon = styled(HiPhotograph)`
	color: ${props => props.theme.colors.logoGreen};
	width: 4rem;
	height: 4rem;
	cursor: pointer;
`;

export default BooksCreatePage;
