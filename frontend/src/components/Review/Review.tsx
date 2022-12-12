import React, { useState } from 'react';
import { useLocation } from 'react-router';
import styled from 'styled-components';
import useCreateReview from '../../api/hooks/review/useCreateReview';
import Button from '../common/Button';
import ConfirmModal from '../common/ConfirmModal';
import Title from '../common/Title';
import { HiStar, HiOutlineStar } from 'react-icons/hi';

const Review = () => {
	const [score, setScore] = useState(5);
	const [content, setContent] = useState('');
	const [submit, setSubmit] = useState(false);
	const location = useLocation();
	const params = new URLSearchParams(location.search);
	const [rentalId, bookId] = [params.get('rentalId'), params.get('bookId')];
	const { mutate } = useCreateReview(rentalId!, bookId!, content, score);

	const handleScoreChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
		setScore(Number(e.target.value));
	};

	const handleChangeContent = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
		setContent(e.target.value);
	};

	const handleSubmitReview = () => {
		setSubmit(true);
	};

	const handleClickSubmit = () => {
		mutate();
	};

	return (
		<Container>
			<Title text="리뷰 남기기" />
			<Layout>
				<Box>
					<p>상인명 : {params.get('id')}</p>
					<SelectBox>
						<span>상인평점 : </span>
						<select name="" id="" onChange={handleScoreChange} defaultValue="5">
							<option value="1">1</option>
							<option value="2">2</option>
							<option value="3">3</option>
							<option value="4">4</option>
							<option value="5">5</option>
						</select>
					</SelectBox>
					<p>리뷰 : </p>
					<Textarea
						placeholder="최대 30자까지 가능합니다"
						maxLength={30}
						value={content}
						onChange={handleChangeContent}
					/>
				</Box>
			</Layout>

			<Button onClick={handleSubmitReview}>리뷰 등록</Button>
			{submit ? (
				<ConfirmModal
					text={'등록 후 수정이 불가합니다. \n 등록 하실래요?'}
					setSubmit={setSubmit}
					handleClick={handleClickSubmit}
				/>
			) : null}
			{/* <ConfirmModal text={'등록 후 수정이 불가합니다. \n 등록 하실래요?'} /> */}
		</Container>
	);
};

const Container = styled.div`
	width: 100%;
	height: 100%;
	display: flex;
	flex-direction: column;
	justify-content: space-between;
	p {
		font-size: ${props => props.theme.fontSizes.subtitle};
		font-weight: 600;
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
	height: 30rem;
	width: 50%;
	margin-top: 5rem;
	span {
		font-size: ${props => props.theme.fontSizes.subtitle};
		font-weight: 600;
	}
`;

const SelectBox = styled.div`
	font-size: 1rem;
	font-weight: 600;
`;

const Textarea = styled.textarea`
	height: 15rem;
	padding: 1rem;
	resize: none;
	font-size: 1.2rem;
	border: 1px solid ${props => props.theme.colors.grey};
`;

export default Review;
