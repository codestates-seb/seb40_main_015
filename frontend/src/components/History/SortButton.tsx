import styled from 'styled-components';
import { MdArrowDropDown } from 'react-icons/md';
import { Dispatch, SetStateAction } from 'react';

interface IsetFilter {
	setFilters: Dispatch<SetStateAction<string>>;
}

const OPTIONS = [
	{ value: '전체', name: '전체' },
	{ value: '거래중', name: '거래중' },
	{ value: '대여중', name: '대여중' },
	{ value: '리뷰미완료', name: '리뷰미완료' },
	{ value: '리뷰완료', name: '리뷰완료' },
	{ value: '취소완료', name: '취소완료' },
];

const SortButton = ({ setFilters }: IsetFilter) => {
	const handleOption = (e: React.ChangeEvent<HTMLSelectElement>) => {
		e.target.value === '전체' && setFilters('');
		e.target.value === '거래중' && setFilters('TRADING');
		e.target.value === '대여중' && setFilters('BEING_RENTED');
		e.target.value === '리뷰미완료' && setFilters('RETURN_UNREVIEWED');
		e.target.value === '리뷰완료' && setFilters('RETURN_REVIEWED');
		e.target.value === '취소완료' && setFilters('CANCELED');
	};
	return (
		<Layout>
			<Container>
				<Box onChange={e => handleOption(e)}>
					{OPTIONS.map(option => {
						const { value, name } = option;
						return (
							<option key={value} value={value}>
								{name}
							</option>
						);
					})}
				</Box>
				<MdArrowDropDown size={25} />
			</Container>
		</Layout>
	);
};

export default SortButton;

const Layout = styled.div`
	width: 100%;
	max-width: 800px;
	display: inline-flex;
	justify-content: flex-end;
`;

const Container = styled.div`
	display: flex;
	justify-content: flex-end;
	padding: 0 1.5rem;
	margin-bottom: 1rem;
	border: 1px solid #eaeaea;
	border-radius: 10px;
	background-color: white;
	cursor: pointer;
`;

const Box = styled.select`
	display: flex;
	align-items: center;
	border: none;
	outline: none;
	text-align: center;
	font-size: ${props => props.theme.fontSizes.paragraph};
	-webkit-appearance: none;
	-moz-appearance: none;
	appearance: none;
	cursor: pointer;
`;
