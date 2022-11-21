import { useState } from 'react';
import styled from 'styled-components';
import { HiOutlineSearch, HiPhotograph } from 'react-icons/hi';

// components
import Title from '../components/common/Title';
import {
	BookInfo,
	Main,
	BodyContainer,
	TitleWrapper,
} from '../components/Books/BookElements';
import Button from '../components/common/Button';

interface PayloadType {
	title: string;
	author: string;
	publisher: string;
	rentalFee: string;
	description: string;
	imageUrl: string;
}

const BooksCreatePage = () => {
	const [title, setTitle] = useState('modern');
	const [author, setAuthor] = useState('woongmo');
	const [publisher, setPublisher] = useState('wiki');
	const [rentalFee, setRentalFee] = useState('');
	const [description, setDescription] = useState('test1');
	const [imageUrl, setImageUrl] = useState('notyet');

	const payload = {
		title,
		author,
		publisher,
		rentalFee,
		description,
		imageUrl,
	};

	const handleCreate = () => {
		console.log('click: ', payload);
	};
	/*
  const handleChangeTitle = (e:React.ChangeEvent<HTMLInputElement>) => {
    // 도서 api 조회 -> 제목, 저자, 출판사 상태 업데이트
    
  }
  */
	const handleChangeRentalFee = (e: React.ChangeEvent<HTMLInputElement>) => {
		setRentalFee(e.target.value);
	};
	const handleChangeDescription = (
		e: React.ChangeEvent<HTMLTextAreaElement>,
	) => {
		setDescription(e.target.value);
	};

	return (
		<Main>
			<TitleWrapper>
				<Title text="책 등록하기" />
			</TitleWrapper>

			<BodyContainer>
				<BookInfo>
					<div>
						<div className="book--info__title">
							<input type="text" placeholder="책 제목" />
							<SearchIcon />
						</div>
						<div className="book--info__default">
							<input type="text" value="저자 :" disabled />
							<input type="text" value="출판사 :" disabled />
						</div>
					</div>
				</BookInfo>
				<BookInfo>
					<input
						className="book--info__fee"
						type="number"
						placeholder="대여료"
						step={100}
						onChange={handleChangeRentalFee}
					/>
					<span>원 </span>
				</BookInfo>
				<BookInfo>
					<textarea
						placeholder="등록하실 책과 관련된 내용을 입력해주세요"
						onChange={handleChangeDescription}
					/>
				</BookInfo>
				<BookInfo>
					<span>거래 위치 : 서울시 종로구</span>
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

const SearchIcon = styled(HiOutlineSearch)`
	width: 1.4rem;
	height: 1.4rem;
	position: absolute;
	top: 0;
	right: 0;
	cursor: pointer;
`;

const Photicon = styled(HiPhotograph)`
	color: ${props => props.theme.colors.logoGreen};
	width: 4rem;
	height: 4rem;
	cursor: pointer;
`;

export default BooksCreatePage;
