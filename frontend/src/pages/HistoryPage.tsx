import styled from 'styled-components';
import TabLists from '../components/common/TabLists';
import Title from '../components/common/Title';
import LentBookLists from '../components/History/LendBookLists';
import RentBookLists from '../components/History/RentBookLists';
import useTabs from '../hooks/useTabs';

const HistoryPage = () => {
	const [tab, curTab, handleChange] = useTabs(['빌린 책', '빌려준 책']);

	return (
		<Layout>
			<Title text="대여 목록" />
			<TabLists tabs={tab} handleChange={handleChange} />
			{curTab === '빌린 책' && <RentBookLists />}
			{curTab === '빌려준 책' && <LentBookLists />}
		</Layout>
	);
};

const Layout = styled.div`
	display: flex;
	align-items: center;
	flex-direction: column;
	padding: 1rem;
	h2 {
		font-size: 2rem;
		margin-bottom: 1rem;
	}
`;

const Tab = styled.button`
	padding: 0.8rem 3rem;
	background-color: ${props => props.theme.colors.main};
	border: none;
	border-radius: 5px;
	box-shadow: nonoe;
	color: white;
	cursor: pointer;
	/* display: flex;
	flex-direction: row;
	flex-wrap: nowrap; */
`;

export default HistoryPage;
