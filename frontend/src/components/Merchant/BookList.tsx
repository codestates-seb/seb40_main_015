import { useNavigate } from 'react-router-dom';
import styled from 'styled-components';
import useMerchantBookList from 'api/hooks/merchant/useMerchantBookList';

interface Item {
	bookId: string;
	title: string;
	bookImage: string;
	status: string;
}

const BookList = ({ merchantId }: { merchantId: string }) => {
	const { lists, hasNextPage, ref } = useMerchantBookList(merchantId);
	const navigate = useNavigate();

	const handleBookDetailPageMove = (id: string) => {
		navigate(`/books/${id}`);
	};

	return (
		<>
			{lists?.length ? (
				lists.map((item: Item, i: number) => {
					const { bookId, title, bookImage, status } = item;
					return (
						<Container key={bookId}>
							<FlexBox
								onClick={e => {
									handleBookDetailPageMove(bookId);
								}}>
								<img src={bookImage} alt="책 이미지" width={50} height={70} />
								<InfoWrapped>
									<p>
										{title.length < 17 ? title : title.slice(0, 17) + '...'}
									</p>
									<BookStatus status={status}>
										{(() => {
											switch (status) {
												case '대여가능':
													return '대여가능';
												case '대여중&예약가능':
													return '예약가능';
												case '예약불가':
													return '예약불가';
											}
										})()}
									</BookStatus>
								</InfoWrapped>
							</FlexBox>
						</Container>
					);
				})
			) : (
				<EmptyBox>
					<p>대여 가능한 책이 없어요</p>
				</EmptyBox>
			)}
			{hasNextPage ? <ScrollEnd ref={ref}>Loading...</ScrollEnd> : null}
		</>
	);
};

const Container = styled.div`
	width: 90%;
	display: flex;
	justify-content: space-between;
	border: 1px solid #eaeaea;
	border-radius: 5px;
	padding: 1rem;
	margin-bottom: 0.5rem;
	background-color: white;
	:hover {
		background-color: ${props => props.theme.colors.grey};
	}
	@media (min-width: 800px) {
		width: 800px;
	}
`;

const FlexBox = styled.div`
	display: flex;
	width: 100%;
	cursor: pointer;
`;

const InfoWrapped = styled.div`
	display: flex;
	width: 100%;
	margin-left: 0.3rem;
	justify-content: space-between;
	align-items: center;
	p {
		font-size: ${props => props.theme.fontSizes.paragraph};
		margin-left: 1rem;
	}
`;

const EmptyBox = styled.div`
	width: 100%;
	height: 75vh;
	display: flex;
	justify-content: center;
	align-items: center;
	p {
		font-size: ${props => props.theme.fontSizes.subtitle};
		font-weight: 600;
	}
`;

const ScrollEnd = styled.div`
	background-color: #fbfbfb;
`;

//책 전체조회페이지와 같음
interface IBookstatus {
	status: string;
}
const BookStatus = styled.div<IBookstatus>`
	width: 30px;
	height: 30px;
	border: none;
	border-radius: 50%;
	padding: 10px;
	color: black;
	display: flex;
	text-align: center;
	font-size: 14px;
	background-color: ${props => {
		switch (props.status) {
			case '대여가능':
				return '#DEF5E5';
			case '대여중&예약가능':
				return '#FFFAD7';
			case '예약불가':
				return '#FF9F9F';
		}
	}};
`;
export default BookList;
