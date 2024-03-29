import React, { useState } from 'react';
import { useLocation } from 'react-router';
import { useAppDispatch } from 'redux/hooks';
import {
	Button,
	ConfirmModal,
	Title,
	RatingSelect,
	TextInput,
} from 'components';
import useCreateReview from 'api/hooks/review/useCreateReview';
import notify from 'utils/notify';
import styled from 'styled-components';

const ReviewCreatePage = () => {
	const [content, setContent] = useState('');
	const [submit, setSubmit] = useState(false);
	const [hovered, setHovered] = useState<number | null>(null);
	const [clicked, setClicked] = useState<number>(3);

	const dispatch = useAppDispatch();
	const location = useLocation();
	const params = new URLSearchParams(location.search);

	const [rentalId, bookId, title, id] = [
		params.get('rentalId'),
		params.get('bookId'),
		params.get('title'),
		params.get('id'),
	];

	const { mutate: createReview } = useCreateReview(
		rentalId!,
		bookId!,
		content,
		clicked,
	);

	const handleChangeContent = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
		setContent(e.target.value);
	};

	const handleSubmitReview = () => {
		setSubmit(true);
	};

	const handleClickSubmit = () => {
		if (!clicked) {
			notify(dispatch, '평점을 체크해주세요');
			return;
		}
		if (!content) {
			notify(dispatch, '리뷰를 작성해주세요');
			return;
		}
		createReview();
	};

	return (
		<Container>
			<Title text="리뷰 남기기" />
			<Layout>
				<Box>
					<ReviewBox>
						<p>도서명 : {title} </p>
						<p>상인명 : {id}</p>
						<SelectBox>
							<span>상인평점 : </span>
							<RatingSelect
								hovered={hovered}
								setHovered={setHovered}
								clicked={clicked}
								setClicked={setClicked}
							/>
						</SelectBox>
						<TextareaBox>
							<p>리뷰 : </p>
							<TextInput
								placeholder="최대 100자까지 가능합니다."
								maxLength={100}
								value={content}
								onChange={handleChangeContent}
							/>
						</TextareaBox>
					</ReviewBox>
					<Button onClick={handleSubmitReview}>리뷰 등록</Button>
				</Box>
			</Layout>
			{submit && (
				<ConfirmModal
					text={
						'리뷰 등록 후 수정이 불가합니다. \n 해당 내용으로 등록 하시겠습니까?'
					}
					setSubmit={setSubmit}
					handleClick={handleClickSubmit}
				/>
			)}
		</Container>
	);
};

const Container = styled.div`
	width: 100%;
	height: 90vh;
	display: flex;
	flex-direction: column;
	justify-content: space-between;
	p {
		font-size: ${props => props.theme.fontSizes.subtitle};
	}
`;

const Layout = styled.div`
	height: 100%;
	display: flex;
	flex-direction: column;
	align-items: center;
	justify-content: space-between;
`;

const Box = styled.div`
	display: flex;
	flex-direction: column;
	justify-content: space-around;
	height: 100%;
	width: 50%;
	margin-top: 5rem;
	background-color: white;
	border: 1px solid #eaeaea;
	border-radius: 5px;
	padding: 0 5rem;
	@media screen and (min-width: 800px) {
		width: 800px;
	}
	span {
		font-size: ${props => props.theme.fontSizes.subtitle};
	}
`;

const ReviewBox = styled.div`
	display: flex;
	flex-direction: column;
	justify-content: space-evenly;
	height: 60%;
`;

const SelectBox = styled.div`
	display: flex;
	align-items: center;
`;

const TextareaBox = styled.div`
	display: flex;
	flex-direction: column;
`;

export default ReviewCreatePage;
